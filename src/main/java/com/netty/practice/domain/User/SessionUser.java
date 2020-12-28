package com.netty.practice.domain.User;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Getter
@RedisHash("user")
public class SessionUser implements Serializable {
    @Id
    private Long id;
    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public SessionUser(Long id,String name,String email,UserRole role){
        this.id=id;
        this.name=name;
        this.email=email;
        this.role=role;
    }
}
