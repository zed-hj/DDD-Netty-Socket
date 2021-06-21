package com.zed.domain.aggregate.enums;

import cn.hutool.core.util.StrUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 传输方式
 *
 * @author Zed
 */
@Getter
@AllArgsConstructor
public enum Transport {

    WEB_SOCKET("websocket"),
    POLLING("polling"),
    ;

    private String transport;

    public static List<String> getUriParams(FullHttpRequest request) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(request.uri());
        return queryDecoder.parameters().get("transport");
    }

    public boolean isWebSocket(FullHttpRequest request) {
        String transport = request.headers().get("Upgrade");
        return StrUtil.equals(this.getTransport(), transport);
    }

}
