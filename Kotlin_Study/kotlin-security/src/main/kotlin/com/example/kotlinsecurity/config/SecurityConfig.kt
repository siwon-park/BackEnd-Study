package com.example.kotlinsecurity.config

import com.example.kotlinsecurity.util.JwtAuthenticationFilter
import com.example.kotlinsecurity.util.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Bean
    fun filterChain(http: HttpSecurity) : SecurityFilterChain {
        return http.csrf().disable()
            .httpBasic().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors()
            .and()
            .addFilterBefore(JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/api/user/membertest")
            .hasAuthority("MEMBER")
            .antMatchers("/api/user/admintest")
            .hasAuthority("ADMIN")
            .anyRequest().permitAll()
            .and()
            .build()
    }

}