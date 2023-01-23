package com.example.usersservice.security;

import com.example.usersservice.dto.RequestLoginDto;
import com.example.usersservice.dto.UserDto;
import com.example.usersservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    // 인스턴스를 WebSecurity에서 직접 생성해서 사용하므로, 자동 주입이 아니라 직접 주입(생성자) 방식을 사용함
    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }


    @Override // 요청 정보를 날렸을 때 인증 처리해주는 메서드
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLoginDto creds = new ObjectMapper().readValue(request.getInputStream(), RequestLoginDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()) // ArrayList 는 권한 정보들이 담김
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return super.attemptAuthentication(request, response);
    }

    @Override // 실제 로그인 성공을 했을 때, 어떤 처리를 해줄 것인지 정의하는 메서드
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // super.successfulAuthentication(request, response, chain, authResult); // RESTAPI 서버이기 때문에 로그인페이지를 응답할 필요가 없으니 주석 처리
        // log.debug(((User)authResult.getPrincipal()).getUsername()); // log는 @Slf4j가 있어야 사용 가능
        String username = ((User)authResult.getPrincipal()).getUsername();
        UserDto userDetails = userService.getUserDetailsByEmail(username);

        String jwtToken = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time")))) // .yml 파일에서 가져오는 데이터는 숫자처럼 보여도 모두 문자열임
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();

        response.addHeader("token", jwtToken);
        response.addHeader("userId", userDetails.getUserId());

    }
}
