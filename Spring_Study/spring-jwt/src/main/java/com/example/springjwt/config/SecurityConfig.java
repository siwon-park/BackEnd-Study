package com.example.springjwt.config;

import com.example.springjwt.filter.Myfilter;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity // 시큐리티 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 커스텀 필터는 시큐리티 필터가 아니기 때문에 addFilter가 아니라, addFilterBefore/After로 등록해줘야함
//        http.addFilterBefore(new Myfilter(), BasicAuthenticationFilter.class); // 이렇게 시큐리티 필터에 걸어줘도 되지만, 따로 걸 수도 있음(FilterConfig를 통해 커스텀 등록)
        // 단, addFilterAfter로 등록하거나, FilterConfig로 등록시 커스텀 필터는 항상 시큐리티 필터 다음으로 우선순위를 가짐
        // 시큐리티 필터보다 우선적으로 커스텀 필터를 동작하고 싶다면 addFilterBefore를 사용하고, 시큐리티 필터 간 우선순위를 파악해서 해당 시큐리티 필터보다 먼저 동작하게 하면 됨
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않겠다는 의미(stateless 서버) => Bearer 방식을 적용하기 위해 설정 필요
        .and()
                .addFilter(corsFilter) // @CrossOrigin => 인증이 없을 때, 인증이 있을 때는 시큐리티 필터에 등록해야함
                .formLogin().disable() // jwt를 사용하므로 formLogin 방식을 사용하지 않음(폼 태그 방식으로 로그인을 하지 않음)
                .httpBasic().disable() // 기본적인 http 로그인 방식을 사용하지 않음 => Bearer 방식 사용
                .authorizeRequests()
                .antMatchers("/api/v1/user/**") // 해당 주소로 요청이 들어오면
                .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 해당 권한이 있으면 접속 가능
                .antMatchers("/api/v1/manager/**")
                .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll(); // 다른 요청은 전부 허락

    }
}
