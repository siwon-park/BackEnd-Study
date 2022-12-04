package com.example.kotlinsecurity.util

interface JwtProperties {

    companion object {
        const val ISSUER: String = "siwonpark"
        const val SECRET: String = "dyAeHubOOc8KaOfYB6XEQoEj1QzRlVgtjNL8PYs1A1tymZvvqkcEU7L1imkKHeDa"
        const val TOKEN_PREFIX: String = "Bearer "
        const val HEADER_STRING: String = "Authorization"
        const val ACCESS_TOKEN_EXPIRATION: Long = 60 * 60 * 1000L // 1시간 설정
        const val REFRESH_TOKEN_EXPIRATION: Long = 2 * 60 * 60 * 1000L // 2시간 설정
    }

}