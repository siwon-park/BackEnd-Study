package com.example.kotlinsecurity.util

import com.example.kotlinsecurity.auth.AuthUserDetailService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val authUserDetailService: AuthUserDetailService,
) {

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

    @Transactional(readOnly = true)
    fun getAuthentication(request: HttpServletRequest): Authentication? { // 코틀린에서 클래스와 메서드는 기본적으로 final이나 상속을 허용하려면 open 키워드를 붙여야한다.
        val token = request.getHeader(JwtProperties.HEADER_STRING).replace("Bearer ", "")
        if (token != null && validateToken(token)) { // 유효한 토큰일 경우
            val authUserDetails = authUserDetailService.loadUserByUsername(getUserPhone(token))
            return UsernamePasswordAuthenticationToken(authUserDetails, null, authUserDetails!!.authorities)
        }
        return null
    }

}