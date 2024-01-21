![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 04_Spring Bean

## 01_Spring Bean

> 스프링 IOC 컨테이너에 의해서 관리되는 POJO(Plain Old Java Object) 객체

재사용 가능한 소프트웨어 컴포넌트로, 스프링 IOC컨테이너에 의해 관리되는 자바 객체이다.

### 1) 스프링 빈이 필요한 이유?

객체를 빈으로 등록하지 않고 의존성을 주입한다면 `직접 모든 의존 관계를 파악하고 의존성을 주입`해야하므로 번거로움이 생기며, 의존 관계에 의해 많은 객체가 중복 생성되는 현상이 발생함

- 객체 간 의존 관계를 직접 파악해야 하고, 직접 주입해야 함
- 직접 주입하다보니 객체를 중복 생성하여 낭비가 발생함
  - 굳이 새로 생성하지 않고 기존에 생성된 것을 사용하면 되는데 자바에서 new 키워드로 객체를 생성하듯이 사용함

![image](https://user-images.githubusercontent.com/93081720/181055931-10aec290-5f0f-4de2-9879-a707307b169d.png)

(A를 생성하려고 하는데, B를 넣어야 할지, C를 넣어야 할지, D를 넣어야 할지 모름)

### 2) 빈을 사용함으로써

- 스프링 IOC 컨테이너가 의존성 주입과 객체 생성을 관리하게 함으로써 주입된 의존성을 사용 가능.

- 객체를 만드는 메모리자원의 효용성과 생성주기를 프레임워크에게 맡김.
- 따라서 개발자는 비즈니스 로직에 집중하여 개발이 가능함

<br>

## 02_Spring IOC 컨테이너

> 스프링 빈을 관리하는 객체
>
> 스프링 빈의 생명 주기를 관리하며, 생성된 스프링 빈들에게 추가적인 기능을 제공하는 역할을 한다

개발자는 일반적으로 new 연산자, 인터페이스 호출, 팩토리 호출 방식으로 객체를 생성하고 소멸하지만, 스프링 컨테이너를 사용하면 해당 역할을 대신해 준다.

즉, 제어 흐름을 외부에서 관리하게 된다 => IOC(Inversion Of Control)

또한, 객체들 간의 의존 관계를 스프링 컨테이너가 런타임 과정에서 알아서 만들어 준다.

<br>

### 1) 역할

- Bean 생성
- Bean 사이의 의존 관계 설정

<br>

## 03_Spring Bean 등록 방법

### 1) ComponentScan(@Component)

#### (1) @**Component**

- 스프링 컨테이너에서 빈으로 등록될 `클래스`를 표시하는 가장 기본적인 어노테이션
- 재사용 가능한 컴포넌트임을 명시하기 위해 사용됨

`@Component` 어노테이션을 붙이면 스프링 빈으로 자동 등록된다. 이를 컴포넌트 스캔이라고 한다

또한 `@Controller`, `@Service`, `@Repository` 어노테이션을 적용시켜도 스프링 빈으로 자동 등록된다. 이는 이 세 개의 어노테이션이 @Component를 상속했기 때문이다.

![image](https://user-images.githubusercontent.com/93081720/230126433-c35f2761-7140-4281-99ed-79e2d48aa731.png)

#### (2) @Controller

- 스프링 MVC 컨트롤러로 인식된다.
- 클라이언트로부터 요청이 왔을 때, 디스패처 서블렛(dispatcher servlet)이 핸들러 어댑터(handler adapter)를 통해 컨트롤러를 찾을 때, 해당 클래스가 '컨트롤러' 역할을 함을 명시함

#### (3) @Repository

- DB에 직접적으로 접근하는 코드(레포지토리)임을 명시
- 스프링 데이터 접근 계층으로 인식하고 해당 계층에서 발생하는 예외는 모두 DataAccessException으로 변환한다.

#### (4) @Service

- 비즈니스 로직과 관련된 코드임을 명시
- 특별한 처리는 하지 않으나, 개발자들이 핵심 비즈니스 계층을 인식하는데 도움을 준다

<br>

### 2) Bean 등록 방법 차이점?

@Component, @Controller, @Service, @Repository의 차이점은 무엇일까?, 언제 어떻게 써야할까?

스프링 공식문서인 Spring Doc에 따르면 `역할을 명시적으로 구분해주기 위해서 구분하여 사용한다`고 나와 있다.

또한, 이렇게 사용함으로써 AOP(관점지향 프로그래밍)에서 적절한 pointcuts을 제공받을 수 있다고 한다.

```
you can annotate your component classes with @Component, but by annotating them with @Repository, @Service, @Controller instead, your classes are more properly suited for processing by tools or associating with aspects. For example, these stereotype annotations make ideal targets for pointcuts.
```

<br>

다음과 같이 회원 컨트롤러를 등록하고, 회원 컨트롤러가 회원서비스와 회원 리포지토리를 사용할 수 있게 의존관계를 설정한다고 할 때

![image](https://user-images.githubusercontent.com/93081720/188269295-e5ec1826-186e-4a1b-b138-ada303e92404.png)

만약에 memberService에 @Service 어노테이션 적용되지 않은 채, memberController에서 @Autowired를 통해 생성자를 자동으로 주입하는 코드를 작성했다고 하자 => 이 경우 서버를 작동시키면 오류가 난다. memberService가 스프링 컨테이너에 스프링 빈으로 등록되어 있지 않기 때문

![image](https://user-images.githubusercontent.com/93081720/188277027-ed3b601d-2835-40b5-8702-a11785dd0eec.png)



### 3) 자바 코드로 직접 등록하기

`@Configuration`과 `@Bean` 어노테이션을 적용

Config파일에 @Configuration을 적용하고, 인스턴스를 생성하는 메소드 위에 @Bean를 명시하면 된다

```java
package hello.hellospring.service;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public  MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
```

#### (1) @Configuration은 어떻게 싱글톤 빈을 보장하는가?

클래스의 바이트 코드를 조작하는 CGLIB 라이브러리를 사용하여 싱글톤을 보장한다. 

CGLIB는 프록시 객체의 일종으로 설정 파일이 빈으로 등록될 때, 해당 설정 파일을 상속 받은 프록시 객체가 빈으로 등록된다. 그리고 설정 파일에서 @Bean이 붙은 메소드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 비로소 등록하고 반환하는 식으로 싱글톤을 보장한다.

#### (2) Bean Lite Mode가 무엇인가?

Bean Lite Mode는 CGLIB를 이용하여 바이트 코드 조작을 하지 않는 방식을 의미한다. 이때 스프링 빈에 대해 싱글톤을 보장하지 않는다

<br>

### 4) 요약

#### (1) @Component

- 개발자가 **직접 컨트롤이 가능한 클래스들의 경우**에 사용된다.
- `클래스 또는 인터페이스 단위`에 붙일 수 있다.

#### (2) @Bean

- 개발자가 컨트롤이 불가능한 **외부 라이브러리들을 Bean으로 등록하고 싶은 경우**에 사용된다.
- `메소드 또는 어노테이션 단위`에 붙일 수 있다.

<br>

## 04_Spring Bean Scope

스프링에서는 싱글톤(Singleton)과 프로토타입(Prototype) 빈 스코프를 제공하고 있다

### 1) 빈과 싱글톤

스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다.

### 2) 싱글톤(Singleton)

싱글톤 빈은 스프링 컨테이너에서 한 번만 생성되며, 컨테이너가 사라질 때 제거된다

- 대상 클래스에 `@Scope(”singletone”)` 을 붙여서 지정할 수도 있다
- 싱글톤 타입으로 적절한 객체
  - 상태가 없는 공유 객체
  - 읽기 전용으로만 상태를 가진 객체
  - 쓰기가 가능한 상태를 지니면서도 사용 빈도가 매우 높은 객체
- 객체를 싱글톤으로 만든다면, 다형성을 적용하지 못함 => 상속이 불가능하므로
- 싱글톤 객체는 공유 객체(하나만 생성되기 때문에 동일 참조를 보장)가 되므로 단위 테스트 수행 시 테스트 순서에 따라 결과가 달라지는 문제가 발생함

### 3) 프로토타입(Prototype)

프로토 타입 빈은 DI가 발생할 때마다 새로운 객체가 생성되어 주입된다

- 빈 소멸에 스프링 컨테이너가 관여하지 않고, gc에 의해 빈이 제거된다
- 대상 클래스에 `Scope("prototype")` 을 붙이면 된다
- 프로토 타입으로 적합한 객체
  - 사용할 때마다 상태가 달라져야 하는 객체
  - 쓰기가 가능한 상태가 있는 객체

<br>

## 05_Spring Bean Life Cycle

### 1) 싱글톤

![image](https://user-images.githubusercontent.com/93081720/188270500-45c5fc33-6838-4ed3-adba-54ed17162cc5.png)

1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존 관계 주입
4. 초기화 콜백
5. 사용
6. 소멸 전 콜백
7. 스프링 종료



### 2) 프로토타입

![image](https://user-images.githubusercontent.com/93081720/188270504-4c93751e-5735-4868-8cdb-6ad50690a0c6.png)

1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존 관계 주입
4. 초기화 콜백
5. 사용
6. GC에 의해 수거
7. 소멸



----

## 참고

[[Spring Boot] 스프링 빈(bean)이란? 스프링 빈 등록하는 방법](https://velog.io/@falling_star3/Spring-Boot-%EC%8A%A4%ED%94%84%EB%A7%81-%EB%B9%88bean%EA%B3%BC-%EC%9D%98%EC%A1%B4%EA%B4%80%EA%B3%84)

[[Spring] Spring Bean 총 정리](https://steady-coding.tistory.com/594)