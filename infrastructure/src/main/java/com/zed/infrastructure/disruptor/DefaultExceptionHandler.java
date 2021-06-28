package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zed
 */

@Slf4j
public class DefaultExceptionHandler<T> implements ExceptionHandler<T> {

    @Override
    public void handleEventException(Throwable ex, long sequence, T event) {
        log.info("handleEventException");
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        log.info("handleOnStartException");
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        log.info("handleOnShutdownException");
    }

}
