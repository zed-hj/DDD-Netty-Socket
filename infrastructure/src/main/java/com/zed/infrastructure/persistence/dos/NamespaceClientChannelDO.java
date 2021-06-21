package com.zed.infrastructure.persistence.dos;


import cn.hutool.core.collection.CollUtil;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用于维护房间里面得客户端
 *
 * @author zed
 */
public class NamespaceClientChannelDO {

    private static volatile NamespaceClientChannelDO INSTANCE = null;

    private Map<String, Set<Channel>> clients = new ConcurrentHashMap<>(256);

    public void put(String namespaceId, Channel... channel) {
        Set<Channel> channels = clients.get(namespaceId);
        if (CollUtil.isEmpty(channels)) {
            channels = new CopyOnWriteArraySet<>(Stream.of(channel)
                    .collect(Collectors.toList()));

            clients.put(namespaceId, channels);
        } else {
            for (Channel channel1 : channel) {
                channels.add(channel1);
            }
        }
    }

    public Set<Channel> get(String key) {
        return clients.get(key);
    }

    public void forEach(BiConsumer<String, Set<Channel>> action) {
        clients.forEach((K, V) -> {
            action.accept(K, V);
        });
    }

    public static NamespaceClientChannelDO getInstance() {

        if (INSTANCE == null) {
            synchronized (NamespaceClientChannelDO.class) {
                if (INSTANCE == null) {
                    return INSTANCE = new NamespaceClientChannelDO();
                }
            }
        }

        return INSTANCE;
    }


    public boolean remove(String namespaceId, Channel channel) {
        Set<Channel> channels = clients.get(namespaceId);
        return channels.remove(channel);
    }

}
