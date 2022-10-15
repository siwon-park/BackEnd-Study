package com.example.springsecurity.controller;


import com.example.springsecurity.config.auth.PrincipalDetails;
import com.example.springsecurity.model.User;
import com.example.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    @ResponseBody
    public String loginTest(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        System.out.println("일반 로그인 테스트=======");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getDetails(); // PrincipalDetails가 UserDetails를 상속받았기 때문에 PrincipalDetails로 캐스팅이 가능함
        System.out.println("authentication: " + principalDetails.getUser());
        System.out.println("userDetails: " + userDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String loginTestOauth(Authentication authentication, @AuthenticationPrincipal OAuth2User oauth2) {
        System.out.println("Oauth2.0 로그인 테스트=======");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication: " + oAuth2User.getAttributes());
        System.out.println("Oauth2User: " + oauth2.getAttributes());
        return "Oauth 세션 정보 확인하기";
    }

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본 폴더: src/main/resources/
        // viewresolver 설정: templates (prefix), .mustache(sufix) => 생략 가능
        return "index"; // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public String manager() {
        return "manager";
    }
    
    // 기본적으로 login 함수를 만들어도 SecurityConfig에서 따로 설정을 하지 않으면 스프링 시큐리티의 기본 login 페이지로 리다이렉트됨
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        user.setRole("ROLE_USER");
//        userRepository.save(user); // 회원가입은 잘 됨. 그러나 비밀번호가 암호화되지 않았기 때문에 시큐리티로 로그인 불가능
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 패스워드 암호화
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm"; // 로그인폼으로 리다이렉트
    }

    @Secured("ROLE_ADMIN") // SecurityConfig에 전역으로 접근 권한을 설정해도 되지만, 어노테이션으로도 적용 가능
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // PreAuthorize, PostAuthorize는 hasRole 키워드를 사용 가능
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "데이터";
    }

}
