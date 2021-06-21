package com.zed.domain.listener;

import com.zed.domain.aggregate.Client;

/**
 * 连接监听器
 *
 * @author Zed
 */
public interface ConnectListener {

    void connect(Client client);

}
