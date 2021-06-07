package com.zed.infrastructure;

import com.zed.infrastructure.handler.ServerHandler;
import com.zed.infrastructure.handler.TimeServerHandler;
import com.zed.infrastructure.handler.serialization.TimeEncoderHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;

public class SeverChannelInitializer extends ChannelInitializer<Channel> {


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /**
         * 客户端建立连接加入哈希时间轮的查看
         */
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        /**
         * 加入多个处理器
         */
        addSocketHandles(pipeline);
    }


    private void addSocketHandles(ChannelPipeline pipeline) {
        pipeline.addLast(new TimeEncoderHandler());
        pipeline.addLast(new TimeServerHandler());
//        pipeline.addLast(new ServerHandler());

    }

}
