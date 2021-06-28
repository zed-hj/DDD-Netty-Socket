package com.zed.infrastructure.persistence.dos;

import com.zed.domain.aggregate.Client;
import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 客户端盒子
 *
 * @author Zed
 */
public enum ClientBoxDO {

    INSTANCE,
    ;

    private final Map<UUID, Client> uuidClients = new ConcurrentHashMap<>();

    private final Map<Channel, Client> channelClients = new ConcurrentHashMap<>();

    public void addClient(Client client) {
        uuidClients.put(client.getId().getId(), client);
    }

    public List<Client> getAll() {
        return uuidClients.values()
                .stream()
                .collect(Collectors.toList());
    }

    public void remove(UUID sessionId) {
        uuidClients.remove(sessionId);
    }

    public Client get(UUID sessionId) {
        return uuidClients.get(sessionId);
    }

    public void add(Channel channel, Client client) {
        channelClients.put(channel, client);
    }

    public void remove(Channel channel) {
        channelClients.remove(channel);
    }

    public Client get(Channel channel) {
        return channelClients.get(channel);
    }

}
