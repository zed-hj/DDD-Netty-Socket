package com.zed.domain.repository;


import com.zed.domain.aggregate.Client;
import com.zed.domain.listener.ConnectListener;
import com.zed.domain.listener.DisconnectListener;
import io.netty.channel.Channel;

import java.util.UUID;

/**
 * ClientBox仓库
 *
 * @author zed
 */
public interface ClientBoxRepository extends ConnectListener, DisconnectListener {

    Client get(Channel channel);

    Client get(UUID uuid);

}
