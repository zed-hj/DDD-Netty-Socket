package com.zed.domain.service;


import com.zed.domain.aggregate.Client;
import com.zed.domain.listener.ConnectListener;
import com.zed.domain.listener.DisconnectListener;
import com.zed.domain.repository.ClientBoxRepository;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 客户端领域服务
 */
@Service
public class ClientService {

    @Resource
    private List<ConnectListener> connectListeners;

    @Resource
    private List<DisconnectListener> disconnectListeners;

    @Resource
    private ClientBoxRepository clientBoxRepository;


    public Client get(Channel channel) {
        return clientBoxRepository.get(channel);
    }

    public Client get(UUID uuid) {
        return clientBoxRepository.get(uuid);
    }

    /**
     * 散布连接事件
     *
     * @param client
     */
    public void connects(Client client) {
        connectListeners.stream().forEach(el -> el.connect(client));
    }

    /**
     * 散布关闭连接事件
     *
     * @param client
     */
    public void disconnect(Client client) {
        disconnectListeners.stream().forEach(el -> {
            if (client != null) {
                el.disconnect(client);
            }
        });
    }

    /**
     * 根据通道处理程序上下文关闭连接事件
     *
     * @param ctx
     */
    public void disconnect(ChannelHandlerContext ctx) {
        Client client = get(ctx.channel());
        disconnectListeners.stream().forEach(el -> {
            if (client != null) {
                el.disconnect(client);
            }
        });
    }

}
