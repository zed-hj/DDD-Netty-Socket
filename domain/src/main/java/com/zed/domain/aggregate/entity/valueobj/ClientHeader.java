package com.zed.domain.aggregate.entity.valueobj;

import cn.hutool.core.collection.CollUtil;
import com.zed.domain.constants.NamespaceConstant;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * 解析客户端头部
 *
 * @author Zed
 */
@Getter
public class ClientHeader {

    private FullHttpRequest request;

    private String uri;
    /**
     * gzip, deflate, br
     */
    private String acceptEncoding;
    private final static String ACCEPT_ENCODING = "Accept-Encoding";
    /**
     * zh-CN,zh;q=0.9
     */
    private String acceptLanguage;
    private final static String ACCEPT_LANGUAGE = "Accept-Language";
    /**
     * no-cache
     */
    private String cacheControl;
    private final static String CACHE_CONTROL = "Cache-Control";
    /**
     * Upgrade
     */
    private String connection;
    private final static String CONNECTION = "Connection";
    /**
     * localhost:7000
     */
    private String host;
    private final static String HOST = "Host";
    /**
     * http://localhost:63342
     */
    private String origin;
    private final static String ORIGIN = "Origin";
    /**
     * no-cache
     */
    private String pragma;
    private final static String PRAGMA = "Pragma";
    /**
     * permessage-deflate;client_max_window_bits
     */
    private String secWebSocketExtensions;
    private final static String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";

    /**
     * 客户端唯一键
     */
    private String secWebSocketKey;
    private final static String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";

    private String secWebSocketVersion;
    private final static String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
    /**
     * websocket
     */
    private String upgrade;
    private final static String UPGRADE = "Upgrade";
    /**
     * Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.106 Safari/537.36
     */
    private String userAgent;
    private final static String USER_AGENT = "User-Agent";


    public ClientHeader(FullHttpRequest request) {
        this.request = request;
        HttpHeaders headers = request.headers();
        this.uri = request.uri();
        this.acceptEncoding = headers.get(ACCEPT_ENCODING);
        this.acceptLanguage = headers.get(ACCEPT_LANGUAGE);
        this.cacheControl = headers.get(CACHE_CONTROL);
        this.connection = headers.get(CONNECTION);
        this.host = headers.get(HOST);
        this.origin = headers.get(ORIGIN);
        this.pragma = headers.get(PRAGMA);
        this.secWebSocketExtensions = headers.get(SEC_WEBSOCKET_EXTENSIONS);
        this.secWebSocketKey = headers.get(SEC_WEBSOCKET_KEY);
        this.secWebSocketVersion = headers.get(SEC_WEBSOCKET_VERSION);
        this.upgrade = headers.get(UPGRADE);
        this.userAgent = headers.get(USER_AGENT);
    }

    private Optional<String> filterUriParams(String key) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(request.uri());
        List<String> results = queryDecoder.parameters().get(key);
        return CollUtil.isNotEmpty(results) ? results.stream().findFirst() : Optional.empty();
    }

    /**
     * 先根据url获取namespace,如果为空则按默认的命名空间存储
     *
     * @return
     */
    public String getNamespace() {
        return filterUriParams(NamespaceConstant.NAMESPACE_KEY)
                .orElseGet(() -> NamespaceConstant.DEFAULT_NAMESPACE);
    }

}
