package com.zed.infrastructure.disruptor;

import cn.hutool.extra.spring.SpringUtil;
import com.zed.domain.listener.DataListener;
import com.zed.protocol.TransportDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zed
 */
@Slf4j
public class DefaultConsumer extends MessageConsumer {

    @Override
    protected void onEventAndCount(TransportDTO event) throws Exception {
        List<DataListener> values = SpringUtil.getBeansOfType(DataListener.class).values()
                .stream()
                .collect(Collectors.toList());

        values.parallelStream().forEach(el -> {
            el.handler(event);
        });
    }

}
