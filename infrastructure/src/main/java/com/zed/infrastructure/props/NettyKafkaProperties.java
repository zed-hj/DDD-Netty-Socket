package com.zed.infrastructure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.zed.infrastructure.props.NettyKafkaProperties.KAFKA_PREFIX;

/**
 * netty 配置
 *
 * @author zed
 */
@Data
@ConfigurationProperties(KAFKA_PREFIX)
public class NettyKafkaProperties {

    public static final String KAFKA_PREFIX = "netty.kafka";

    private boolean enabled = false;

}
