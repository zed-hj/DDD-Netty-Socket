package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.zed.protocol.TransportDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * @author Zed
 */
@Configuration
@AllArgsConstructor
public class DisruptorFactory implements InitializingBean {

    private static RingBuffer<TransportDTO> RING_BUFFER = null;

    private RingBufferProperties ringBufferProperties;

    private ExceptionHandler exceptionHandler;

    private Executor disruptorTaskExecutor;

    /**
     * 多生产者模式,并缓存起来
     */
    private final static Map<String, MessageProducer> REUSABLE_PRODUCER = new ConcurrentHashMap<>();

    public void init() {

        RING_BUFFER = RingBuffer.create(ProducerType.MULTI,
                new EventFactory<TransportDTO>() {
                    @Override
                    public TransportDTO newInstance() {
                        return new TransportDTO();
                    }
                },
                ringBufferProperties.getBufferSize(),
                new BlockingWaitStrategy());

        SequenceBarrier sequenceBarrier = RING_BUFFER.newBarrier();

        WorkerPool workerPool = new WorkerPool(RING_BUFFER,
                sequenceBarrier,
                exceptionHandler,
                messageConsumers());

        RING_BUFFER.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(disruptorTaskExecutor);
    }

    public static MessageProducer getProducer(String key) {
        MessageProducer messageProducer = REUSABLE_PRODUCER.get(key);
        if (messageProducer == null) {
            messageProducer = new MessageProducer(RING_BUFFER);
            REUSABLE_PRODUCER.put(key, messageProducer);
        }
        return messageProducer;
    }

    private MessageConsumer[] messageConsumers() {
        int consumers = this.ringBufferProperties.getConsumers();
        MessageConsumer[] messageConsumers = new MessageConsumer[consumers];
        for (int i = 0; i < consumers; i++) {
            messageConsumers[i] = new DefaultConsumer();
        }
        return messageConsumers;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
