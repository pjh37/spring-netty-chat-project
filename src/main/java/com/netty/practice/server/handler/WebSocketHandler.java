package com.netty.practice.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netty.practice.domain.Payload;
import com.netty.practice.service.ChannelService;
import com.netty.practice.service.ChatService;
import com.netty.practice.service.RoomService;
import com.netty.practice.util.MapperUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private final RoomService roomService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("채널 활성화");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException("unsupported frame type");
        }
        log.info("channel id : "+ctx.channel().id());
        Payload payload= MapperUtil.readValueOrThrow(((TextWebSocketFrame) frame).text(),Payload.class);
        switch (payload.getCommand()){
            case CHAT_LOG_RECEIVE:
                roomService.sendBroadcast(payload.getBody());
                break;
            case ROOM_CREATE:
                roomService.create(payload.getBody());
                break;
            case ROOM_ENTER:
                roomService.addUser(ctx.channel(),payload.getBody());
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.info("채널 비활성화");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
