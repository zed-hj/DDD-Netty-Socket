package com.zed.domain.service;

import cn.hutool.json.JSONUtil;
import com.zed.domain.result.Result;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author Zed
 */

public interface WebSocketResponse {

    /**
     * 服务器向客户端响应消息
     *
     * @param ctx
     * @param result
     */
    default <T> void sendMessage(ChannelHandlerContext ctx, Result<T> result) {
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSONUtil.toJsonStr(result));
        //服务端向客户端发送数据
        ChannelFuture cf = ctx.channel().writeAndFlush(textWebSocketFrame);
    }


}
