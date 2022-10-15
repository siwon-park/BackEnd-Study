package com.example.springsecurity.config;

import com.example.springsecurity.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// SecurityConfig 파일을 만들고 설정을 다 하고나면 기존에 SpringSecurity가 default로 리다이렉트 하는 곳을 막을 수 있다
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터(Security Config)가 스프링 필터 체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // user/...으로 들어오면 인증이 필요함
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 인증뿐만 아니라 어드민이나 매니저 역할이 필요함
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 어드민 역할이 필요함
                .anyRequest().permitAll() // 다른 요청은 전부 허락
                .and()
                .formLogin().loginPage("/loginForm") // 인증이 필요할 경우 우리가 설정한 "/login"으로 리다이렉트 가능
//              // .usernameParameter("username2") // loginForm.html에서 name=username을 username2로 받았을 경우
                .loginProcessingUrl("/login") // /login 주소가 호출되면 시큐리티가 대신 로그인을 진행해줌 => 컨트롤러에 로그인을 만들지 않아도 됨
                .defaultSuccessUrl("/") // 성공 시 메인페이지로 이동. 단, 기존 요청 했던 페이지에서 로그인 요청 시 기존 페이지로 리다이렉트
                .and().oauth2Login().loginPage("/loginForm")
                .userInfoEndpoint().userService(principalOauth2UserService); // 구글 로그인 후 후처리 필요 => 엑세스 토큰 + 사용자 프로필 정보
    }
}
