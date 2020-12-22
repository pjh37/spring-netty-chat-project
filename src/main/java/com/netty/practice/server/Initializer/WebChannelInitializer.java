package com.netty.practice.server.Initializer;

import com.netty.practice.server.handler.WebSocketHandler;
import com.netty.practice.service.ChannelService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebChannelInitializer extends ChannelInitializer<SocketChannel> {
    private static final int MAX_CONTENT_LENGTH=65536;
    private final WebSocketHandler webSocketHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();

        pipeline.addLast(new HttpServerCodec())
                .addLast(new HttpObjectAggregator(MAX_CONTENT_LENGTH))
                .addLast(new WebSocketServerCompressionHandler())
                .addLast(new WebSocketServerProtocolHandler("/ws",null,true))
                .addLast(webSocketHandler)
                .addLast(new LoggingHandler(LogLevel.INFO))
                .addLast(new IdleStateHandler(0,0,180));
    }
}
