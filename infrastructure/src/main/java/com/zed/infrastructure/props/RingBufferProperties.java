package com.zed.infrastructure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zed
 */
@ConfigurationProperties(prefix = "disruptor.ringbuffer")
@Data
public class RingBufferProperties {

    private int bufferSize = 1024;

    private int consumers = 10;

}
