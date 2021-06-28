package com.zed.infrastructure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 异步配置
 *
 * @author zed
 */
@Data
@ConfigurationProperties("disruptor.async")
public class AsyncProperties {
    /**
     * 异步核心数,默认: 2
     */
    private int corePoolSize = 100;
    /**
     * 异步最大线程数，默认：50
     */
    private int maxPoolSize = 500;
    /**
     * 队列容量，默认：10000
     */
    private int queueCapacity = 10000;
    /**
     * 线程存活时间，默认：300
     */
    private int keepAliveSeconds = 300;
}
