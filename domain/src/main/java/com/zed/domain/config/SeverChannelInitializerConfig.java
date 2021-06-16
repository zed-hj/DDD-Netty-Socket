package com.zed.domain.config;

import com.zed.domain.aggregate.handler.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author zed
 */
public class SeverChannelInitializerConfig extends ChannelInitializer<Channel> {

    private SocketConfig socketConfig;

    public SeverChannelInitializerConfig(SocketConfig socketConfig) {
        this.socketConfig = socketConfig;
    }

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
        /**
         * start解析GET和POST的URL和RequestBody 参数的
         */
        pipeline.addLast("http-codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        /**
         * end解析GET和POST的URL和RequestBody 参数的
         */
        /**
         * 来解决大文件或者码流传输过程中可能发生的内存溢出问题。
         */
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast("handler", new WebSocketHandler(socketConfig));

//        pipeline.addLast(new TimeEncoderHandler());
//        pipeline.addLast(new TimeServerHandler());
//        pipeline.addLast(new ServerHandler());

    }

}
