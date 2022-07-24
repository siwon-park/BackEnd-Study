![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 02_@어노테이션 정리

스프링에서 자주 쓰는 어노테이션에 대해 정리함

## @RestController

@Controller + @ResponseBody

<br>

## @RequestMapping

<br>

## @GetMapping, @PostMapping, @PutMapping, @DeleteMapping

<br>

## @PathVariable

<br>

## @RequestParam

<br>

## @RequestBody

<br>

## @ResponseBody

<br>

## @ResponseEntity

<br>

## @Component, @Controller, @Service, @Repository

>  @Controller, @Service, @Repository는 @Component 어노테이션이 구체화된 형태

@Component는 개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션 

![image](https://user-images.githubusercontent.com/93081720/179360100-a36338a0-e127-476d-a46b-8686f314eaef.png)

<br>

## @ComponentScan

> @Component, @Service, @Repository, @Controller, @Configuration 어노테이션이 붙은 클래스 Bean들을 찾아서 Context에 bean 등록을 해주는 어노테이션

#### ※ @SpringBootApplication 

@SpringBootApplication의 어노테이션 안에는 @ComponentScan이 포함되어 있음. 따라서 우리는 실질적으로 @ComponentScan을 쓸 일은 거의 없다.

<br>

## @Bean

@Bean은 개발자가 직접 제어가 불가능한 외부 라이브러리 등을 Bean으로 만들려고 할 때 사용하는 어노테이션

<br>

## @Configuration

@Configuration은 해당 클래스에서 1개 이상의 Bean을 생성하고 있음을 명시하는 어노테이션

따라서 @Bean을 사용하는 클래스는 반드시 @Configuration과 함께 사용함

<br>

## @Autowired

@Autowired는 field(속성) , setter, method, constructor(생성자)에서 사용하며 Type에 따라 자동으로 bean을 주입시켜주는 어노테이션

※ Autowired 방식으로 의존성을 주입할 경우, 순환 참조가 발생할 수도 있어 생성자를 통한 의존성 주입 방식이 권장됨

<br>

## @Qualifier

같은 타입의 bean이 두 개 이상 존재하는 경우, 어떤 bean을 주입해야하는지 알 수  없어, 스프링 컨테이너를 초기화하는 과정에서 예외가 발생하는데, 이때 @Qualifier를 @Autowired와 함께 사용하여 어떤 bean을 사용할지 명시할 수 잇음

<br>

## @Table

<br>

## @GeneratedValue

<br>

## @Slf4j

로그(log)를 사용/기록할 수 있게 해주는 어노테이션

info, debug, warn, error

<br>

# Lombok 어노테이션

## @Builder

빌더란 인스턴스 생성을 위한 디자인 패턴 중 하나인데, 롬복의 @Builder를 사용하면 따로 빌더 클래스를 구현하지 않아도 빌더 패턴을 사용해 오브젝트를 생성할 수 있다

<br>

## @NoArgsConstructor

롬복의 어노테이션 중 하나로 매개변수가 없는 기본 생성자를 구현해준다

사용하면 아래 예시와 같이 기본 생성자를 구현하는 것과 같다

```java
public Car() {
    
}
```

<br>

## @AllArgsConstructor

롬복의 어노테이션 중 하나로 클래스의 모든 맴버 변수를 매개변수로 받는 생성자를 구현해준다

<br>

## @Data

롬복의 어노테이션 중 하나로 클래스 맴버 변수의 Getter/Setter 메서드를 구현해준다

<br>

## @Getter

롬복의 어노테이션 중 하나로 클래스 맴버 변수의 Getter메서드를 구현해준다

<br>

## @Setter

롬복의 어노테이션 중 하나로 클래스 맴버 변수의 Setter메서드를 구현해준다

<br>

