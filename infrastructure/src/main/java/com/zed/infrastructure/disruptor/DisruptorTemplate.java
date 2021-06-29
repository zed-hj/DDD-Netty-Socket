package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.Data;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author Zed
 */
@Data
public abstract class DisruptorTemplate<E> {

    @Resource
    private Executor disruptorTaskExecutor;
    /**
     * Disruptor 对象
     */
    private Disruptor<E> disruptor;

    /**
     * RingBuffer 对象
     */
    private RingBuffer<E> ringBuffer;

    /**
     * 事件工厂
     *
     * @return EventFactory
     */
    protected abstract EventFactory<E> eventFactory();

    /**
     * 队列大小
     *
     * @return 队列长度，必须是2的幂
     */
    protected abstract int bufferSize();

//    /**
//     * 线程池
//     *
//     * @return 线程实现
//     */
//    protected abstract ThreadFactory threadFactory();

    /**
     * 生产者类型
     *
     * @return
     */
    protected abstract ProducerType producerType();

    protected abstract ExceptionHandler<E> exceptionHandler();

    /**
     * 事件消费者
     *
     * @return WorkHandler[]
     */
    protected abstract WorkHandler[] getHandler();

//    private Map<String, MessageProducer> cacheMultiProducer = new ConcurrentHashMap<>();
//
//    private MessageProducer cacheProducer;

    /**
     * 等待策略
     *
     * @return
     */
    protected abstract WaitStrategyType waitStrategy();

    public DisruptorTemplate() {
    }

    protected void init() {

        disruptor = new Disruptor<E>(eventFactory(),
                bufferSize(),
                disruptorTaskExecutor,
                producerType(),
                waitStrategy().getWaitStrategy());

        disruptor.setDefaultExceptionHandler(exceptionHandler());

        disruptor.handleEventsWithWorkerPool(getHandler());

        this.ringBuffer = disruptor.start();


    }


    /**
     * 生产消息
     *
     * @param consumer
     */
    public void publishEvent(Consumer<E> consumer) {
        long next = this.ringBuffer.next();
        try {
            E e = this.ringBuffer.get(next);
            consumer.accept(e);
        } finally {
            ringBuffer.publish(next);
        }
    }

}


