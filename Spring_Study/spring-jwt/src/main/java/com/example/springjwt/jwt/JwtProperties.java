package com.example.springjwt.jwt;

public interface JwtProperties {
    String SECRET = "siwonpark"; // 비밀 키 값
    int EXPIRATION_TIME = 172800000; // 2일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";

}
