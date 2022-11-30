![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 03_@어노테이션 정리

스프링에서 자주 쓰는 어노테이션에 대해 정리함

## 01_Springboot 기본

### @Component, @Controller, @Service, @Repository

>  @Controller, @Service, @Repository는 @Component 어노테이션이 구체화된 형태

@Component는 개발자가 직접 작성한 Class를 Bean으로 등록하기 위한 어노테이션 

![image](https://user-images.githubusercontent.com/93081720/179360100-a36338a0-e127-476d-a46b-8686f314eaef.png)

<br>

### @ComponentScan

> @Component, @Service, @Repository, @Controller, @Configuration 어노테이션이 붙은 클래스 Bean들을 찾아서 Context에 bean 등록을 해주는 어노테이션

#### ※ @SpringBootApplication 

@SpringBootApplication의 어노테이션 안에는 @ComponentScan이 포함되어 있음. 따라서 우리는 실질적으로 @ComponentScan을 쓸 일은 거의 없다.

<br>

### @Bean

> @Bean은 개발자가 직접 제어가 불가능한 외부 라이브러리 등을 Bean으로 만들려고 할 때 사용하는 어노테이션

<br>

### @Configuration

> @Configuration은 해당 클래스에서 1개 이상의 Bean을 생성하고 있음을 명시하는 어노테이션

따라서 @Bean을 사용하는 클래스는 반드시 @Configuration과 함께 사용함

<br>

### @Autowired

> @Autowired는 field(속성) , setter, method, constructor(생성자)에서 사용하며 Type에 따라 자동으로 bean을 주입시켜주는 어노테이션

※ Autowired 방식으로 의존성을 주입할 경우, 순환 참조가 발생할 수도 있어 생성자를 통한 의존성 주입 방식이 권장됨

<br>

### @Qualifier

같은 타입의 bean이 두 개 이상 존재하는 경우, 어떤 bean을 주입해야하는지 알 수  없어, 스프링 컨테이너를 초기화하는 과정에서 예외가 발생하는데, 이때 @Qualifier를 @Autowired와 함께 사용하여 어떤 bean을 사용할지 명시할 수 있음

<br>

### @Slf4j

> 로그(log)를 사용/기록할 수 있게 해주는 어노테이션

info, debug, warn, error

<br>

## 02_Controller

### @RestController

> @Controller + @ResponseBody

※ @Controller와 차이점

@RestController는 클래스 메서드의 리턴 타입이 String이면 문자열 데이터를 의미하므로, 문자열을 그대로 리턴한다.

하지만, @Controller는 클래스 메서드의 리턴 타입이 String이면 해당 문자열과 파일명이 일치하는 jsp파일 리턴한다.

<br>

### @RequestMapping

> 해당 컨트롤러와 맵핑되는 기본 주소를 정의할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/204794910-5c2898cb-5b63-4b3c-8231-16b1094b7bd6.png)

<br>

### @GetMapping, @PostMapping, @PutMapping, @DeleteMapping

각각 GET, POST, PUT, DELETE 요청에 따른 주소 맵핑을 정의하는 것으로, `@RequestMapping(Method=RequestMethod.OOO)`와 같은 의미이다.

<br>

### @PathVariable

> 매개변수로 들어온 변수를 url로 맵핑해주는 어노테이션

 @PathVariable 어노테이션이 적용된 동일한 이름을 갖는 파라미터에 매핑

![image](https://user-images.githubusercontent.com/93081720/204803570-fb0ceaa3-3ba7-40fd-8e1c-f422ba451bff.png)

`http://xxxx.x.x/index/1`와 같이 REST API 호출에 주로 사용

<br>

### @RequestParam

> 매개변수로 들어온 변수를 url의 쿼리스트링으로 전달하는 어노테이션

?, &와 함께, key=value 형식으로 전달될 값들을 맵핑함

![image](https://user-images.githubusercontent.com/93081720/204805303-26a45e67-382c-4ff4-9079-57411b3ce58d.png)

위에서 `/list?word=abcdef`와 같은 형태로 url이 맵핑된다. => 페이지 정보 및 검색에 주로 사용

#### name

url에 맵핑되는 key값의 이름을 정의할 수 있는 옵션

#### required

true일 경우 해당 파라미터가 필수 요소임을 의미

#### defaultValue

해당 파라미터가 비어있을 때, 기본값을 지정해줄 수 있다.

<br>

### @RequestHeader

> 클라이언트의 요청에 있는 header의 값을 받아오기 위한 어노테이션

#### value

value에 해당하는 request header에 있는 key에 대한 값을 가져오게 함

![image](https://user-images.githubusercontent.com/93081720/204808015-79c8cb93-1109-4dca-875e-f61d0d10edab.png)

<br>

### @RequestBody

> 요청 본문

json 기반의 POST요청에 대해 DTO나 VO에 전달할 데이터를 담아서 파라미터로 요청함

Http 요청의 바디 내용을 DTO, VO에 맵핑하여 파라미터로 요청함

![image](https://user-images.githubusercontent.com/93081720/204811197-e799e30e-471e-485e-bd22-738abfd291bc.png)

<br>

### @ResponseBody

> 응답 본문

Http 요청의 body를 DTO, VO와 같은 자바 객체로 전달함

<br>

### @ResponseEntity

> 사용자의 Http 요청에 대한 응답 데이터를 '포함'하는 '데이터 클래스'

HttpEntity를 상속(확장)하여 headers, body, status를 지정할 수 있다.

다음과 같이 http 요청 응답 결과 상태 코드와 함께 데이터를 함께 응답할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/204812956-309c85da-159f-4f79-957d-2fa9fb18c827.png)

<br>

## 03_Service

### @Transactional

> 선언적 트랜잭션 처리를 지원해주는 어노테이션. 일련의 작업들을 묶어서 하나의 단위로 처리하고 싶을 때 사용.

클래스, 메서드에 `@Transactional`을 붙여서 사용하며, 메서드 레벨이 우선 적용된다.

`@Transactional`이 붙은 메서드는 해당 메서드가 포함하고 있는 작업 중 하나라도 실패할 경우 전체 작업을 취소한다.

예를 들어, 중고 거래 상황을 가정할 경우

- 물건을 받기 위해 상대방에게 선입금을 함
- 그런데 벽돌이 도착

위와 같은 상황이 발생했을 경우 `@Transactional` 어노테이션은 이를 롤백할 수 있게 해준다. 

**즉, 어떤 작업에서 오류가 발생했을 경우 이전에 있던 모든 작업들이 성공적이었더라도 아예 없었던 일처럼 완전히 되돌리는 것이 트랜잭션의 개념이다.**

=> 데이터베이스를 다룰 때, 트랜잭션을 적용하면 데이터의 추가, 갱신, 삭제 등으로 이루어진 작업을 처리하던 중 오류가 발생하더라도 롤백이 가능하다.

테스트 메서드에서 @Transactional을 사용하면 메서드가 종료될 경우 자동적으로 롤백된다.

단, Id는 롤백되지 않는다.

왜냐하면 주로 id에 대해 작업을 할 때 Auto Increment옵션을 적용하곤 하는데, 해당 옵션은 트랜잭션의 범위 밖에서 동작하기 때문이다.  이러한 이유는 `동시성` 때문인데, 만약 트랜잭션 범위 내에서 롤백이 가능하다고 한다면, 회원가입이 동시에 일어날 때, 트랜잭션이 성공할 수도 있고 실패할 수도 있는데, 각 트랜잭션이 다른 사람의 회원 가입 성공 여부를 기다렸다가 id를 부여 받아서 결과를 전달하기까지 얼마나 많은 시간이 걸릴지 모른다.

<br>

## 04_Lombok 어노테이션

### @Builder

빌더란 인스턴스 생성을 위한 디자인 패턴 중 하나로, 롬복의 @Builder를 사용하면 따로 빌더 클래스를 구현하지 않아도 빌더 패턴을 사용해 오브젝트를 생성할 수 있다.

빌더 패턴의 장점으로는 기존에 생성자를 사용해서 인스턴스를 생성할 경우 매개변수의 입력 순서를 반드시 지켜줘야 했던 것을 원하는 순서대로 입력해도 되게끔 한다.

<br>

### @NoArgsConstructor

매개변수가 없는 기본 생성자를 구현해준다.

사용하면 아래 예시와 같이 기본 생성자를 구현하는 것과 같다.

```java
public Car() {
}
```

<br>

### @AllArgsConstructor

> 클래스의 모든 맴버 변수를 매개변수로 받는 생성자를 구현해준다.

<br>

### @RequiredArgsConstructor

> `final`이나 `@NotNull`인 필드 값만 파라미터로 받는 생성자를 구현해준다.

<br>

### @Data

> 롬복의 어노테이션 중 하나로 클래스 맴버 변수의 Getter/Setter 메서드를 구현해준다.

<br>

### @Getter

> 롬복의 어노테이션 중 하나로 클래스 맴버 변수의 Getter메서드를 구현해준다.

<br>

### @Setter

> 롬복의 어노테이션 중 하나로 클래스 맴버 변수의 Setter메서드를 구현해준다.

<br>

## 05_Domain 어노테이션

### @Entity

> 실제 DB 테이블과 맵핑될 클래스임을 의미한다.

<br>

### @Table

> Entity 클래스에 맵핑할 테이블의 정보를 정의한다.

```java
@Table(name="USER")
```

<br>

### @Id

> 해당 테이블의 필드 값이 기본키(PK)임을 의미한다.

<br>

### @GeneratedValue

> 기본키(PK)의 생성 규칙을 정의한다.

<br>

### @Column

> 해당 필드가 테이블의 칼럼과 맵핑됨을 의미함.

굳이 써주지 않더라도 자동적으로 맵핑되지만, 기본 네이밍 규칙에 의해 맵핑됨. 예) userId => user_id

사용하는 이유는 `@Column(name="username")`과 같이 테이블 명을 직접 지정해주거나, VARCHAR(255)와 같은 값을 VARCHAR(500)으로 늘리는 등의 추가적인 변경이 필요한 옵션이 있을 경우에 사용한다.

<br>

## 06_Springboot Test

### @SpringbootTest

> 스프링부트 어플리케이션을 테스트하기 위한 Context를 생성

각 테스트 메서드는 Spring Context를 공유함 => 따라서 전체 테스트를 한번에 수행한다면 한 테스트의 결과가 다른 테스트에 영향을 미침

<br>

### @Test

> 테스트 메서드를 지정함

<br>

### @DisplayName

> 테스트가 실행될 때 어떤 이름으로 보여줄지 지정

```java
@Test
@DisplayName("유저 생성 테스트")
public void createUserTest() {
}
```

<br>

### @AfterAll

> 모든 테스트를 수행하고 나서 최초 1회 수행되는 메서드를 지정

<br>

### @AfterEach

> 각 테스트 메서드가 수행되기 전에 실행되는 메서드를 지정

SpringbootTest의 테스트 메서드는 Context를 공유하므로, 하나의 테스트 결과가 다른 테스트에 영향을 미칠 수도 있다.

따라서 다른 테스트에 영향을 주지 못하게 전체 테스트 메서드를 실행했을 때, 각 테스트 메서드가 끝나고 보통 `@AfterEach`로 DB에 들어간 데이터를 싹 비워주는 행동을 한다.

<br>

### @BeforeAll

> 모든 테스트를 수행하기 전 최초 1회 수행되는 메서드를 지정

코틀린의 경우 `@JvmStatic`을 붙여줘야 한다. 

<br>

### @BeforeEach

> 각 테스트 메서드가 수행된 후에 실행되는 메서드를 지정

 모든 테스트 메서드에서 동일한 행동을 똑같이 한다면, `@BeforeEach`어노테이션을 붙인 메서드를 통해 해당 행동을 정의하여, 각 테스트 메서드가 실행되기 전에 한 번씩 실행되게 정의해줄 수 있다.

<br>

## 07_Swagger

<br>

## 08_SpringSecurity
