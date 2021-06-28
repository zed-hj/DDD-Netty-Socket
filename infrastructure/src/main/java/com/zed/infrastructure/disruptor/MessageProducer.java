package com.zed.infrastructure.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.zed.protocol.TransportDTO;

/**
 * 消息生产者
 *
 * @author Zed
 */
public class MessageProducer {

    private RingBuffer<TransportDTO> ringBuffer;

    public MessageProducer(RingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void producer(TransportDTO data) {
        long next = ringBuffer.next();
        try {
            TransportDTO transportDTO = ringBuffer.get(next);
            transportDTO.setId(data.getId());
            transportDTO.setData(data.getData());
            transportDTO.setEvent(data.getEvent());
            transportDTO.setTarget(data.getTarget());
            transportDTO.setType(data.getType());
            transportDTO.setNamespace(data.getNamespace());
            transportDTO.setSessionId(data.getSessionId());
        } finally {
            ringBuffer.publish(next);
        }
    }


}
