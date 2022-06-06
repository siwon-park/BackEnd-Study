![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 01_Spring_Basic



## 01_MVC Pattern



### Model



### View



### Controller







## Basic Project Structure

<br>

![image](https://user-images.githubusercontent.com/93081720/172045696-f2418b0e-fd0f-46fc-8298-4b41947f8de2.png)

<br>

- src/main/resources
  - java파일을 제외한 HTML, CSS, JS, 환경 파일 등이 작성되는 공간
  - /templates
    - HTML 템플릿이 저장하는 공간
  - /static
    - CSS, JS, 이미지(png, jpg)파일을 저장하는 공간
- src/test/java
  - 프로젝트에서 작성한 파일을 테스트하기 위한 테스트 코드를 작성하는 공간
- build.gradle 파일
  - 그레이들(Gradle)이 사용하는 환경 파일
  - 프로젝트를 위해 필요한 플러그인과 라이브러리 등을 기술함 => django의 settings.py와 유사



<br>

![image](https://user-images.githubusercontent.com/93081720/172045895-74cf3374-b25a-4e19-b266-89b392911428.png)

<br>

`@Controller` : 해당 클래스를 스프링부트의 컨트롤러로 지정하는 애너테이션

`@RequestMapping(url)` : 요청된 url을 해당 메서드와 맵핑하는 애너테이션

`@ResponseBody` : 해당 메서드의 리턴값이 요청된 url에 대한 결과라는 것을 지정하는 애너테이션 

> 만약 `@ResponseBody`를 생략하면 리턴값과 동일한 이름의 템플릿 파일을 찾게 된다.



<br>

## JPA (Java Persistence API)

JPA(Java Persistence API)는 자바 기반 프로젝트의 ORM 표준으로 사용하는 인터페이스의 모음이다.

=> JPA는 인터페이스이므로 반드시 인터페이스를 구현한 실제 클래스가 필요하다. JPA를 실제 구현한 대표적인 클래스는 Hibernate가 있다.



### JPA 및 H2 Database 설치

H2 데이터베이스는 주로 개발용이나 소규모 프로젝트에서 사용되는 파일 기반의 경량 데이터베이스이다. => django의 sqlite3와 유사

![image](https://user-images.githubusercontent.com/93081720/172048322-1e238f42-3d0d-4cb0-b01f-4278f0efad56.png)

<br>

※ Refresh Gradle Project

build.gradle파일에 뭔가 추가하고나면 오른쪽 상단에 보면 해당 아이콘이 생기는데 이를 눌러서 최신화를 시켜줘야한다.

![image](https://user-images.githubusercontent.com/93081720/172046530-0d9ca067-da64-44bb-843d-744285bf4f12.png)

<br>

#### application.properties파일 수정

설치한 JPA와 H2 DB를 이용하기 위해서는 application.properties파일을 다음과 같이 수정해줘야한다.

![image](https://user-images.githubusercontent.com/93081720/172048474-89c2f712-b3ca-4bbd-9271-267c6b035013.png)

<br>

#### h2

- spring.h2.console.enabled=true : H2 콘솔의 접속을 허용할지 여부
- spring.h2.console.path : 콘솔 접속을 위한 경로. 위의 경우 localhost:8080/h2-console로 url이 지정됨
- spring.datasource.url : db파일의 위치. 위의 경우 `~/local` 이라고 썼으므로 홈 디렉토리에 `local.mv.db`라는 파일을 만들어야한다.(만약 `~/test`라고 썼다면 `test.mv.db`라고 생성해야한다). 빈 파일만 생성하면 됨.
  - localhost:8080/h2-console로 접속하여 jdbc url을 변경해준다.
  - ![image](https://user-images.githubusercontent.com/93081720/172047458-efc18f1c-8986-44dc-9858-c756bdda8d9c.png)
- spring.datasource.driverClassName : 데이터베이스 접속시 사용하는 드라이버
- spring.datasource.username : 데이터베이스의 사용자명(기본 값: sa)
- spring.datasource.password - 데이터베이스의 패스워드. 로컬 개발 용도로만 사용하기 때문에 패스워드를 설정하지 않음

<br>

#### JPA

- spring.jpa.properties.hibernate.dialect : 데이터베이스 엔진 종류를 설정
- spring.jpa.hibernate.ddl-auto : 엔티티를 기준으로 테이블을 생성하는 규칙을 정의
  - none : 엔티티가 변경되더라도 DB를 변경하지 않음
  - update : 엔티티의 변경된 부분만 적용
  - validate : 변경사항이 있는지만 검사
  - create : 스프링부트 서버가 시작될 때 모두 drop하고 재생성함
  - create-drop : create와 동일하지만, 종료시에도 모두 drop함

=> 개발환경에서는 보통 update 모드를 사용하고 운영환경에서는 none 또는 validate 모드를 사용함

<br>

## Model (Entity)

엔티티(Entity)는 데이터베이스 테이블과 맵핑되는 자바 클래스를 말한다. 

엔티티는 모델(Model) 또는 도메인 모델이라고도 부른다. => 폴더명을 쓸 경우 model 또는 domain으로 쓴다. 

![image](https://user-images.githubusercontent.com/93081720/172082312-393547b6-adf6-4475-87ee-379381238472.png)![image](https://user-images.githubusercontent.com/93081720/172082369-15a7481e-c55a-4b25-82fe-f1b8fc8f21c9.png)

#### Question Entity 작성 예시)

![image](https://user-images.githubusercontent.com/93081720/172059209-f00b8a22-a345-429c-8c98-2d035c91b9a3.png)

<br>

- `@Id` : 해당 속성을 id값으로 하여 primary key로 지정함
- `@GeneratedValue` : 데이터를 저장할 때, 해당 속성에 값을 따로 세팅하지 않아도 자동으로 1씩 증가하여 저장되게 함
  - strategy : 고유 번호를 생성하는 옵션(생략할 경우, @GenerateValue옵션이 적용된 모든 필드가 동일한 시퀀스로 번호를 생성하기 때문에 고유한 번호를 가질 수 없게됨)
  - GenerationType.IDENTITY : 해당 필드만의 독립적인 번호 시퀀스를 생성

- `@Column` : 해당 테이블의 필드(칼럼)으로 인식하게 함. 없어도 인식하지만 명시적으로 사용함. 테이블 칼럼으로 인식하게 하고 싶지 않다면 `@Transient` 애너테이션을 사용함
  - length : 칼럼의 길이를 설정함
  - columnDefinition : 칼럼의 속성을 정의해줄 때 사용함

- `@OneToMany` : 역참조를 위한 관계를 설정하기 위한 애너테이션
  - mappedBy : 참조 엔티티의 속성명을 지정
  - cascade = CascadeType.REMOVE : 부모가 삭제되면 그 아래에 있는 자식도 전부 삭제되게 하는 옵션


<br>

#### Answer Entity 작성 예시)

![image](https://user-images.githubusercontent.com/93081720/172059267-819ed66f-2cbb-4c05-bf5d-97c4544ad878.png)

<br>

## 레포지토리(Repository)

엔티티, 모델에 의해 생성된 DB 테이블에 접근하는 메서드들(예- findAll, save 등)을 사용하기 위한 **인터페이스**또는 **클래스**를 말한다

데이터 처리를 위해서는 테이블에 어떤 값을 넣거나 값을 조회하는 등의 CRUD가 필요한데, 이러한 CRUD를 어떻게 처리할지 정의하는 계층을 레포지토리(Repository)라고 한다.

![image](https://user-images.githubusercontent.com/93081720/172082724-7be76cfd-1564-49e5-8b96-2cb4fcb27b14.png)![image](https://user-images.githubusercontent.com/93081720/172082782-dc2d005d-ceaf-44df-8303-8faf14ebe58f.png)

<br>

다음과 같이 직접 인터페이스를 작성하고 해당 인터페이스를 구현한 클래스를 통해 레포지토리를 작성하거나

![image](https://user-images.githubusercontent.com/93081720/172082972-abca286d-a35d-4984-994e-05a4c4f5970b.png)

![image](https://user-images.githubusercontent.com/93081720/172083438-226ddc3e-d492-41ba-b417-e567f50875f3.png)

<br>

이미 작성된 인터페이스를 상속받아서 인터페이스를 새로 작성하고 해당 인터페이스를 사용하는 방법이 있다.

- 아래 그림 예시에서는 JpaRepository에 findAll 및 save메서드는 기설계된 상태이고, findByTitle, findByTitleAndContent 및 findByTitleLike를 새롭게 설계한 상태이다.

![image](https://user-images.githubusercontent.com/93081720/172083026-899c5ddf-ece6-4591-ae71-b8ec2e956521.png)

<br>

### Test코드 작성

![image](https://user-images.githubusercontent.com/93081720/172083695-d6af15f2-b047-4bd5-9b9c-6eafff1d5048.png)

- `@SpringBootTest` : 해당 클래스가 스프링부트 테스트 클래스임을 명시하는 애너테이션
- `@Autowired` : 객체를 주입하기 위해 사용하는 애너테이션
  - Setter 및 생성자를 통해서 객체를 주입할 수도 있지만, 순환참조 문제가 발생할 가능성이 있어서 @Autowired 방식보다는 생성자를 통한 객체 주입 방식이 권장된다. 하지만 테스트 코드에서는 생성자를 통한 객체 주입이 불가능하기 때문에 테스트 코드 작성 시에만 주로 사용한다
  - DI(Dependency Injection) : 의존 주입. 스프링이 객체를 대신 생성하여 의존성을 주입함

<br>

#### 생성(POST) 예시)

![image](https://user-images.githubusercontent.com/93081720/172084336-85ad3861-b860-4bc7-87c2-ef67af38d6f7.png)

- `@Test` : 해당 메서드가 테스트 메서드임을 나타내는 애너테이션

![image](https://user-images.githubusercontent.com/93081720/172085701-3736cd54-ddc4-46f6-aed9-fa81810d10f6.png)

<br>

#### 조회(GET) 예시)

![image](https://user-images.githubusercontent.com/93081720/172084550-a62cef52-6dd5-434b-af84-42b43ac9ae71.png)

![image](https://user-images.githubusercontent.com/93081720/172085134-0a66d718-e4c3-4d69-9e50-b9c2ed04e51b.png)

- %는 0개 이상의 문자열이라는 의미를 가진다
  - text% : text로 시작하는 문자열
  - %text : text로 끝나는 문자열
  - %text% : text를 포함하는 문자열

<br>

#### 수정(PUT) 예시)

![image](https://user-images.githubusercontent.com/93081720/172084678-3126c3a7-8082-4028-99d2-e08b9f6e4a31.png)

<br>

#### 삭제(DELETE) 예시)

![image](https://user-images.githubusercontent.com/93081720/172084759-e91bee3c-b693-4434-a82c-38741044fd7b.png)

- `assertEquals(expected, value)` : 기대값과 실제값이 같은지 확인한다 
- `assertTrue(value)` : 값이 참인지 확인한다

<br>

#### 역참조 예시)

![image](https://user-images.githubusercontent.com/93081720/172085853-8b2dcc14-ebdb-41af-a115-d842135f6b35.png)

- `@Transactional` : 메서드가 종료될 때까지 DB세션을 유지시켜주는 애너테이션
  - 테스트 코드를 테스트할 때 해당 애너테이션을 쓰지 않으면 answerList를 조회하는 시점에서 에러가 발생한다. 왜냐하면 questionRepository가 findById를 호출하여 Question 객체를 조회(get)하고 나면 DB세션이 끊어지기 때문이다. (실제 서버상에서는 코드를 실행해도 DB세션이 종료되지 않기 때문에 에러가 발생하지 않는다.)
  - 이후 실행되는 getAnswerList() 메서드는 세션이 종료된 상태에서 호출되기 때문에 현재 q에는 답변 리스트에 대해 조회한 데이터가 없는 상태에서 조회 메서드가 호출된 것이므로 에러가 발생한다. 답변 데이터 리스트는 q 객체를 조회할때 가져오지 않고 getAnswerList() 메서드를 호출하는 시점에 가져오기 때문이다.
  - 이렇게 필요한 시점에만 데이터를 가져오는 방식을 `Lazy 방식`이라고 한다. 반대로 q객체를 조회할 때 답변 리스트까지 전부 가져오는 방식을 `Eager 방식`이라고 한다.
  - `@OneToMany`, `@ManyToOne` 애너테이션의 옵션으로 `fetch=FetchType.LAZY` 또는 `fetch=FetchType.EAGER` 처럼 데이터를 가져오는 방식을 설정할 수 있다.



----

### Spring Initializer

스프링 프로젝트 개발을 도와주는 웹 페이지

초기 개발환경을 설정하여 압축파일로 제공해준다

[Spring Initializer(스프링 이니셜라이저)](https://start.spring.io/)



### Questions

- maven과 gradle의 차이점?
  - gradle: 그루비(Groovy)를 기반으로한 빌드 도구로, Maven과 같은 이전 세대 빌드 도구의 단점을 보완하고 장점을 취합하여 만든 빌드 도구
- Bean이란 무엇인가?
- DI(Dependency Injection)란 구체적으로 무엇인가?