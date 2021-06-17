package com.zed.domain.aggregate;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.zed.domain.aggregate.entity.Tenant;
import com.zed.domain.exception.NamespaceException;
import lombok.*;

import java.util.Objects;
import java.util.Set;


/**
 * 一个房间必须要有1个用户,
 * 一个用户只能加入1个房间,
 * 如果房间不存在租户则销毁这个房间释放资源,
 * 所以根据不可变性命名空间为聚合根
 *
 * @author zed
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Namespace {

    private NamespaceId id;

    private Set<Tenant> tenants;

    private void checkAtLeastOneTenant() {
        if (CollUtil.isEmpty(tenants)) {
            throw new NamespaceException("该NameSpace不存在租户");
        }
    }

    public void broadcast(String msg) {
        checkAtLeastOneTenant();
        if (StrUtil.isNotEmpty(msg)) {
            /**
             * 广播通知
             */
            this.tenants.stream().forEach(el -> {
                el.getChannel().writeAndFlush(msg);
            });
        }

    }


    @Getter
    public static class NamespaceId {

        /**
         * 默认的命名空间
         */
        private final static String DEFAULT_NAMESPACE = "DEFAULT_NAMESPACE";

        private String id;


        /**
         * 如果为空则视为加入默认的房间去
         */
        public NamespaceId() {
            this(DEFAULT_NAMESPACE);
        }

        public NamespaceId(String id) {
            if (StrUtil.isNotBlank(id)) {
                this.id = id;
            }
            this.id = DEFAULT_NAMESPACE;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            NamespaceId that = (NamespaceId) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }

}
