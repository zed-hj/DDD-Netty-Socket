package com.zed.domain.aggregate.entity;

import com.zed.domain.aggregate.NamespaceAggregate;
import com.zed.domain.aggregate.enums.AccessType;
import io.netty.channel.Channel;
import lombok.*;

import java.util.Objects;

/**
 * 暂时租用房间的租户
 *
 * @author zed
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Tenant {

    private TenantId id;

    private NamespaceAggregate.NamespaceId namespaceId;

    private Channel channel;

    private AccessType accessType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(id, tenant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @AllArgsConstructor
    public static class TenantId {

        private String id;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TenantId tenantId = (TenantId) o;
            return Objects.equals(id, tenantId.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }


}
