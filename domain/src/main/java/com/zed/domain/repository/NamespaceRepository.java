package com.zed.domain.repository;


import com.zed.domain.aggregate.Namespace;
import io.netty.channel.Channel;

import java.util.List;

/**
 * 服务器通道仓库
 *
 * @author zed
 */
public interface NamespaceRepository {

    /**
     * 保存租户
     *
     * @param namespaceId 命名空间Id
     * @param tenantId    租户id
     * @param channel     管道
     */
    void saveTenant(String namespaceId, String tenantId, Channel channel);

    /**
     * 推出房间
     *
     * @param channel
     */
    void quitNamespace(Channel channel);

    /**
     * 获取命名空间
     * @param name
     */
    List<Namespace> getNamespaces(String name);

}
