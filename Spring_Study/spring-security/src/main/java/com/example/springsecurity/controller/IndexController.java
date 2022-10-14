package com.example.springsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

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
    @GetMapping("/login")
    @ResponseBody
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/joinProc")
    @ResponseBody
    public String joinProc() {
        return "회원가입 완료";
    }

}
