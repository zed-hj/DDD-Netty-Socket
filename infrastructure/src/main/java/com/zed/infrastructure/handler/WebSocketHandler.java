package com.zed.infrastructure.handler;

import cn.hutool.http.HttpStatus;
import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dao.NamespaceRepositoryImpl;
import com.zed.infrastructure.persistence.dos.ClientChannelDO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.List;

/**
 * @author zed
 */
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    /**
     * 是否开启证书连接
     */
    private static boolean IS_SSL = false;

    private WebSocketServerHandshaker handshaker;

    /**
     * 抽象成接口支持集群模式的存储
     */
    private NamespaceRepository namespaceRepository;


    public WebSocketHandler() {
        this(new NamespaceRepositoryImpl());
    }

    public WebSocketHandler(NamespaceRepository namespaceRepository) {
        this.namespaceRepository = namespaceRepository;
    }

    /**
     * 客户端与服务器断开连接的时候调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
        //TODO 删除记录
    }

    /**
     * 服务器接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 异常时候调用
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 服务端处理客户端websocket请求的核心方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        //1. 客户端向服务器发送握手请求
        //2. 建立websocket连接

        //处理客户端向服务器发起http握手请求的业务
        if (msg instanceof FullHttpRequest) {
            handshake(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            /**
             * 处理websocket连接业务
             */
            handWebsocketFrame(ctx, (WebSocketFrame) msg);
        }

    }

    /**
     * 处理客户端与服务器之间的websocket业务
     *
     * @param ctx
     * @param frame
     */
    private void handWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否是关闭websocket的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), ((CloseWebSocketFrame) frame).retain());
            return;
        }

        //判断是否是ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        //判断是否是二进制消息，如果是二进制消息，抛出异常，  不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            System.out.println("目前我们不支持二进制消息");
            throw new RuntimeException("【" + this.getClass().getName() + "】不支持消息");
        }

        //返回应答消息
        //获取客户端向服务器端发送的消息
        String request = ((TextWebSocketFrame) frame).text();
        System.out.println("服务端收到客户端的消息====>>>" + request);
        //创建TextWebSocketFrame对象，接收客户端发送过来的消息
        TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + "=====>>>>" + request);

        //群发，服务端向每个连接上来的客户端群发消息
        ClientChannelDO.getInstance().sendToAll(tws);

    }


    /**
     * 服务器向客户端响应消息
     *
     * @param ctx
     * @param req
     * @param res
     */
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        //请求失败
        if (res.getStatus().code() != HttpStatus.HTTP_OK) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        //服务端向客户端发送数据
        ChannelFuture cf = ctx.channel().writeAndFlush(res);
        if (res.getStatus().code() != HttpStatus.HTTP_OK) {
            cf.addListener(ChannelFutureListener.CLOSE);
        }
    }


    void handshake(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.getDecoderResult().isSuccess() || !(NAME.equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        //创建工厂对象
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request), null, false);
        //创建handshaker对象
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), request);
        }

        namespaceRepository.saveTenant(null, getUriParams(request, "sid").get(0), ctx.channel());
    }

    public static final String NAME = "websocket";

    private List<String> getUriParams(FullHttpRequest request, String key) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(request.uri());
        return queryDecoder.parameters().get(key);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            List<String> transport = getUriParams(req, "transport");
            List<String> sid = getUriParams(req, "sid");
            List<String> appId = getUriParams(req, "appId");

            if (transport != null && NAME.equals(transport.get(0))) {
                try {
                    if (sid != null && sid.get(0) != null) {
                        messageReceived(ctx, msg);
//                        final String sessionId = sid.get(0);
//                        handshake(ctx, req);
                    } else {
                        System.out.println("没有执行");
//                        ClientHead client = ctx.channel().attr(ClientHead.CLIENT).get();
//                         first connection
//                        handshake(ctx, client.getSessionId(), path, req);
                    }
                } finally {
                    /**
                     * SimpleChannelInboundHandler 因为继承了这个会自动去释放，所以不用去管理
                     */
                    req.release();
                }
            } else {
                ctx.fireChannelRead(msg);
            }
        } else if (msg instanceof WebSocketFrame) {
            messageReceived(ctx, msg);
        } else {
            ctx.fireChannelRead(msg);
        }
    }


    private String getWebSocketLocation(HttpRequest req) {
        String protocol = "ws://";
        if (IS_SSL) {
            protocol = "wss://";
        }
        return protocol + req.headers().get(HttpHeaderNames.HOST) + req.uri();
    }


}
