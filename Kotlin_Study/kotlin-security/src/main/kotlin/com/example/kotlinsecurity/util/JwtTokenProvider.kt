package com.example.kotlinsecurity.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider() {

    fun createAccessToken(phone: String): String { // 엑세스 토큰 생성
        val claims = Jwts.claims().setSubject(phone) // 핸드폰 번호를 저장 (바로 .setSubject를 해도 될듯?)
        // claims["role"] = userRole
        val now = Date()
        return Jwts.builder()
            .setIssuedAt(now)
            .setClaims(claims)
            .setIssuer(JwtProperties.ISSUER)
            .setExpiration(Date(now.time + JwtProperties.ACCESS_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET) // HS256알고리즘으로 암호화
            .compact()
    }

    fun createRefreshToken(phone: String): String { // 리프레시 토큰 생성
        val claims = Jwts.claims().setSubject("refresh") // 리프레시 토큰의 목적은 accessToken의 재발급이므로 refresh로 구분
        val now = Date()
        return Jwts.builder()
            .setIssuedAt(now)
            .setClaims(claims)
            .setIssuer(JwtProperties.ISSUER)
            .setExpiration(Date(now.time + JwtProperties.REFRESH_TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET) // HS256알고리즘으로 암호화
            .compact()
    }


    fun getUserPhone(token: String): String {
        return Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token).body.subject
    }


    fun validateToken(token: String): Boolean { // 토큰 검증
        try {
            val claims = Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token) ?: return false
            return claims.body.expiration.after(Date())
        } catch (e: Exception) {
            return false
        }
    }

}