package com.example.kotlinsecurity.util

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


open class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
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
            val authentication: Authentication? = jwtTokenProvider.getAuthentication(req)
            SecurityContextHolder.getContext().authentication = authentication  // SecurityContextHolder에 인증 정보를 저장
            // SecurityContextHolder.getContext().setAuthentication(authentication); 자바 구문과 위 구문은 동일(위의 구문을 코틀린에서는 권장)
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        chain?.doFilter(request, response)
    }

}