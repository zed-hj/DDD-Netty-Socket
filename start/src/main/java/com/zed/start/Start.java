package com.zed.start;

import cn.hutool.extra.spring.SpringUtil;
import com.zed.infrastructure.Server;
import com.zed.infrastructure.config.SocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * 服务启动器
 *
 * @author zed
 */
@SpringBootApplication(scanBasePackages = "com.zed")
@Import(SpringUtil.class)
public class Start {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Start.class);

        new Server(new SocketConfig()).run();

    }

}
