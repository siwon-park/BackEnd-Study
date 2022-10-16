package com.example.springjwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springjwt.auth.PrincipalDetails;
import com.example.springjwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있는데
// /login 요청 시 username, password를 전송하면(post) UsernamePasswordAuthenticationFilter가 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override // /login 요청을 하면 로그인 시도를 위해 실행되는 함수
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도 중");
        // 1. username, password를 받아서
        ObjectMapper objectMapper = new ObjectMapper();
        // LoginRequestDto loginRequestDto = null; // DTO 사용 시
        User user = null;
        try {
            user = objectMapper.readValue(request.getInputStream(), User.class);
            // loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class); // DTO 사용 시
        } catch (Exception e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // 2. 정상인지 로그인 시도 -> authenticationManager로 로그인 시도하면 PrincipalDetailsService가 호출되고, loadUserByUsername() 함수가 실행됨
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 3. PrincipalDetails를 세션에 담고 => 세션에 저장하는 이유는 스프링 시큐리티의 권한(USER, MANAGER, ADMIN) 관리를 하기 위함임 (권한 관리가 없다면 만들 필요가 없음)
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // 4. JWT 토큰을 만들어서 응답하면 됨
        return authentication;
//        return super.attemptAuthentication(request, response);
    }

    @Override // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfullAuthentication 함수가 실행됨
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("인증 완료!");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 만료시간: 1000이 1초
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC256(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);
//        super.successfulAuthentication(request, response, chain, authResult);
    }
}
