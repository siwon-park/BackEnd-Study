package com.example.springjwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springjwt.auth.PrincipalDetails;
import com.example.springjwt.model.User;
import com.example.springjwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 권한이나 인증이 필요한 특정 주소를 요청했을 때 시큐리티의 BasicAuthenticationFilter를 무조건 거치게 되어 있음
// 만약 권한이나 인증이 필요한 주소가 아니라면 해당 필터를 거치지 않음
// 인가 필터
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override // 인증이나 권한이 필요한 주소 요청이 있을 대 해당 필터를 거치게 됨
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        super.doFilterInternal(request, response, chain); // 응답을 2번하게 되어 오류가 나므로 주석 처리
        System.out.println("권한이나 인증이 필요한 주소가 요청됨");

        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        
        // 헤더가 있는지, 헤더가 올바른지 확인
        if (jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) { // 헤더가 올바르지 않으면 반복
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET))
                .build()
                .verify(jwtToken)
                .getClaim("username").asString();
        // username이 null이 아니면 서명이 된 것임
        if (username != null) {
            User user = userRepository.findByUsername(username);
            PrincipalDetails principalDetails = new PrincipalDetails(user);
            // jwt 토큰 서명을 통해서 서명이 정상이면 강제적으로 임의로 Authentication 객체를 만듦
            // 패스워드(credentials)는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까
            // 3번째 인자로 권한을 넣어줘야함(principalDetails.getAuthorities()) => 넣어주지 않으면 postman에서 200을 리턴받더라도 응답이 없음
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            
            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
