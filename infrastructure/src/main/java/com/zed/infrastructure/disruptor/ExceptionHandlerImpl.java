package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Zed
 */
@Configuration
public class ExceptionHandlerImpl {

    @Bean
    public ExceptionHandler exceptionHandler1() {
        return new DefaultExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(ExceptionHandler.class)
    public ExceptionHandler exceptionHandler() {
        return new DefaultExceptionHandler();
    }


}
