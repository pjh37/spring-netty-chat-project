package com.netty.practice.server;

import com.netty.practice.server.Initializer.WebChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServer {
    private static final int WORKER_THREAD=Runtime.getRuntime().availableProcessors()*2;
    private static final int PORT=8081;
    private static final int CONNECT_TIMEOUT=3000;
    private final WebChannelInitializer channelInitializer;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @PreDestroy
    public void preDestroy(){
        stop();
    }

    public void start(){
        log.info("start");
        bossGroup=new NioEventLoopGroup(1);
        workerGroup=new NioEventLoopGroup(WORKER_THREAD);
        try{
            ServerBootstrap bootstrap=new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,CONNECT_TIMEOUT)
                    .option(ChannelOption.SO_BACKLOG,500)//동시에 수용할 수 있는 소켓 연결 요청 수입니다
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.SO_REUSEADDR,true)
                    .childHandler(channelInitializer);

            ChannelFuture channelFuture=bootstrap.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }finally {
            stop();
        }
    }

    public void stop(){
        bossGroup.shutdownGracefully().awaitUninterruptibly();
        workerGroup.shutdownGracefully().awaitUninterruptibly();
    }
}
