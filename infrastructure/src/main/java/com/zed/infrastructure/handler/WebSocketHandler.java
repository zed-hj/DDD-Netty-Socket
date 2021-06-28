package com.zed.infrastructure.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.zed.domain.aggregate.Client;
import com.zed.domain.repository.ClientBoxRepository;
import com.zed.domain.service.ClientService;
import com.zed.infrastructure.protocol.PacketsMessage;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zed
 */
@Slf4j
public class WebSocketHandler extends ChannelInboundHandlerAdapter {

    private ClientBoxRepository clientBoxRepository;

    private ClientService clientService;

    public WebSocketHandler() {
        this.clientBoxRepository = SpringUtil.getBean(ClientBoxRepository.class);
        this.clientService = SpringUtil.getBean(ClientService.class);
    }

    /**
     * 客户端与服务器断开连接的时候调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        clientService.disconnect(ctx);
        super.channelInactive(ctx);
    }

    /**
     * 注销事件
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        clientService.disconnect(ctx);
        super.channelUnregistered(ctx);
    }

    /**
     * 服务器接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        super.channelReadComplete(ctx);
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
        clientService.disconnect(ctx);
        super.exceptionCaught(ctx, cause);
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
                Client client = clientBoxRepository.get(ctx.channel());
                if (client == null) {
                    log.info("Client with was already disconnected. Channel closed!");
                    ctx.channel().close();
                    frame.release();
                    return;
                }

                /**
                 * 处理消息
                 */
                ctx.pipeline().fireChannelRead(new PacketsMessage(ctx.channel(),
                        client,
                        byteBufHolderFrame.content()));

                byteBufHolderFrame.release();
            }

        } else {
            ctx.fireChannelRead(msg);
        }
    }


}
