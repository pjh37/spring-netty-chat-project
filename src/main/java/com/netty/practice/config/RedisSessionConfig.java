package com.netty.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
public class RedisSessionConfig extends AbstractHttpSessionApplicationInitializer {
    public RedisSessionConfig(){
        super(RedisConfig.class);
    }
}
