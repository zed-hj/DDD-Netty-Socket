package com.zed.infrastructure.persistence.dao;

import cn.hutool.core.collection.CollUtil;
import com.zed.domain.aggregate.Namespace;
import com.zed.domain.aggregate.entity.Tenant;
import com.zed.domain.factory.NamespaceFactory;
import com.zed.domain.repository.NamespaceRepository;
import com.zed.infrastructure.persistence.dos.ClientChannelDO;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    /**
     * 暂定规则，如果name获取为空，则查询全部,并转换
     *
     * @param name
     * @return
     */
    @Override
    public List<Namespace> getNamespaces(String name) {
        List<Namespace> data = new ArrayList<>();

        Set<Channel> channels = ClientChannelDO.getInstance().get(name);

        if (CollUtil.isNotEmpty(channels)) {
            NamespaceFactory.createNamespace(name,)
        }


        return data;
    }


}
