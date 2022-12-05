package com.example.kotlinsecurity.util

import com.example.kotlinsecurity.auth.AuthUserDetailService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val authUserDetailService: AuthUserDetailService,
) : GenericFilterBean() {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = request as HttpServletRequest // request를 HttpServletRequest로 호출함 => 헤더를 가지고 오기 위함
        val header = req.getHeader(JwtProperties.HEADER_STRING) // Authorization
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) { // 헤더가 null이거나, Bearer 로 시작하지 않으면 필터에 걸림
            chain?.doFilter(request, response) // chain?으로 해야함
            return
        }
        try {
            val authentication: Authentication? = getAuthentication(req)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        chain?.doFilter(request, response)
    }

    @Transactional(readOnly = true)
    fun getAuthentication(request: HttpServletRequest): Authentication? { // 코틀린에서 클래스와 메서드는 기본적으로 final이나 상속을 허용하려면 open 키워드를 붙여야한다.
        val token = request.getHeader(JwtProperties.HEADER_STRING)
        if (token != null && jwtTokenProvider.validateToken(token)) { // 유효한 토큰일 경우
            val authUserDetails = authUserDetailService.loadUserByUsername(jwtTokenProvider.getUserPhone(token))
            return UsernamePasswordAuthenticationToken(jwtTokenProvider.getUserPhone(token), null, authUserDetails!!.authorities)

        }
        return null
    }


}