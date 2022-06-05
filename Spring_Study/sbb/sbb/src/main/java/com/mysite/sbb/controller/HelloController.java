// HelloController.java
package com.mysite.sbb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // @Controller 애너테이션을 적용하면 해당 클래스는 스프링부트의 컨트롤러가 됨
public class HelloController {

    @RequestMapping("/sbb") // @RequestMapping은 요청한 url을 맵핑하는 것임
    @ResponseBody // @ResponseBody 애너테이션은 맵핑한 url 요청에 대한 응답을 index함수가 리턴한다는 의미
    public String index() {
        return "Hello Spring!";
    }

}

