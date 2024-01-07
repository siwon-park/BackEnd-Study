package tobyspring.helloboot;


import java.util.Objects;

public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    public String hello(String name) { // 쿼리 스트링으로 동작 /hello?name=
//        SimpleHelloService helloService = new SimpleHelloService();
        return helloService.sayHello(Objects.requireNonNull(name));
//        return "Hello " + name;
    }
}
