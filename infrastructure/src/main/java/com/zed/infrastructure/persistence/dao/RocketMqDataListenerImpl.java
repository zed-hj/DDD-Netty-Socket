package com.zed.infrastructure.persistence.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zed.domain.listener.DataListener;
import com.zed.infrastructure.props.NettyRocketMQProperties;
import com.zed.protocol.TransportDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author Zed
 */
@Slf4j
@Repository
@ConditionalOnProperty(prefix = NettyRocketMQProperties.ROCKET_PREFIX, name = "enabled", havingValue = "true")
public class RocketMqDataListenerImpl implements DataListener<TransportDTO> {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public RocketMqDataListenerImpl() {
        log.info("RocketMqDataListenerImpl");
    }

    @Override
    public void handler(TransportDTO data) {
//        log.info("KafkaDataListenerImpl,{}", data.toString());
        try {
            String json = OBJECT_MAPPER.writeValueAsString(data);
            rocketMQTemplate.send(data.getTarget(), MessageBuilder.withPayload(json).build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
