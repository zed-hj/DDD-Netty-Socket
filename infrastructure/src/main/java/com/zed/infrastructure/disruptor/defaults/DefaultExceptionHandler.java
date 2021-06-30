package com.zed.infrastructure.disruptor.defaults;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zed
 */

@Slf4j
public class DefaultExceptionHandler<T> implements ExceptionHandler<T> {

    /**
     * 此处异常可以做日志落库或者遇到致命异常关闭客户端连接
     *
     * @param ex       the exception that propagated from the {@link EventHandler}.
     * @param sequence of the event which cause the exception.
     * @param event    being processed when the exception occurred.  This can be null.
     */
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
