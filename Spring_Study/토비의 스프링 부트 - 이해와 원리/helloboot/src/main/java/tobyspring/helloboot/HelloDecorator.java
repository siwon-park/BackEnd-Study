package tobyspring.helloboot;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary // 의존성 주입 우선 순위
public class HelloDecorator implements HelloService { // 프록시 패턴을 적용한 데코레이터
    private final HelloService helloService;

    public HelloDecorator(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public String sayHello(String name) {
        return helloService.sayHello(name); // helloService의 sayHello메서드를 대신 호출해서 사용
    }
}
