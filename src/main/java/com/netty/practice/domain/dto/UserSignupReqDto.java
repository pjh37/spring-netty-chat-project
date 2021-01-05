package com.netty.practice.domain.dto;

import com.netty.practice.domain.User.User;
import com.netty.practice.domain.User.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSignupReqDto {
    private String name;
    private String email;
    private String password;

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(UserRole.USER)
                .build();
    }
}
