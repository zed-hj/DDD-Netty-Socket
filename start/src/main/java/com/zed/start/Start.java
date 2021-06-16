package com.zed.start;

import com.zed.domain.aggregate.entity.Server;
import com.zed.domain.config.SocketConfig;
import com.zed.infrastructure.persistence.dao.NamespaceRepositoryImpl;

/**
 * 服务启动器
 *
 * @author zed
 */
public class Start {

    public static void main(String[] args) throws Exception {
        new Server(new SocketConfig(new NamespaceRepositoryImpl()))
                .run();
    }

}
