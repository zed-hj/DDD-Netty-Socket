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

import com.zed.domain.repository.NamespaceRepository;

/**
 * TCP socket configuration contains configuration for main server channel
 * and client channels
 *
 * @see java.net.SocketOptions
 */
public class SocketConfig {

    private boolean tcpNoDelay = true;

    private int tcpSendBufferSize = -1;

    private int tcpReceiveBufferSize = -1;

    private boolean tcpKeepAlive = false;

    private int soLinger = -1;

    private boolean reuseAddress = false;

    private int acceptBackLog = 1024;

    /**
     * 命名空间仓库,抽象成接口支持集群模式的存储
     */
    private NamespaceRepository namespaceRepository;

    /**
     * 默认命名空间仓库,
     */
    public static NamespaceRepository DEFAULT_NAMESPACE_REPOSITORY;


    private static int DEFAULT_PORT = 7000;
    /**
     * 运行端口
     */
    private int port;

    public SocketConfig() {
        this(DEFAULT_NAMESPACE_REPOSITORY);
    }

    public SocketConfig(NamespaceRepository namespaceRepository) {
        this(DEFAULT_PORT, namespaceRepository);
    }

    public SocketConfig(Integer port, NamespaceRepository namespaceRepository) {
        this.port = port;
        this.namespaceRepository = namespaceRepository;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public NamespaceRepository getNamespaceRepository() {
        return namespaceRepository;
    }

    public void setNamespaceRepository(NamespaceRepository namespaceRepository) {
        this.namespaceRepository = namespaceRepository;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public int getTcpSendBufferSize() {
        return tcpSendBufferSize;
    }

    public void setTcpSendBufferSize(int tcpSendBufferSize) {
        this.tcpSendBufferSize = tcpSendBufferSize;
    }

    public int getTcpReceiveBufferSize() {
        return tcpReceiveBufferSize;
    }

    public void setTcpReceiveBufferSize(int tcpReceiveBufferSize) {
        this.tcpReceiveBufferSize = tcpReceiveBufferSize;
    }

    public boolean isTcpKeepAlive() {
        return tcpKeepAlive;
    }

    public void setTcpKeepAlive(boolean tcpKeepAlive) {
        this.tcpKeepAlive = tcpKeepAlive;
    }

    public int getSoLinger() {
        return soLinger;
    }

    public void setSoLinger(int soLinger) {
        this.soLinger = soLinger;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public int getAcceptBackLog() {
        return acceptBackLog;
    }

    public void setAcceptBackLog(int acceptBackLog) {
        this.acceptBackLog = acceptBackLog;
    }

}
