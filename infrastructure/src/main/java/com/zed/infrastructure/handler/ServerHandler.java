package com.zed.infrastructure.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * 监听客户端状态
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 连接事件
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {

            String inStr = in.toString(Charset.defaultCharset());
            System.out.println("来自" + ctx.channel().remoteAddress().toString() + "数据:" + inStr);

        } finally {
            /**
             * 释放资源
             * ReferenceCountUtil.release(msg);
             */
            in.release();
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
