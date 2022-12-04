package com.example.kotlinsecurity.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider() {

    fun createAccessToken(phone: String): String {
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


}