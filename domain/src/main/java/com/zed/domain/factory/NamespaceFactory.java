package com.zed.domain.factory;

import com.zed.domain.aggregate.Namespace;
import com.zed.domain.aggregate.entity.Tenant;
import io.netty.channel.Channel;
import lombok.NonNull;

import java.util.Set;
import java.util.stream.Collectors;


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
     * @param tenantIds   租户Id
     * @param channel     通道
     * @return
     */
    public static Namespace createNamespace(String nameSpaceId,
                                            @NonNull Channel channel,
                                            Set<String> tenantIds) {
        Set<Tenant> tenants = tenantIds.stream().map(el -> {
            return Tenant.builder()
                    .id(new Tenant.TenantId(el))
                    .channel(channel)
                    .namespaceId(new Namespace.NamespaceId(nameSpaceId))
                    .build();
        }).collect(Collectors.toSet());

        return Namespace.builder()
                .id(new Namespace.NamespaceId())
                .tenants(tenants)
                .build();
    }


    public static Tenant createTenant(String nameSpaceId,
                                      @NonNull String tenantId,
                                      @NonNull Channel channel) {
        return Tenant.builder()
                .id(new Tenant.TenantId(tenantId))
                .namespaceId(new Namespace.NamespaceId(nameSpaceId))
                .channel(channel)
                .build();
    }


}
