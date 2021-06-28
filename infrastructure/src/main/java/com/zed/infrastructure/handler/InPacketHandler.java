/**
 * Copyright (c) 2012-2019 Nikita Koksharov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zed.infrastructure.handler;

import com.zed.domain.aggregate.Client;
import com.zed.infrastructure.disruptor.DisruptorFactory;
import com.zed.infrastructure.disruptor.MessageConsumer;
import com.zed.infrastructure.protocol.JsonSupport;
import com.zed.infrastructure.protocol.PacketsMessage;
import com.zed.protocol.TransportDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InPacketHandler extends SimpleChannelInboundHandler<PacketsMessage> {

    private JsonSupport jsonSupport;

    public InPacketHandler(JsonSupport jsonSupport) {
        this.jsonSupport = jsonSupport;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PacketsMessage msg) throws Exception {
        ByteBuf byteBuf = msg.getByteBuf();
        Client client = msg.getClient();
        /**
         * {
         *         "id":"321321",
         *         "data":"321321",
         *         "event":"32131",
         *         "target":"321dcc",
         *         "type":"MQ"
         *     }
         */

        TransportDTO transportDTO = this.jsonSupport.readValue(byteBuf, TransportDTO.class);
        /**
         * 根据命名空间去分配生产者
         */
        String namespace = client.getClientHeader().getNamespace();
        transportDTO.setNamespace(namespace);
        transportDTO.setSessionId(client.getId().getId().toString());

        DisruptorFactory.getProducer(namespace).producer(transportDTO);

    }


}
