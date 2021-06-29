package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.ProducerType;
import com.zed.infrastructure.disruptor.defaults.DefaultConsumer;
import com.zed.infrastructure.disruptor.defaults.DefaultExceptionHandler;
import com.zed.infrastructure.props.RingBufferProperties;
import com.zed.protocol.TransportDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Zed
 */
@Slf4j
@Component
public class TransportDisruptorTemplate extends DisruptorTemplate<TransportDTO> implements InitializingBean {

    @Resource
    private RingBufferProperties ringBufferProperties;

    @Override
    protected EventFactory<TransportDTO> eventFactory() {
        return new EventFactory() {
            @Override
            public TransportDTO newInstance() {
                return new TransportDTO();
            }
        };
    }

    @Override
    protected int bufferSize() {
        return ringBufferProperties.getBufferSize();
    }

    @Override
    protected ProducerType producerType() {
        return ProducerType.MULTI;
    }

    @Override
    protected ExceptionHandler<TransportDTO> exceptionHandler() {
        return new DefaultExceptionHandler();
    }

    @Override
    protected WorkHandler[] getHandler() {
        WorkHandler[] workHandlers = new WorkHandler[ringBufferProperties.getConsumers()];
        for (int i = 0; i < workHandlers.length; i++) {
            workHandlers[i] = new DefaultConsumer();
        }
        return workHandlers;
    }

    @Override
    protected WaitStrategyType waitStrategy() {
        return WaitStrategyType.BLOCKING;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.init();
    }
}
