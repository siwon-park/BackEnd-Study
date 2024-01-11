package tobyspring.helloboot;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Component
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    @ResponseBody // 디스패처 서블릿이 String 결과에 해당하는 이름의 뷰를 찾기 때문에 값 자체를 반환하기 위함
    public String hello(String name) { // 쿼리 스트링으로 동작 /hello?name=
//        SimpleHelloService helloService = new SimpleHelloService();
        return helloService.sayHello(Objects.requireNonNull(name));
//        return "Hello " + name;
    }
}
