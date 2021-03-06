package com.zed.infrastructure.persistence.dao;

import com.zed.domain.aggregate.Client;
import com.zed.domain.repository.ClientBoxRepository;
import com.zed.infrastructure.persistence.dos.ClientBoxDO;
import io.netty.channel.Channel;

import java.util.List;
import java.util.UUID;

/**
 * 客户端盒子集合
 *
 * @author zed
 */
public class ClientBoxRepositoryImpl implements ClientBoxRepository {

    private ClientBoxDO clients = ClientBoxDO.INSTANCE;

    @Override
    public Client get(Channel channel) {
        return clients.get(channel);
    }

    @Override
    public Client get(UUID uuid) {
        return clients.get(uuid);
    }

    @Override
    public List<Client> getAll() {
        return clients.getAll();
    }

    @Override
    public void connect(Client client) {
        clients.addClient(client);
        clients.add(client.getChannel(), client);
    }

    @Override
    public void disconnect(Client client) {
        Channel channel = client.getChannel();
        try {
            channel.close();
        } finally {
            clients.remove(channel);
            clients.remove(client.getId().getId());
        }
    }
}
