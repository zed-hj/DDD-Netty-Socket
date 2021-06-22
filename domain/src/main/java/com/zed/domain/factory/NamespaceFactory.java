package com.zed.domain.factory;

import com.zed.domain.aggregate.Client;
import com.zed.domain.aggregate.Namespace;
import lombok.NonNull;

import java.util.Set;

/**
 * 客户端工厂类
 *
 * @author zed
 */
public class NamespaceFactory {

    /**
     * 创建命名空间工厂类
     *
     * @param id      房间key
     * @param clients 客户端集合
     * @return
     */
    public static Namespace createNamespace(@NonNull String id, @NonNull Set<Client> clients) {
        return Namespace.builder()
                .id(new Namespace.NamespaceId(id))
                .clients(clients)
                .build();
    }

}
