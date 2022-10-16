package com.example.springjwt.controller;

import com.example.springjwt.model.User;
import com.example.springjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home() {
        return "<h1>홈</h1>";
    }

    @GetMapping("/api/v1/admin/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .roles("ROLE_USER")
                .build();
        userRepository.save(newUser);
        return "회원가입 완료";
    }

    @GetMapping("/api/v1/user")
    public String user() {
        return "<h1>유저 권한이 필요한 곳입니다.</h1>";
    }

    @GetMapping("/api/v1/manager")
    public String manager() {
        return "<h1>매니저 권한이 필요한 곳입니다.</h1>";
    }

    @GetMapping("/api/v1/admin")
    public String admin() {
        return "<h1>어드민 권한이 필요한 곳입니다.</h1>";
    }

}
