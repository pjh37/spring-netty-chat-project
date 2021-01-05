package com.netty.practice.domain.User;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.netty.practice.domain.User.*;
import java.util.Arrays;
import java.util.Collections;

public class UserAccount extends org.springframework.security.core.userdetails.User {
    private User user;
    public UserAccount(User user){
        super(user.getName(),user.getPassword(),Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole())));
        this.user=user;
        System.out.println("UserAccount: "+user.getName());
        System.out.println("UserAccount: "+user.getPassword());
    }

    public User getUser() {
        return user;
    }
}
