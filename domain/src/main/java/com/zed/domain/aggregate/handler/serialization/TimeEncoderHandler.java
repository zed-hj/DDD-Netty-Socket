//package com.zed.domain.aggregate.handler.serialization;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelOutboundHandlerAdapter;
//import io.netty.channel.ChannelPromise;
//
///**
// * 编码
// *
// * public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
// *     @Override
// *     protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
// *         out.writeInt((int)msg.value());
// *     }
// * }
// *
// * @author 15901019193
// */
//public class TimeEncoderHandler extends ChannelOutboundHandlerAdapter {
//
//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        UnixTime m = (UnixTime) msg;
//        ByteBuf encoded = ctx.alloc().buffer(4);
//        encoded.writeInt((int)m.value());
//        ctx.write(encoded, promise);
//    }
//}
