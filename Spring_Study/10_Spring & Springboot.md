![image](https://user-images.githubusercontent.com/93081720/211854738-60911ea9-80cd-486e-87f5-ba681e796d20.png)

# 10_스프링과 스프링 부트

> 스프링과 스프링 부트의 차이

<br>

## 1. 스프링(Spring)

> 자바 기반 어플리케이션 개발을 위한 오픈소스 경량 프레임워크

스프링은 자바 진영의 웹 개발 프레임워크이다.

> Whatever happened next, the framework needed a name. In the book it was referred to as the “Interface21 framework” (at that point it used com.interface21 package names), but that was not a name to inspire a community. Fortunately Yann stepped up with a suggestion: “Spring”. His reasoning was association with nature (having noticed that I'd trekked to Everest Base Camp in 2000); and **the fact that Spring represented a fresh start after the “winter” of traditional J2EE**. We recognized the simplicity and elegance of this name, and quickly agreed on it.

![image](https://user-images.githubusercontent.com/93081720/211850356-c717870d-fe7f-4b65-ae8d-8e989713732e.png)

### 1) 특징

스프링은 POJO(Plain Old Java Object) 프로그래밍을 지향한다.

- Java가 제공하는 객체 지향을 통해서 느슨한 결합과 확장성, 코드의 재사용성을 가져가기 위해서
- 특정 기술을 사용하려면? 스프링에서 제공하는 해당 기술에 대한 인터페이스를 활용해서 사용한다.

#### (1) IoC (Inversion Of Control, 제어 역전)

개발자가 주도해서 개발하는 것이 아니라, 프레임워크의 구조와 틀에 맞게 필요한 부분을 개발해서 적용하는 특징.

메서드나 객체의 호출 작업을 개발자가 결정하는 것이 아니라 스프링 프레임워크에 의해 결정됨.

자바 객체를 생명 주기(Life Cycle)에 따라 스프링 IoC 컨테이너에서 직접 관리하고, 어플리케이션 동작에 필요한 것을 가져와서 사용함.

- 제어의 역전의 한 예시가 바로 의존성 주입임.
- Servlet이나 Bean을 개발자가 설정/등록을 해놓으면, 스프링에서 알아서 가져다가 사용함.

#### (2) DI (Dependency Injection, 의존성 주입)

직접 객체를 생성하지 않고, 외부에서 생성해서 주입하는 특징.

- 왜 할까?
  - 코드 수정이 용이함; 한 클래스를 수정할 때, 다른 클래스까지 수정해야 하는 상황을 피하고, 외부에서 객체를 주입받는 곳만 수정하면 됨.
  - 모듈 간의 느슨한 결합이 가능해짐; 필요한 객체를 주입하기만 하면 됨.
  - 코드의 재사용성 증가;

#### (3) AOP (Aspect Of Programming, 관점지향 프로그래밍)

어플리케이션 전반에 걸쳐 특정한 관점을 기준으로 코드를 나눠서 모듈화하는 프로그래밍 기법.

비즈니스 로직 핵심 코드와 공통적으로 사용되는 유틸성 코드를 분리해서 사용하는 것이 하나의 핵심적인 예시.

#### (4) PSA (Protable Service Abstraction, 일관된 서비스 추상화)

특정 기술과 관련된 서비스들을 추상화하여 일관된 방식으로 사용할 수 있게 제공함.

- 이걸 안 하면 어떻게 될까?
  - Java 코드가 외부의 기술을 사용하기 위해 그 기술의 패러다임이나 프레임워크에 의존한 채 개발됨.
    - 전체적으로 코드가 무거워지고, 결합성이 강해져서 수정이 힘들어질 수도 있음.
- 예) 데이터 접근을 위해서 스프링이 JDBC와 같은 데이터 접근 인터페이스를 제공함

<br>

## 2. 스프링 부트(Spring Boot)

> 스프링(Spring) 사용을 위한 설정을 자동화하여 더 편리하게 스프링을 사용할 수 있게 해주는 프레임워크

스프링의 많은 부분을 자동화하여, 스프링 사용에 편리성을 더한 프레임워크

> Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "**just run**".

### 1) 특징

#### (1) 내장 Tomcat (Embed Tomcat)

스프링 부트는 내부에 Tomcat/Jetty와 같은 WAS가 포함되어 있어, 따로 WAS를 설치하거나 버전 관리를 해야 하는 번거로움을 덜어준다.

- 스프링부트는 `@SpringBootApplication`을 통해 자동적으로 외장 라이브러리나 내장 톰캣 서버를 실행 가능하다.
- 반면에 스프링은 WAS 서버를 띄우고자 한다면, 어떤 서버를 띄울 것인지 명시적으로 설정해줘야 한다.

#### (2) 의존성 관리 자동화

`spring-boot-starter-%`를 통해 의존성(dependency) 관리 및 자동화를 시켜준다.

- pom.xml이나 build.gradle에 springboot 사용에 필요한 라이브러리를 손쉽게 추가 가능하다.

- 예를 들어, JPA가 필요할 경우 `spring-boot-starter-jpa`만 추가해줘도 나머지는 스프링부트가 알아서 필요한 제반 라이브러리를 추가해준다.
  - 반면에 스프링은 dependency에 호환되는 라이브러리 버전을 `직접` 설정해서 맞춰줘야 한다.

#### (3) 쉬운 배포

스프링 부트는 내장 WAS 서버 덕분에  `.jar`파일을 이용해 손쉽게 어플리케이션 배포가 가능하다.

- 반면에 스프링은 WAS를 정한다음, 모든 설정을 마치고 war 파일을 WAS에 담아서 배포해야 한다.

<br>

## 3. 스프링과 스프링부트의 차이

> 셀프 마라탕과 주문 마라탕의 차이와 유사

- 스프링 == 셀프 마라탕
- 스프링 부트 == 주문 마라탕

### 1) 차이점

스프링과 스프링 부트의 가장 큰 차이는 많은 부분에 있어`설정의 자동화`이다. (구글링을 해보면 용도까지 구분하는 사람들도 있는데, 솔직히 조금 억지 같다.)

#### (1) WAS 서버

- 스프링은 서버를 명시적으로 설정해줘야 한다
- 반면, 스프링 부트는 내장 Tomcat이나 Jetty를 제공하기 때문에 WAS에 대한 설정이 필요 없다.
  - 기본적으로 제공되는 @SpringBootApplication 어노테이션에 스프링에서 하는 서버에 대한 설정이 이미 구현되어 있다.

#### (2) 종속성 (dependency)

- 스프링은 종속성(dependency) 설정 시 어떤 한 종속성의 버전을 올릴 경우, 다른 종속성/라이브러리의 버전을 직접 수정해줘야 한다.
  - 이 작업을 Maven Repository에서 직접 찾아가면서 한다고 생각해보자. 끔직하다.
  - 또한 종속성들의 버전 간 충돌로 인한 문제 발생 시 디버깅에 많은 시간을 쏟게 된다.
- 반면, 스프링 부트는 spring starter를 통해 종속성/라이브러리 버전 자동 구성이 가능하다.

#### (3) 설정 (configuration)

- 스프링은 어노테이션이나 빈의 등록 등과 같은 configuration 설정을 모두 직접 해줘야 한다.
- 반면에 스프링 부트는 여러 유용한 어노테이션 덕분에 자동 설정 구성(AutoConfiguration)이 가능하다.
  - @ComponentScan, @EnableAutoConfiguration 등

