![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 00_Spring_Basic



## 01_MVC Pattern

스프링의 소프트웨어 디자인 패턴으로 각각 M: Model, V: View, C: Controller를 의미한다. 스프링 프레임워크는 현재 MVC2 패턴을 채택하고 있다

### Model

어플리케이션의 데이터, DB 등 모든 데이터 정보를 가공하여 갖고 있는 컴포넌트

- 사용자가 이용하려는 모든 데이터를 갖고 있어야하며, View와 Controller에 대해 어떠한 정보도 알 수 없어야 한다
- 변경이 발생할 경우 그에 대한 처리 방법을 구현해야한다

### View

UI, 시각적인 부분을 담당하는 컴포넌트

- Model과 Controller에 대한 정보를 알면 안되며, 단순히 표현/렌더링을 하는 역할을 한다
- 변경이 발생할 경우 그에 대한 처리 방법을 구현해야한다

### Controller

Model과 View를 연결하는 역할의 컴포넌트로, 데이터와 비즈니스 로직 간의 상호작용을 담당한다

- Model과 View에 대한 정보를 알고 있어야하며, 변경에 대해 대처를 해야한다.

<br>

## 02_MVC Pattern 동작 구조

![image](https://user-images.githubusercontent.com/93081720/172411593-e1b641a8-393a-4698-aa7e-5977c709c0b1.png)

1.  Client가 url을 통해서 요청(request)을 보냄
2.  DispatcherServlet은 요청을 처리하기 위한 Controller를 HandlerMapping에게 검색을 요청한다
3.  HandlerMapping은 요청된 URL을 이용해서 이를 처리할 Controller를 DispathcerServlet에게 return한다
4.  DispathcerServlet은 HandlerAdapter에게  Controller가 요청을 처리할 수 있도록 요청 처리를 위임한다
5.  HandlerAdapter는 Controller에게 요청에 알맞는 method를 호출하도록 요청한다
6.  Controller는 Service에게 비즈니스 로직 처리를 위임한다
7.  Service는 요청에 필요한 작업을 수행하거나, DB에 접근이 필요할 경우 DAO에게 처리를 위임한다
8.  DAO(Data Access Object)는 DB정보를 DTO(Data Transfer Object)를 통해 전달받아 서비스에게 전달한다
9.  Controller는 비즈니스 로직 처리 결과를 HandlerAdapter에게 반환한다
10.  HandlerAdapter는 DispatcherServlet에게 처리 결과를 ModelAndView 객체로 변환하여 반환한다
11.  DispatcherServlet은 결과를 보여줄 View를 찾기 위해 ViewResolver에게 ModelAndView안의 해당 View를 검색 요청한다
12.  ViewResolver는 ModelAndView안에 있는 View 이름에 해당하는 View객체를 찾거나 생성해서 반환한다
13.  DispatcherServlet은 전달받은 View 객체에게 request result 생성을 요청한다
14.  View 객체는 JSP를 사용하는 경우 JSP를 실행하여 result를 Rendering한 후 Client에게 Rendering된 View를 응답(Response)한다

<br>

## 03_IoC(Inversion of Control)와 DI(Dependency Injection)

### IoC(Inversion of Control) - 제어의 역전

제어의 역전 => 제어의 흐름을 바꾼다

메서드나 객체의 호출 작업을 개발자가 결정하는 것이 아니라, 외부(프레임워크)에서 결정하는 것을 의미 => 개발자가 주도해서 개발하는 것이 아니라, 프레임워크의 구조와 틀에 맞게 필요한 부분을 개발해서 적용한다.

#### Why?

기존의 new를 통한 방식의 객체 생성은 객체 간의 의존성을 갖게 만듦 => 클래스 내부에서 객체를 생성하여 사용하게 되면 해당 클래스는 생성한 객체에 의존성을 갖게 된다.

해당 클래스 수정이 어려워지는 문제가 발생한다. 클래스 내부에 생성된 객체에 변경 사항이 발생했을 경우 해당 클래스뿐만 아니라 연결된 다른 클래스까지 전부 변경해줘야한다 => 소프트웨어 모듈화에 큰 방해가 됨

#### So?

이를 해결하기 위해 의존성 주입이라는 개념이 생겨났음



### DI(Dependency Injection) - 의존성 주입

의존성 주입 => 외부에서 객체를 생성하여 주입함

외부에서 객체를 생성하여 주입함으로써 모듈 간 결합 관계를 낮출 수 있음 => 코드 수정이 용이해짐

한 클래스를 수정한다고 했을 때, 다른 클래스까지 수정해야하는 상황이 발생하지 않으며, 외부에서 객체를 주입받는 부분만 수정하면 됨

#### 방법

- 생성자(권장)

```java
public class Chef {
    private Knife knife;
    
    public Chef(Knife knife) { // 외부에서 생성된 knife 객체를 Chef 클래스의 생성자의 인자로 받음
        this.knife = knife;
    }
}
```

- setter

```java
public class Chef {
    // setter를 통해서 외부에서 생성된 knife 객체를 전달받음(Spring에서 @Autowired 애너테이션이 필요)
    public void setKnife(Knife knife) {
        this.knife = knife;
    }
}
```

- @Autowired

```java
public class Chef {
    
    @Autowired // 속성에 @Autowired 애너테이션을 적용하여 객체를 주입받음
    private Knife knife;
}
```

<br>

## 04_메이븐(Maven)과 그레이들(Gradle)

![build-logo](https://user-images.githubusercontent.com/93081720/173735849-207a1ef0-01a4-4ccd-9865-c2eae868d407.png)

### 공통점

#### 빌드 관리 도구

※ 빌드: 소스 파일을 컴파일하여 실행할 수 있는 결과물로 변환하는 과정

=> 즉, 빌드 관리 도구란 소스 코드에서 어플리케이션을 생성하면서 사용자가 외부 라이브러리를 직접 관리할 필요 없이 자동으로 관리해주는 도구를 말한다

어플리케이션을 개발하면서, 개발에 필요한 다양한 외부 라이브러리들을 다운로드하는 경우가 많은데 이 때 각 라이브러리들을 번거롭게 모두 다운받을 필요없이, 빌드도구 설정파일에 필요한 라이브러리 종류와 버전들, 종속성 정보를 명시하여 필요한 라이브러리들을 설정파일을 통해 자동으로 다운로드 해주고 이를 간편히 관리해주는 도구

프로젝트 생성, 테스트 빌드, 배포 등의 작업을 위한 전용 프로그램

### 메이븐(Maven)

> java 전용 프로젝트 라이프 사이클 관리 목적 빌드 도구로 Apache Ant의 불편함을 해결하고자 만들어졌음

#### 특징

- 라이프 사이클: 정해진 라이프 사이클에 의해 작업을 수행, 전반적인 프로젝트 관리 기능을 포함하고 있음
- 프로젝트 모델링: Maven은 `pom.xml`에 필요한 라이브러리를 정의한다
- 플러그인을 통한 전역적인 재사용: Maven은 빌드에 대한 대부분의 책임을 각 플러그인에 위임한다. 이러한 플러그인들은 Maven Repository에 저장된다
- 공통 인터페이스: Maven 이전에는 각 개발 환경에 대한 빌드 프로세스를 파악하는데 시간이 오래 걸렸다

<br>

### 그레이들(Gradle)

> Ant Builder와 그루비(Groovy)를 기반으로한 빌드 도구로 기존 Ant의 역할과 배포 스크립의 기능을 모두 사용가능하며 스프링부트와 안드로이드에서 사용된다. Maven과 같은 이전 세대 빌드 도구의 단점을 보완하고 장점을 취합하여 만든 빌드 도구

#### 특징

- 간결함: xml은 구조적인 장점이 있는 대신 문서 양이 비대해진다는 단점이 있는데, Gradle은 코딩에 의한 간결한 정의가 가능하다
- 재사용 용이: 프로젝트를 `설정 주입 방식(Configuration Injection)`으로 정의하여 Maven의 상속 구조보다 재사용이 용이함
- 구조적 장점: Groovy 기반의 DSL(Domain Specific Language)를 사용하여 코드로서 설정 정보를 구성하기 때문에 구조적 장점이 있다
- 편리함: Gradle은 설치 없이 Gradle Wrapper를 이용하여 빌드를 지원한다
- 멀티 프로젝트 지원: Gradle은 멀티 프로젝트 빌드를 지원한다(애초에 그렇게 할 목적으로 설계된 빌드 관리 도구이다)
- 지원: Maven을 완전 지원한다

<br>

### 결론

- 스크립트의 길이와 가독성 면에서 Gradle이 Maven보다 우수하다
- 빌드와 테스트 실행 결과 속도 면에서 Gradle이 Maven보다 빠르다(Gradle은 캐시를 사용하기 때문에 반복적으로 테스트 시 차이가 더 날 수 밖에 없다)
- 의존성이 늘어날 수록 성능과 스크립트의 품질 면에서 Gradle이 우세하다

<br>

## 05_Entity, DAO, DTO

### Entity(엔티티)

> DB 테이블에 존재하는 칼럼(필드)들을 속성(property)로 가지는 클래스

DB와 1대1로 대응되며, 따라서 DB테이블이 칼럼으로 가지지 않는 것을 필드로 가져서는 안 된다. 또한 다른 클래스를 상속 받거나 인터페이스의 구현체여서는 안 된다.

JPA를 사용할 때는 `@Entity` 애너테이션을 붙여서 Entity임을 명시적으로 정의할 수 있다.

Entity는 외부에서 최대한 Entity의 Getter를 사용하지 않도록 Domain 로직만 구현하고, Presentation 로직은 구현하지 않는다

Entity를 만들 때, 최대한 Setter를 만드는 것을 지양하는데, 그 이유는 Setter를 사용할 경우 Setter를 호출하여 Entity의 인스턴스 값들이 언제든지 바뀔 수 있으므로 로직에 따른 명확한 변화 시점을 알 수 없게된다.

그래서 엔티티 클래스에 생성자를 만들고 Build패턴을 통해 필요한 값을 집어 넣는 방식으로 Entity 인스턴스를 생성하는 것이 좋다

![image](https://user-images.githubusercontent.com/93081720/173801732-862e2452-da82-4996-88b0-69375db8a192.png)

<br>

빌더 패턴을 통핸 인스턴스 생성

![image](https://user-images.githubusercontent.com/93081720/173803180-fc4bed34-64fc-4d63-aec0-c40c7d51ab13.png)

#### ※ 빌더 패턴의 장점

- 필요한 데이터만 설정할 수 있음 => 생성자에 지정된 모든 매개변수를 넣지 않고도 생성자를 생성할 수 있음
- 유연성을 확보할 수 있음 => 매개변수에 따른 생성자를 따로 생성할 필요없이, 전체 속성값을 가진 생성자를 정의한 후 사용 가능
- 가독성을 높일 수 있음 => 어떤 항목으로 어떤 데이터가 들어가는 지 명시적으로 알 수 있음
- 변경 가능성을 최소화할 수 있음 => final 키워드를 붙이지 않고 Setter를 정의하지 않고 Build패턴을 적용

대부분의 경우에는 빌더 패턴을 적용하는 것이 좋지만 다음과 같은 사항에서는 굳이 빌더를 구현할 필요가 없다

- 객체 생성을 라이브러리로 위임하는 경우
- 변수의 개수가 2개 이하이며, 변경 가능성이 없는 경우

=> 변수의 개수와 변경 가능성 등을 중점으로 보고 빌더 패턴을 적용할지 판단하면 됨

<br>

### DAO(Data Access Object)

> 실제 DB에 접근하는 객체

DB에 데이터를 CRUD하는 `Repository`객체들이 DAO라고 말할 수 있음(실제 개념적으로는 차이가 있음)

Persistence Layer(Repository) => DB에 data를 CRUD하는 계층

JPA 등장 이전에는 보통 생성, 검색, 수정, 삭제와 같은 기본적인 CRUD 오퍼레이션을 각 Entity마다 작성해주었다. => DAO 클래스를 각 Entity마다 작성하였음

왜? 데이터 테이블에 접근해서 데이터에 대한 오퍼레이션을 실행 당하는 대상이 자바 내의 데이터 테이블 객체인 Entity이기 때문

<br>

### DTO(Data Transfer Object)

> 계층 간 데이터 교환 역할을 하는 객체

DB에서 꺼낸 데이터를 저장하는 Entity를 가지고 만드는 일종의 Wrapper 객체라고 볼 수 있음.  Controller와 같은 클라이언트 단과 직접 마주하는 계층에서 Entity를 통해서 직접 데이터를 전달하는 것이 아니라, 순수하게 계층 간의 데이터 교환이 이루어질 수 있도록 하는 객체로서 특별한 로직을 가지지 않는 DTO를 사용한다. 따라서 DTO에서 데이터를 임의로 조작할 필요가 없기 때문에 Setter를 만들지 않는다

#### Entity와 DTO를 분리하는 이유

- 관심사의 분리(Seperation of Concerns, SoC) :서로 다른 관심사들을 분리하여 변경 가능성을 최소화하고, 유연하며 확장가능한 클린 아키텍처를 구축하도록 도와준다

=> DTO의 관심사는 데이터를 담아서 다른 계층 또는 컴포넌트로 데이터를 전달하는 것임.

반면에 Entity 또는 도메인 객체는 핵심 비즈니스 로직을 담고 있는 비즈니스 도메인의 일부이기 때문에 로직이 새롭게 추가될 수 있으며, 오로지 DB와 1대1 맵핑에 그 핵심이 있음

- Entity가 변하면 Repository 클래스의 Entity Manager의 flush가 호출될 때 DB값이 반영되는데, 이는 다른 로직들에게도 영향을 미친다. 따라서 View와 통신하면서 필연적으로 데이터의 변경이 많은 DTO클래스를 분리해주는 것이다.

- Entity와 DTO가 분리되어 있지 않다면 Entity안에 Presentation을 위한 필드나 로직이 추가되게 되어 객체 설계를 망가뜨리게 된다. 때문에 이런 경우 DTO에 Presentation로직 정도를 추가해서 사용하고, Entity에는 그대로 두고 도메인 모델링을 깨뜨리지 않는다.

<br>

## 06_AOP(Aspect Oriented Programming)



<br>

## 07_@; 에너테이션 정리

### @Component, @Controller, @Service, @Repository

>  @Controller, @Service, @Repository는 @Component 에너테이션이 구체화된 형태

@Component는 개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 에너테이션 

![image](https://user-images.githubusercontent.com/93081720/179360100-a36338a0-e127-476d-a46b-8686f314eaef.png)

<br>

### @ComponentScan

> @Component, @Service, @Repository, @Controller, @Configuration 에너테이션이 붙은 클래스 Bean들을 찾아서 Context에 bean 등록을 해주는 에너테이션

#### ※ @SpringBootApplication 

@SpringBootApplication의 에너테이션 안에는 @ComponentScan이 포함되어 있음. 따라서 우리는 실질적으로 @ComponentScan을 쓸 일은 거의 없다.

<br>

### @Bean

@Bean은 개발자가 직접 제어가 불가능한 외부 라이브러리 등을 Bean으로 만들려고 할 때 사용하는 에너테이션

### @Configuration

@Configuration은 해당 클래스에서 1개 이상의 Bean을 생성하고 있음을 명시하는 에너테이션

따라서 @Bean을 사용하는 클래스는 반드시 @Configuration과 함께 사용함

<br>

### @Autowired

@Autowired는 field(속성) , setter, method, constructor(생성자)에서 사용하며 Type에 따라 자동으로 bean을 주입시켜주는 에너테이션

※ Autowired 방식으로 의존성을 주입할 경우, 순환 참조가 발생할 수도 있어 생성자를 통한 의존성 주입 방식이 권장됨

### @Qualifier

같은 타입의 bean이 두 개 이상 존재하는 경우, 어떤 bean을 주입해야하는지 알 수  없어, 스프링 컨테이너를 초기화하는 과정에서 예외가 발생하는데, 이때 @Qualifier를 @Autowired와 함께 사용하여 어떤 bean을 사용할지 명시할 수 잇음

<br>

## 08_AOP(Aspect Oriented Programming)



<br>

---

### Questions / Todos

- ~~maven과 gradle의 차이점?~~
  - ~~gradle: 그루비(Groovy)를 기반으로한 빌드 도구로, Maven과 같은 이전 세대 빌드 도구의 단점을 보완하고 장점을 취합하여 만든 빌드 도구~~
- Bean이란 무엇인가? 스프링 빈 컨테이너? 스프링 빈 라이프 사이클 훅?
- ~~DI(Dependency Injection)란 구체적으로 무엇인가?~~
- AOP, POJO란 무엇인가?
- ~~DAO, DTO?~~
- 스프링과 스프링부트의 차이점?
- 도메인, 컨트롤러 등에 대해 용어 정리 및 개념 탑재