package com.zed.infrastructure.handler;

import com.zed.infrastructure.entity.UnixTime;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * 时间返回服务器
 *
 * @author 15901019193
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 连接成功之后返回时间
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(new UnixTime())
                .addListener(ChannelFutureListener.CLOSE);

//        final ByteBuf time = ctx.alloc().buffer(4); // (2)
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//
//        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
////                ctx.close();
//            }
//        }); // (4)

//        final ByteBuf time = ctx.alloc().buffer(10);
//        time.writeCharSequence(LocalDateTime.now().toString(), CharsetUtil.UTF_8);
//        final ChannelFuture f = ctx.writeAndFlush(time);
        // 完成后的事件
       /* f.addListener((ChannelFuture future) -> {
            assert f == future;
        });*/
//        f.addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
