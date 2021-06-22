package com.zed.domain.repository;


import com.zed.domain.aggregate.Namespace;
import com.zed.domain.listener.ConnectListener;
import com.zed.domain.listener.DisconnectListener;

import java.util.List;

/**
 * 服务器通道仓库
 *
 * @author zed
 */
public interface NamespaceRepository extends ConnectListener, DisconnectListener {

    List<Namespace> getNamespaces(String name);

}
