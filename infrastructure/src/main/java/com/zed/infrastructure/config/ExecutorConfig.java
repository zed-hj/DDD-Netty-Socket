package com.zed.infrastructure.config;

import com.zed.infrastructure.props.AsyncProperties;
import lombok.AllArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理
 *
 * @author Zed
 */
@EnableAsync(proxyTargetClass = true)
@AllArgsConstructor
@Configuration
public class ExecutorConfig extends AsyncConfigurerSupport {

    private AsyncProperties asyncProperties;

    @Override
    @Bean(name = "disruptorTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("disruptor-executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        //返回值为void的异步方法不会传递异常，当方法中出现异常的时候只会打印日志，重写此方法来自定义异常处理方法
        return new SimpleAsyncUncaughtExceptionHandler();
    }

}
