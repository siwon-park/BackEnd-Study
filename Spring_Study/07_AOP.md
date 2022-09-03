# 07_AOP

## 개념

### AOP

>  Aspect Oriented Programming; 관점 지향 프로그래밍 

관점 지향 프로그래밍은 핵심적인 관점 또는 부가적인 관점과 같이 **어떤 로직을 기준으로 특정 관점으로 나누어서 이를 기준으로 모듈화하는 프로그래밍 기법**이다

![image](https://user-images.githubusercontent.com/93081720/188274588-9898cc1a-a825-45bd-8da6-bfb14365eb56.png)

<br>

## 스프링 AOP

> 스프링 AOP는 프록시 기반의 AOP 구현체이며, 스프링 빈에만 AOP를 적용할 수 있다

### 프록시(Proxy) 패턴

> 프록시 패턴이란 프록시 객체가 원래 객체를 감싸서 클라이언트의 요청을 처리하는 패턴이다.

인터페이스가 존재하고 클라이언트는 이 인터페이스 타입으로 프록시 객체를 사용한다

프록시 객체는 원래의 객체(Real Subject)를 참조하고 있다.  Proxy 객체와 Real Subject의 타입은 같고, Proxy는 원래 해야 할 일을 가지고 있는 Real Subject를 감싸서 Client의 요청을 처리한다.



![image](https://user-images.githubusercontent.com/93081720/188274727-31378551-a746-4b20-9f78-3d644e2a5d75.png)

#### 프록시 패턴을 사용하는 이유?

> 기존 코드의 변경 없이 접근 제어 또는 부가 기능 추가를 위해서 사용한다

예시)

- EventService interface

```java
public interface EventService {
    void createEvent();
    void publishEvent();
}
```

- TestEventService

```java
@Service
public class TestEventService implements EventService {
    @Override
    public void createEvent() {
        System.out.println("Create Event");
    }
    
    @Override
    public void publishEvent() {
        System.out.println("Publish Event");
    }
}
```

=> 이 코드를 실행시키는 `main`이 client라고 볼 수 있고, TestEventService가 `원래 객체`로 볼 수 있다. 

그런데 TestEventServic의 createEvent메서드와 publishEvent메서드에 동일한 기능을 하는 코드를 넣고자 할 때, 각각의 메서드에 똑같이 넣어주는 것은 효율성이 떨어진다.

이 때, 프록시 패턴을 사용하여 기존 코드는 건드리지 않고 프록시 객체를 통해서 원하는 기능을 추가할 수 있다.

- ProxyTestEventService

```java
// 원래 객체와 같은 interface구현
@Primary
@Service
public class ProxyTestEventService implements EventService { 
    @Autowired
    TestEventService testEventService;
    
    @Override
    public void createEvent() {
        long begin = System.currentTimeMillis(); // 새로 추가한 기능
        testEventService.createEvent(); // 원래 객체의 기능
        System.out.println(System.currentTimeMillis() - begin); // 새로 추가한 기능
    }
    
    @Override
    public void publishEvent() {
        long begin = System.currentTimeMillis(); // 새로 추가한 기능
        testEventService.publishEvent(); // 원래 객체의 기능
        System.out.println(System.currentTimeMillis() - begin); // 새로 추가한 기능
    }
}
```

- `@Primary` : 같은 타입의 빈이 여러 가지일 때, 그 중 하나를 선택하여 사용하는 어노테이션

이렇게 해주면, 원래 코드에 손을 쓰지 않고도 기능을 추가 할 수는 있지만, 프록시 객체에 중복코드가 발생할 수 있고, 다른 클래스에서도 동일한 기능을 사용하고자 할 때, 매번 코딩을 해줘야하는 부분에서 효율적이지 못하다.



### 스프링 AOP

위의 문제를 해결해주는 것이 바로 스프링 AOP이다.

스프링 AOP는 런타임시, 동적으로 프록시 객체를 만들어준다

#### 적용 방법

@AOP 사용을 위해 의존성을 추가한다

- pom.xml의 경우

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

- build.gradle의 경우

```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-aop' 
}
```



Aspect를 나타내는 클래스 작성

```java
@Component
@Aspect
public class PerfAspect {

    @Around("execution(* Room.EventService.*(..))") // point cut
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        // ProceedingJoinPoint가 이 advice가 적용되는 대상임
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }

}
```

이를 위해 2가지 정보가 필요한데

- 해야할 일(Advice)
  - public Object logPerf 자체가 하나의 Advice이다
- Advice를 적용할 곳(PointCut)
  - @Around 어노테이션으로 적용
  - @Around("execution(*패키지.interface.메서드)") 형태로 사용한다



pointcut을 execution이 아니라 어노테이션을 만들어서 적용할 수도 있다

```java
// 어노테이션 파일 생성
@Documented // javadoc을 만들 때, documentation이 되도록 설정
@Target(ElementType.METHOD) // 메서드를 target으로 잡음
@Retention(RetentionPolicy.CLASS) // 어노테이션 정보를 얼마나 유지할 것인지 설정(.class 파일까지)
public @interface PerfLogging {
}
```

적용

```java
// 원래 객체에 만든 어노테이션을 적용
@Service
public class TestEventService implements EventService {
    @PerfLogging // 적용
    @Override
    public void createEvent() {
        System.out.println("Create Event");
    }
    
    @PerfLogging // 적용
    @Override
    public void publishEvent() {
        System.out.println("Publish Event");
    }
}
```

Aspect 클래스의 @Around 어노테이션에 execution대신 @annotation을 사용

```java
// @annotation 사용
@Component
@Aspect
public class PerfAspect {

    @Around("@annotation(PerfLogging)") // execution대신 @annotation 사용
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }

}

// 또는 bean을 사용할 수도 있음
@Component
@Aspect
public class PerfAspect {

    @Around("bean(testEventService)") // @annotation대신 bean 사용
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        System.out.println(System.currentTimeMillis() - begin);
        return retVal;
    }

}
```



