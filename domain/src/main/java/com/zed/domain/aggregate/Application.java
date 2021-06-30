package com.zed.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 以后做法 从Nacos 或者 Eurka 加载服务器 判断是否能转发
 * 或者管理 @link TransportDTO target 地址是否有效
 *
 * @author Zed
 */
@Getter
@Builder
public class Application {

    @Getter
    @AllArgsConstructor
    public static class Id {
        private String id;
    }
}
