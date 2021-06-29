package com.zed.infrastructure.persistence.dao;

import com.zed.domain.listener.DataListener;
import com.zed.infrastructure.props.NettyKafkaProperties;
import com.zed.protocol.TransportDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Zed
 */
@Slf4j
@Repository
@ConditionalOnProperty(prefix = NettyKafkaProperties.KAFKA_PREFIX, name = "enabled", havingValue = "true")
public class KafkaDataListenerImpl implements DataListener<TransportDTO> {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaDataListenerImpl() {
        log.info("111");
    }

    @Override
    public void handler(TransportDTO data) {
//        log.info("KafkaDataListenerImpl,{}", data.toString());
        kafkaTemplate.send(data.getTarget(), data.getData());
    }
}
