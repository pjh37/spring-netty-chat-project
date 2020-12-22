package com.netty.practice.server.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netty.practice.domain.Payload;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class WebPayloadDecoder extends MessageToMessageDecoder<String> {
    private final ObjectMapper objectMapper=new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println("WebPayloadDecoder : "+msg);
        Payload payload=objectMapper.readValue(msg, Payload.class);
        out.add(payload);
    }
}
