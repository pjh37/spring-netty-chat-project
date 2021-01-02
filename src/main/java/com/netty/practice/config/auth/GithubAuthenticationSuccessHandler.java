package com.netty.practice.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netty.practice.domain.User.SessionUser;
import com.netty.practice.domain.User.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@Component
@RequiredArgsConstructor
public class GithubAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final HttpSession httpSession;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Principal principal=(Principal)authentication.getPrincipal();

        response.addCookie(new Cookie("name",authentication.getName()));
        response.sendRedirect("http://localhost:3000/login/oauth2/code/github");
    }


}
