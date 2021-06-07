package com.zed.infrastructure.handler;

import com.zed.infrastructure.entity.UnixTime;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 时间客户端
 *
 * @author zed
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private ByteBuf buf;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        buf = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        UnixTime unixTime = (UnixTime) msg;
        ctx.close();

//        ByteBuf m = (ByteBuf) msg;
//        buf.writeBytes(m);
//        m.release();
//
//        long currentTimeMillis = (buf.readUnsignedInt() - 2208988800L) * 1000L;
//        System.out.println(new Date(currentTimeMillis));
//        ctx.close();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}
