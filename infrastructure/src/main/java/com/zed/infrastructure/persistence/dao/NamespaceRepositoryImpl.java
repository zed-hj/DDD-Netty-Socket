package com.zed.infrastructure.persistence.dao;

import cn.hutool.core.util.StrUtil;
import com.zed.domain.aggregate.Client;
import com.zed.domain.aggregate.Namespace;
import com.zed.domain.factory.NamespaceFactory;
import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dos.ClientBoxDO;
import com.zed.infrastructure.persistence.dos.NamespaceClientChannelDO;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zed
 */
public class NamespaceRepositoryImpl implements NamespaceRepository {

    private NamespaceClientChannelDO namespaceClients = NamespaceClientChannelDO.getInstance();

    private ClientBoxDO clientBox = ClientBoxDO.INSTANCE;

    @Override
    public void connect(Client client) {
        namespaceClients.put(client.namespace(), client.getChannel());
    }

    @Override
    public void disconnect(Client client) {
        String namespace = client.namespace();
        Channel channel = client.getChannel();
        try {
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            namespaceClients.remove(namespace, channel);
        }
    }


    @Override
    public List<Namespace> getNamespaces(String name) {
        List<Namespace> result = new ArrayList<>();
        if (StrUtil.isNotBlank(name)) {
            Set<Channel> channels = namespaceClients.get(name);
            Set<Client> clients = channels.stream().map(clientBox::get).collect(Collectors.toSet());
            result.add(NamespaceFactory.createNamespace(name, clients));
        } else {
            namespaceClients.forEach((k, v) -> {
                Set<Client> clients = v.stream().map(clientBox::get).collect(Collectors.toSet());
                result.add(NamespaceFactory.createNamespace(k, clients));
            });
        }
        return result;
    }
}
