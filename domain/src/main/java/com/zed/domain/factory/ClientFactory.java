package com.zed.domain.factory;

import com.zed.domain.aggregate.Client;
import com.zed.domain.aggregate.entity.valueobj.ClientHeader;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.NonNull;

/**
 * 客户端工厂类
 *
 * @author zed
 */
public class ClientFactory {

    /**
     * 创建客户端工厂类
     *
     * @param id      sessionId
     * @param request 连接信息
     * @param channel netty 通道
     * @return
     */
    public static Client createClient(String id,
                                      @NonNull FullHttpRequest request,
                                      @NonNull Channel channel) {

        return Client.builder()
                .id(new Client.ClientId(id))
                .clientHeader(new ClientHeader(request))
                .channel(channel)
                .build();
    }

}
