package com.zed.infrastructure.persistence.dao;

import com.zed.domain.listener.DataListener;
import com.zed.infrastructure.disruptor.MessageConsumer;
import com.zed.infrastructure.props.NettyKafkaProperties;
import com.zed.protocol.TransportDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

/**
 * @author Zed
 */
@Slf4j
@Repository
@ConditionalOnProperty(prefix = NettyKafkaProperties.KAFKA_PREFIX, name = "enabled", havingValue = "true")
public class KafkaDataListenerImpl implements DataListener<TransportDTO> {

    @Override
    public void handler(TransportDTO data) {
        log.info("KafkaDataListenerImpl,{}", data.toString());
    }
}
