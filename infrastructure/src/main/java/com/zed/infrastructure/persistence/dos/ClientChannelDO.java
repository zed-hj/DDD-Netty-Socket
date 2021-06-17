package com.zed.infrastructure.persistence.dos;


import cn.hutool.core.collection.CollUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/**
 * 用于保存客户端的通道
 *
 * @author zed
 */
public class ClientChannelDO {

    private static volatile ClientChannelDO INSTANCE = null;

    private Map<String, Set<Channel>> clients = new ConcurrentHashMap<>(256);

    public void put(String id, Channel... channel) {
        Set<Channel> channels = clients.get(id);
        if (CollUtil.isEmpty(channels)) {
            channels = CollUtil.newHashSet(channel);
            clients.put(id, channels);
        } else {
            for (Channel channel1 : channel) {
                channels.add(channel1);
            }
        }
    }

    public Set<Channel> get(String key) {
        return clients.get(key);
    }

    public Set<String> forEach(BiConsumer<String, Set<Channel>> action) {
        clients.forEach((K, V) -> {
            action.accept(K, V);
        });
    }

    public static ClientChannelDO getInstance() {

        if (INSTANCE == null) {
            synchronized (ClientChannelDO.class) {
                if (INSTANCE == null) {
                    return INSTANCE = new ClientChannelDO();
                }
            }
        }

        return INSTANCE;
    }

    public void sendToAll(TextWebSocketFrame msg) {
        if (!clients.isEmpty()) {
            clients.forEach((k, v) -> {
                System.out.println("向房间里" + k + "用户" + v + "发送:" + msg.text());
                v.forEach(el -> el.writeAndFlush(msg));
            });
        }
    }


}
