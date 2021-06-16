//package com.zed.domain.aggregate.handler.serialization;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//
//import java.util.List;
//
///**
// * Time 解码
// * 或者继承ReplayingDecoder不用自己去判断字节数
// *
// * @author 15901019193
// */
//public class TimeDecoderHandler extends ByteToMessageDecoder {
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        if (in.readableBytes() < 4) {
//            return;
//        }
//        out.add(new UnixTime(in.readUnsignedInt()));
//    }
//}
