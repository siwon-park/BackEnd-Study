package com.example.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// SecurityConfig 파일을 만들고 설정을 다 하고나면 기존에 SpringSecurity가 default로 리다이렉트 하는 곳을 막을 수 있다
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(Security Config)가 스프링 필터 체인에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // user/...으로 들어오면 인증이 필요함
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 인증뿐만 아니라 어드민이나 매니저 역할이 필요함
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 어드민 역할이 필요함
                .anyRequest().permitAll() // 다른 요청은 전부 허락
                .and()
                .formLogin().loginPage("/login"); // 인증이 필요할 경우 우리가 설정한 "/login"으로 리다이렉트 가능
    }
}
