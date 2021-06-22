package com.zed.infrastructure.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zed.domain.aggregate.enums.Attributes;
import com.zed.domain.aggregate.enums.Transport;
import com.zed.domain.factory.ClientFactory;
import com.zed.domain.service.ClientService;
import com.zed.infrastructure.config.SocketConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;

import java.util.List;

/**
 * 处理连接和关闭得Handler
 *
 * @author Zed
 */
public class FullHttpRequestHandler extends ChannelInboundHandlerAdapter {

    private SocketConfig socketConfig;

    private WebSocketServerHandshaker handShaker;

    private ClientService clientService;

    public FullHttpRequestHandler(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
        this.clientService = SpringUtil.getBean(ClientService.class);
    }

    private String getWebSocketLocation(HttpRequest req) {
        String protocol = "ws://";

        if (socketConfig.isSSL()) {
            protocol = "wss://";
        }

        return protocol + req.headers().get(HttpHeaderNames.HOST) + req.uri();
    }

    private void handShake(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess() || !(Transport.WEB_SOCKET.isWebSocket(request))) {
            ctx.channel().close();
            return;
        }

        //创建工厂对象
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(request),
                null,
                false);

        handShaker = wsFactory.newHandshaker(request);

        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), request);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            List<String> transport = Transport.getUriParams(req);
            List<String> sids = Attributes.getUriParams(req, Attributes.SID);
            if (CollUtil.isNotEmpty(transport) && transport.contains(Transport.WEB_SOCKET.getTransport())) {
                try {
                    handShake(ctx, (FullHttpRequest) msg);
                    if (CollUtil.isNotEmpty(sids)) {
                        clientService.connects(ClientFactory.createClient(sids.get(0), req, ctx.channel()));
                    } else {
                        clientService.connects(ClientFactory.createClient(null, req, ctx.channel()));
                    }
                } finally {
                    req.release();
                }
            }
        } else if ((msg instanceof WebSocketFrame) && (msg instanceof CloseWebSocketFrame)) {
            CloseWebSocketFrame closeFrame = (CloseWebSocketFrame) msg;
            handShaker.close(ctx.channel(), closeFrame.retain());
            closeFrame.release();
            return;
        } else {
            ctx.fireChannelRead(msg);
        }
    }

}
