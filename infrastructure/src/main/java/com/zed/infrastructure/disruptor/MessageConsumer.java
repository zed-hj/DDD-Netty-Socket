package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.zed.protocol.TransportDTO;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 消息消费者
 *
 * @author Zed
 */
public abstract class MessageConsumer implements WorkHandler<TransportDTO> {

    private static final AtomicLong count = new AtomicLong(0);

    @Override
    public void onEvent(TransportDTO event) throws Exception {
        count.incrementAndGet();
        onEventAndCount(event);
    }

    protected abstract void onEventAndCount(TransportDTO event) throws Exception;

    public static Long getCount() {
        return count.get();
    }

}
