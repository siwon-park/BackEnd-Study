package com.example.kotlinsecurity

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class KotlinSecurityApplication {
	@Bean
	fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
		return BCryptPasswordEncoder()
	}
}

fun main(args: Array<String>) {
	runApplication<KotlinSecurityApplication>(*args)
}
