package com.zed.domain.aggregate.enums;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


/**
 * Channel Attributes 管理
 *
 * @author Zed
 */
@Getter
@AllArgsConstructor
public enum Attributes {

    SID(AttributeKey.<String>valueOf("sid")),

    TOKEN(AttributeKey.<String>valueOf("token")),

    NAMESPACES(AttributeKey.<String>valueOf("namespaces")),
    ;

    private AttributeKey attributeKey;


    public static List<String> getUriParams(FullHttpRequest request, Attributes key) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(request.uri());
        return queryDecoder.parameters().get(key.getAttributeKey().name());
    }

}
