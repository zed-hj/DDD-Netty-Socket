package com.zed.start;

import cn.hutool.extra.spring.SpringUtil;
import com.zed.infrastructure.Server;
import com.zed.infrastructure.config.SocketConfig;
import com.zed.infrastructure.disruptor.MessageConsumer;
import com.zed.infrastructure.props.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * 服务启动器
 *
 * @author zed
 */
@SpringBootApplication(scanBasePackages = "com.zed")
@EnableConfigurationProperties(value = {RingBufferProperties.class, DisruptorAsyncProperties.class, NettyProperties.class,
        NettyKafkaProperties.class,
        NettyRocketMQProperties.class})
@Import(SpringUtil.class)
@Slf4j
public class Start {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(Start.class);

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.info("消费数量为 ： {}", MessageConsumer.getCount());
                }
            }
        }).start();

        new Server(new SocketConfig()).run();

    }

}
