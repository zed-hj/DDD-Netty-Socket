package com.zed.domain.aggregate.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.zed.domain.aggregate.entity.valueobj.ClientHeader;
import com.zed.domain.repository.ClientBoxRepository;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.*;

/**
 * @author zed
 */
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    private ClientBoxRepository clientBoxRepository;

    public WebSocketHandler() {
        this.clientBoxRepository = SpringUtil.getBean(ClientBoxRepository.class);
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


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof WebSocketFrame) {
            WebSocketFrame frame = (WebSocketFrame) msg;
            /**
             * 判断是否是ping消息
             */
            if (frame instanceof PingWebSocketFrame) {
                ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
                frame.release();
                return;
            }

            if (msg instanceof BinaryWebSocketFrame
                    || msg instanceof TextWebSocketFrame) {
                /**
                 * 处理消息
                 */
                ByteBufHolder byteBufHolderFrame = (ByteBufHolder) msg;
                ClientHeader clientHeader = clientBoxRepository.get(ctx.channel());
                if (clientHeader == null) {
                    System.out.println("Client with was already disconnected. Channel closed!");
                    ctx.channel().close();
                    frame.release();
                    return;
                }

                /**
                 * 处理消息
                 */
//                ctx.pipeline().fireChannelRead(new PacketsMessage(client, frame.content(), Transport.WEBSOCKET));
//                frame.release();
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }


}
