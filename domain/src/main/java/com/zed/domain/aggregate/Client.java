package com.zed.domain.aggregate;

import cn.hutool.core.util.StrUtil;
import com.zed.domain.aggregate.entity.valueobj.ClientHeader;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Client {

    private ClientId id;

    private ClientHeader clientHeader;

    private Channel channel;

    public void notifyMsg(String msg) {
        this.channel.writeAndFlush(new TextWebSocketFrame(msg));
    }

    @Getter
    public static class ClientId {

        private UUID id;

        public ClientId(String id) {
            this(StrUtil.isNotBlank(id) ? UUID.fromString(id) : null);
        }

        public ClientId(UUID id) {
            if (id == null) {
                this.id = UUID.randomUUID();
            } else {
                this.id = id;
            }
        }
    }

    public String namespace() {
        return this.clientHeader.getNamespace();
    }


}
