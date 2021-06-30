package com.zed.infrastructure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.zed.infrastructure.props.NettyRocketMQProperties.ROCKET_PREFIX;

/**
 * netty 配置
 *
 * @author zed
 */
@Data
@ConfigurationProperties(ROCKET_PREFIX)
public class NettyRocketMQProperties {

    public static final String ROCKET_PREFIX = "netty.rocket";

    private boolean enabled = false;

}
