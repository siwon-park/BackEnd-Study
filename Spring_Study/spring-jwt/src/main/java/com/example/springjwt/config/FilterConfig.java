package com.example.springjwt.config;

import com.example.springjwt.filter.Myfilter;
import com.example.springjwt.filter.Myfilter2;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Myfilter> filter1() {
        FilterRegistrationBean<Myfilter> bean = new FilterRegistrationBean<>(new Myfilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); // 낮은 번호가 필터 중에서 가장 먼저 실행됨
        return bean;
    }

    @Bean
    public FilterRegistrationBean<Myfilter2> filter2() {
        FilterRegistrationBean<Myfilter2> bean = new FilterRegistrationBean<>(new Myfilter2());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}
