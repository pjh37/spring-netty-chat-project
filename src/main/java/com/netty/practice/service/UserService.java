package com.netty.practice.service;

import com.netty.practice.domain.User.*;
import com.netty.practice.domain.dto.UserSignupReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("not exist user"));

        SessionUser sessionUser= SessionUser.builder().user(user).build();
        userRedisRepository.save(sessionUser);
        log.info("UserService loadUserByUsername");
        return new UserAccount(user);
    }

    @Transactional
    public boolean save(UserSignupReqDto request){
        if(existsByName(request.getName())){
            return false;
        }
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(request.toEntity());
        return true;
    }

    @Transactional
    public boolean existsByName(String name){
        return userRepository.existsByName(name);
    }
}
