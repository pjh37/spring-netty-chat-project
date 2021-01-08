package com.netty.practice.config;

import com.netty.practice.config.auth.CustomLogoutSuccessHandler;
import com.netty.practice.config.auth.CustomOAuth2UserService;
import com.netty.practice.config.auth.GithubAuthenticationSuccessHandler;
import com.netty.practice.config.auth.LoginFailHandler;
import com.netty.practice.domain.User.UserRole;
import com.netty.practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final GithubAuthenticationSuccessHandler githubAuthenticationSuccessHandler;

    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new LoginFailHandler();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/images/**","/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/**","/login/**","/css/**","/images/**","/js/**","/h2-console/**","/join/**").permitAll()
                    .antMatchers("/api/**").hasRole(UserRole.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .clearAuthentication(true)
                    .logoutSuccessUrl(baseUrl)
                    .deleteCookies("JSESSIONID")
                .and()
                    .oauth2Login()
                    .successHandler(githubAuthenticationSuccessHandler)
                    .userInfoEndpoint()
                    .userService(customOAuth2UserService);
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("email")
                .permitAll();
        http.logout().logoutSuccessUrl("/");
    }
}
