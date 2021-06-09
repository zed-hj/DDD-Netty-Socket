package com.zed.infrastructure.persistence.dao;

import com.zed.domain.aggregate.entity.Tenant;
import com.zed.domain.factory.NamespaceFactory;
import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dos.ClientChannelDO;
import io.netty.channel.Channel;

/**
 * @author zed
 */
public class NamespaceRepositoryImpl implements NamespaceRepository {

    @Override
    public void saveTenant(String namespaceId, String tenantId, Channel channel) {
        Tenant tenant = NamespaceFactory.createTenant(namespaceId, tenantId, channel);
        ClientChannelDO.getInstance()
                .put(tenant.getNamespaceId().getId(), tenant.getChannel());
    }

    @Override
    public void quitNamespace(Channel channel) {

    }
}
