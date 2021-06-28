package com.zed.protocol;

import java.io.Serializable;

/**
 * @author Zed
 */
public enum ServiceTransportType implements Serializable {

    /**
     * 队列
     */
    MQ,
    /**
     * 在线
     */
    ONLINE

}
