package com.zed.infrastructure.protocol;

import com.zed.domain.aggregate.Client;
import com.zed.domain.aggregate.entity.valueobj.ClientHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Zed
 */
@Data
@AllArgsConstructor
public class PacketsMessage {

    private Channel channel;

    private Client client;

    private ByteBuf byteBuf;

}
