package com.zed.infrastructure.persistence.dao;

import com.zed.domain.aggregate.Client;
import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dos.NamespaceClientChannelDO;
import io.netty.channel.Channel;

/**
 * @author zed
 */
public class NamespaceRepositoryImpl implements NamespaceRepository {

    private NamespaceClientChannelDO namespaceClients = NamespaceClientChannelDO.getInstance();

    @Override
    public void connect(Client client) {
        namespaceClients.put(client.namespace(), client.getChannel());
    }

    @Override
    public void disconnect(Client client) {
        String namespace = client.namespace();
        Channel channel = client.getChannel();
        namespaceClients.remove(namespace, channel);
    }
}
