package com.netty.practice.domain.User;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import java.util.Arrays;
import java.util.Collections;

public class UserAccount extends User {
    public UserAccount(String email,String password,UserRole role){
        super(email,password,Arrays.asList(new SimpleGrantedAuthority("ROLE_"+role.getKey())));
    }
}
