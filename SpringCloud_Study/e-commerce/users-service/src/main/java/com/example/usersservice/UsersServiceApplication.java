package com.example.usersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import feign.Logger;
import com.example.usersservice.error.FeignErrorDecoder;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UsersServiceApplication {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
	}

	@Bean
	public Logger.Level feignLoggerLevel() { // Feign Client 로그 확인을 위한 Bean 등록
		return Logger.Level.FULL;
	}

	@Bean
	public FeignErrorDecoder getFeignErrorDecoder() {
		return new FeignErrorDecoder();
	}

}
