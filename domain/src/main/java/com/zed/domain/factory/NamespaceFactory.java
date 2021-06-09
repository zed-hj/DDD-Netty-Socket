package com.zed.domain.factory;

import cn.hutool.core.collection.CollUtil;
import com.zed.domain.aggregate.NamespaceAggregate;
import com.zed.domain.aggregate.entity.Tenant;
import io.netty.channel.Channel;
import lombok.NonNull;


/**
 * 命名空间工厂类
 *
 * @author zed
 */
public class NamespaceFactory {


    /**
     * 创建NamespaceAggregate聚合根
     *
     * @param nameSpaceId 命名空间Id
     * @param tenantId    租户Id
     * @param channel     通道
     * @return
     */
    public static NamespaceAggregate createNamespace(String nameSpaceId,
                                                     @NonNull String tenantId,
                                                     @NonNull Channel channel) {
        return NamespaceAggregate.builder()
                .id(new NamespaceAggregate.NamespaceId())
                .tenants(CollUtil.newHashSet(Tenant
                        .builder()
                        .id(new Tenant.TenantId(tenantId))
                        .channel(channel)
                        .namespaceId(new NamespaceAggregate.NamespaceId(nameSpaceId))
                        .build()))
                .build();
    }


    public static Tenant createTenant(String nameSpaceId,
                                      @NonNull String tenantId,
                                      @NonNull Channel channel) {
        return Tenant.builder()
                .id(new Tenant.TenantId(tenantId))
                .namespaceId(new NamespaceAggregate.NamespaceId(nameSpaceId))
                .channel(channel)
                .build();
    }


}
