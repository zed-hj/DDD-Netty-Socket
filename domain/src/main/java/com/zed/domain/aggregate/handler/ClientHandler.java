package com.zed.domain.aggregate.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public ClientHandler() {
        firstMessage = Unpooled.copiedBuffer("Hello", CharsetUtil.UTF_8);
    }

    /**
     * 客户端连接服务端并发送Hello给到服务端
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {

            String inStr = in.toString(Charset.defaultCharset());
            System.out.println("来自" + ctx.channel().remoteAddress().toString() + "数据:" + inStr);
        } finally {
            /**
             * 释放资源
             * ReferenceCountUtil.release(msg);
             */
//            in.release();
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        ctx.close();
    }

}
