package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // HTTP 메서드 GET ()괄호 안에 쓰는 문자열이 url의 일부
    public String hello(Model model) {
        model.addAttribute("data","hello I'm siwonpark");
        return "hello"; // hello.html에 연결
    }

    @GetMapping("hello-MVC")
    public String helloMVC(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "Hello-Template";
    }

    @GetMapping("hello-string")
    @ResponseBody // body부분에 내가 직접 넣겠다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // 위와는 다르게 HTML이 아니고 String으로 바로 넘어간 상태
    }

    @GetMapping("hello-api")
    @ResponseBody // 이 예시는 JSON 형태로 렌더링됨
    public Hello helloAPI(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
