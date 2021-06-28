package com.zed.infrastructure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * netty 配置
 *
 * @author zed
 */
@Data
@ConfigurationProperties("netty")
public class NettyProperties {

}
