/**
 * Copyright (c) 2012-2019 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zed.domain.config;

import cn.hutool.extra.spring.SpringUtil;
import com.zed.domain.repository.ClientBoxRepository;
import com.zed.domain.repository.NamespaceRepository;
import lombok.Data;

/**
 * TCP socket configuration contains configuration for main server channel
 * and client channels
 *
 * @see java.net.SocketOptions
 */
@Data
public class SocketConfig {

    private boolean tcpNoDelay = true;

    private boolean isSSL = false;

    private int tcpSendBufferSize = -1;

    private int tcpReceiveBufferSize = -1;

    private boolean tcpKeepAlive = false;

    private int soLinger = -1;

    private boolean reuseAddress = false;

    private int acceptBackLog = 1024;

    private static int DEFAULT_PORT = 7000;
    /**
     * 运行端口
     */
    private int port;

    public SocketConfig() {
        this.port = DEFAULT_PORT;
    }

    public NamespaceRepository getNamespaceRepository() {
        return SpringUtil.getBean(NamespaceRepository.class);
    }

    public ClientBoxRepository getClientBoxRepository() {
        return SpringUtil.getBean(ClientBoxRepository.class);
    }
}
