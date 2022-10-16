package com.example.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true); // 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 하는 것을 설정(false로 할 경우 JS로 요청해도 응답이 오지 않음)
        corsConfiguration.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 header에 응답을 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 method(get, post, put, delete, ...) 요청을 허용
        source.registerCorsConfiguration("/api/**", corsConfiguration);
        return new CorsFilter(source);
    }

}
