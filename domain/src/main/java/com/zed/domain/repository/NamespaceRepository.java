package com.zed.domain.repository;


import com.zed.domain.aggregate.Namespace;
import com.zed.domain.listener.ConnectListener;
import com.zed.domain.listener.DisconnectListener;
import io.netty.channel.Channel;

import java.util.List;
import java.util.UUID;

/**
 * 服务器通道仓库
 *
 * @author zed
 */
public interface NamespaceRepository extends ConnectListener, DisconnectListener {


}
