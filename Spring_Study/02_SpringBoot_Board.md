![image](https://user-images.githubusercontent.com/93081720/173190210-cc3509be-bf6f-4fb0-970a-55e28eba1753.png)

# 02_SpringBoot_Board

스프링부트로 질문 게시판 웹 페이지 만들기(점프 투 스프링 부트 참조)

## 01_Basic Project Structure

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

## 02_JPA (Java Persistence API)

JPA(Java Persistence API)는 자바 기반 프로젝트의 ORM 표준으로 사용하는 인터페이스의 모음이다.

=> JPA는 인터페이스이므로 반드시 인터페이스를 구현한 실제 클래스가 필요하다. JPA를 실제 구현한 대표적인 클래스는 Hibernate가 있다.

<br>

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

## 03_모델, 엔티티(Model, Entity)

엔티티(Entity)는 데이터베이스 테이블과 맵핑되는 자바 클래스를 말한다. 

엔티티는 모델(Model) 또는 도메인(domain) 모델이라고도 부른다. => 폴더명을 쓸 경우 model 또는 domain으로 쓴다. 

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

## 04_레포지토리(Repository)

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

<br>

## 05_서비스(Service)

대부분의 규모있는 스프링부트 프로젝트는 컨트롤러에서 리포지터리를 직접 호출하지 않고 중간에 서비스(Service)를 두어 데이터를 처리한다. 서비스는 스프링에서 데이터 처리를 위해 작성하는 클래스이다.

### 서비스가 필요한 이유는 무엇인가?

#### 모듈화

서비스를 만들지 않고 컨트롤러에서 해당 기능을 구현하려고 한다면, 모든 컨트롤러가 동일한 기능에 대해 중복으로 구현해야한다. => 서비스는 모듈화를 위해서 필요하다

#### 보안

컨트롤러가 서비스를 통해서만 DB에 접근하도록 구현하는 것이 보안상 안전하다. 이렇게 하면 컨트롤러에 보안 문제가 발생하더라도 리포지토리에 접근하여 뭔가 조작할 수 없다



### 엔티티 객체와 DTO 객체

엔티티 클래스는 데이터베이스와 직접 맞닿아 있는 클래스이기 때문에 컨트롤러나 타임리프 같은 템플릿 엔진에 전달하여 사용하는 것은 좋지 않다. 왜냐하면 컨트롤러나 타임리프에서 사용하는 데이터 객체는 속성을 변경하여 비즈니스적인 요구를 처리해야 하는 경우가 많은데 엔티티를 직접 사용하여 속성을 변경한다면 테이블 컬럼이 변경되어 엉망이 될수도 있기 때문이다.

이러한 이유 때문에 엔티티 클래스 대신 사용할 DTO(Data Transfer Object) 클래스가 필요하다. 엔티티 객체를 DTO 객체로 변환하는 일을 서비스에서 담당한다.

=> 서비스는 컨트롤러와 리포지토리의 중간에서 엔티티 객체와 DTO 객체를 서로 변환하여 양방향 전달하는 역할을 한다.



#### `Controller -> Service -> Repository` 구조로 데이터를 처리함

<br>

![image](https://user-images.githubusercontent.com/93081720/172428403-cbaa3948-8373-46da-9ea2-5f011367f2c8.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/172432700-304c0a49-cbe9-42fe-bb63-ffbdbd1d75cf.png)

`model.addAttribute(attributeName, object)` : 모델 객체에 attributeName에 지정된 문자열로 해당 객체를 저장하겠다는 의미

=> 관련된 템플릿에서 지정된 문자열로 해당 객체 정보를 가져와서 렌더링 가능해짐

<br>

## 06_질문 상세 및 URL Prefix

### template에서 variable 맵핑하기

`/question/detail/`과 같은 문자열과 `${question.id}`와 같은 자바 객체의 값을 더할 때는 반드시  `|` 로 좌우를 감싸 주어야 한다 => `{|/question/detail/${question.id}|}`

![image](https://user-images.githubusercontent.com/93081720/172423341-e6a9431e-c060-44a4-a7bb-937255a5afdd.png)

<br>

### Controller의 url에 variable 맵핑하기

![image](https://user-images.githubusercontent.com/93081720/172422995-fb8a560f-206c-43c0-8325-ef886b6ffb40.png)

url 주소에 id와 같은 변수(variable)를 맵핑하기 위해서는 `@PathVariable` 애너테이션을 사용해서 맵핑할 수 있다. 단, 이때 애너테이션 안에 지정해준 문자열 형태의 변수명과 실제 변수명이 일치해야한다.

`@RequestMapping(value = )`에서 value는 써도 되고 안 써도 상관없다

<br>

### 사용자 정의 예외 처리

![image](https://user-images.githubusercontent.com/93081720/172434129-db0c743e-f209-4980-bbc3-91c69760defd.png)

![image](https://user-images.githubusercontent.com/93081720/172434377-867985ce-05fe-43bc-b53c-269eda531212.png)

<br>

### URL Prefix(URL 프리픽스)

 ※ Controller의 클래스 단위의 URL 맵핑(URL prefix)는 필수 사항은 아니고, Controller의 성격에 맞게 사용하면 됨

![image](https://user-images.githubusercontent.com/93081720/172432068-61ea647f-3c8d-4205-9bf3-10a86aa96eb4.png)

<br>

## 07_답변 등록

### AnswerController 작성

![image](https://user-images.githubusercontent.com/93081720/173193182-2c8cabba-eddd-4a09-bf86-325eb0b11641.png)

#### createAnswer메서드 생성

![image](https://user-images.githubusercontent.com/93081720/173192272-bb2bcbf9-a215-41dd-ba35-8bdaff23107f.png)

- `@PostMapping`: @RequestMapping과 마찬가지로 url 맵핑 역할을 하지만, POST요청만 받아들일 경우에 사용하는 애너테이션
- `@RequestParams` : 답변으로 입력한 내용을 요청 매개변수로 받기 위해 지정한 애너테이션. 답변 템플릿에 content라는 이름으로 썼기 때문에 여기서도 똑같이 content로 써야함

### AnswerService 작성

![image](https://user-images.githubusercontent.com/93081720/173193447-c72e1aef-3dee-4258-93b6-af9c18610086.png)

<br>

#### 답변 등록 form 작성

![image](https://user-images.githubusercontent.com/93081720/173193542-d0f20aca-571c-49ea-973a-1ce97d0b42bd.png)

- `#`: 타임리프가 제공하는 유틸리티
  - `#list.size(이터러블 객체)`: 객체의 길이를 반환
  - `#temporals.format(날짜객체, 날짜포맷)` :날짜객체를 날짜포맷에 맞게 변환

<br>

## 08_템플릿 상속

모든 템플릿의 부모가 되는 부모 템플릿을 생성함 => `layout.html`

이는 django의 base.html과 같음

![image](https://user-images.githubusercontent.com/93081720/173193761-cef06317-ef55-44ba-be70-5a0bb38eac6e.png)

<br>

### layout.html 생성

![image](https://user-images.githubusercontent.com/93081720/173193819-eeaaa8a8-bcb5-458f-b19a-87dd90d4f760.png)

- 부모 템플릿에 타임리프의 "content"라는 이름의 블록태그를 넣음 => django base.html의 block태그와 동일한 기능을 함

<br>

### 자식 템플릿에서 부모 템플릿을 상속함

![image](https://user-images.githubusercontent.com/93081720/173193904-20777887-11c9-49f5-9b79-97401fc179d3.png)

- html태그의 `layout:decorate="~{layout}"` 적용 => layout이라는 이름의 부모 템플릿을 상속하겠다는 의미
  - django DTL의 extends와 유사
- div태그의 `layout:fragment="content"`적용 => "content"라고 명명된 블록태그에 띄울 내용이라는 것을 지정함

<br>

## 09_폼(Form)과 질문/답변 등록

### 폼(Form)

유효성(validation) 검사 도구

입력된 값에 대한 유효성을 검사함으로써 잘못된 데이터가 입력되는 경우와 외부의 악의적 공격 및 데이터 손상에 대해 방어가 가능함

### Spring Boot Validation 라이브러리 설치

Spring Boot Validation 라이브러리를 통해서 입력값에 대해 검증이 가능함

![image](https://user-images.githubusercontent.com/93081720/173194272-042ef2d2-9dfc-4486-ba90-1b66cc770bf5.png)

<br>

#### Spring Boot Validation 라이브러리의 적용 가능한 애너테이션들

![image](https://user-images.githubusercontent.com/93081720/173194318-c718c0f4-bc71-4348-b176-bfdb9142381b.png)

이외에도 여러가지 애너테이션들이 존재함 (https://beanvalidation.org/)

<br>

### 폼 클래스 작성

![image](https://user-images.githubusercontent.com/93081720/173194427-63457620-4939-45d5-9cf5-04bcfa3bca9b.png)

<br>

### QuestionForm 작성

Question 엔티티의 필드 항목들에 대해 validation 애너테이션들을 적용시킴

getter와 setter 메서드도 작성 필요

![image](https://user-images.githubusercontent.com/93081720/173194472-be105552-d716-4530-810f-48a124d37ee0.png)

<br>

### QuestionService 수정

#### create 메서드 작성

![image](https://user-images.githubusercontent.com/93081720/173191259-841e3d75-c810-4d3d-83a4-0dbe27923c45.png)

<br>

### QuestionController 수정

#### 질문 등록 GET

질문 등록인데 왜 GET요청을 보내냐고 궁금해할 수도 있는데, 일단 질문 등록을 위한 템플릿을 조회(GET)해야하기 때문임

![image](https://user-images.githubusercontent.com/93081720/173194686-8d24b01c-dea7-4b3c-8df6-22a43b7bd947.png)

<br>

#### 질문 등록 POST

오류가 발생할 경우 질문 생성 html로 다시 이동시키고, Form Validation을 통과했을 경우 질문을 생성하고 redirect함

![image](https://user-images.githubusercontent.com/93081720/173195191-1d1794a7-87f6-477e-8e34-7e0d5a552cf6.png)

- `@Valid` : QuestionForm 객체에 대해 필드 유효성 검증 애너테이션 기능 적용이 가능하게 해주는 애너테이션
- `BindingResult` : BindingResult 매개변수는 @Valid 애너테이션으로 인해 검증이 수행된 결과를 의미하는 객체이다

> BindingResult 매개변수는 항상 @Valid 애너테이션이 적용된 매개변수 바로 뒤에 위치해야한다. 만약 이 2개의 매개변수의 위치가 정확하지 않다면 @Valid만 적용되어 입력값 검증 실패 시 400(BadRequest) 에러가 발생한다.

<br>

#### 질문 등록 템플릿 작성

createQuestion 메서드에 questionForm을 매개변수로 입력했으니 model 객체로 따로 addAttribute를 하지 않아도 템플릿에서 th:object에 questionForm객체를 바인딩 가능함

![image](https://user-images.githubusercontent.com/93081720/173194772-72b5d266-e2e9-4afd-ad09-451ab4a66583.png)

- `th:if="${#fields.hasAnyErrors()}"` : 에러가 있을 경우 true가 됨
- `th:each="err: ${#fields.allErrors()}"` : 발생한 모든 에러에 대해 반복 구문을 돌려서 th:text="${err}"로 출력함
- `th:field="*{필드명}"` : 잘못된 데이터 입력 시 오류 메시지 출력과 함께 페이지가 새로고침 되면서 기존에 입력된 데이터가 초기화되는 현상을 방지하기 위해 적용 => 입력 중이던 데이터를 남김

<br>

### AnswerForm 작성

![image](https://user-images.githubusercontent.com/93081720/173195760-868806e3-d115-4125-8aef-85eeb9fe689d.png)

<br>

### AnswerController 수정

#### createAnswer 메서드 수정

![image](https://user-images.githubusercontent.com/93081720/173195819-e76c7568-1280-4a94-80ab-42a8c5ef2308.png)

<br>

#### 답변 등록 템플릿 수정

질문 생성 템플릿에 적용했던 요소들을 마찬가지로 추가 작성하여 수정함

![image](https://user-images.githubusercontent.com/93081720/173195938-f7ae6c24-c211-4c6b-aeb6-7974eb44d2f5.png)

- 현재 답변 등록이 질문 상세(detail) 페이지에 댓글(comment)처럼 등록하고 있는 상태인데, detail 페이지 템플릿에서 answerForm 객체를 사용하기 위해서는 QuestionController의 detail 메서드에 answerForm객체를 매개변수로 전달해줘야한다. 

![image](https://user-images.githubusercontent.com/93081720/173195900-692096ad-5e34-4f72-8cfc-1de2ed2189ca.png)

<br>

## 10. 공통 템플릿

에러 메세지와 같이 반복적으로 사용되는 코드는 공통 템플릿으로 만들고 필요한 부분에 삽입하여 쓸 수 있도록 변경하는 것이 좋음

![image](https://user-images.githubusercontent.com/93081720/173397023-c771964f-587d-4f1d-ac0b-a87d6e0a9af3.png)

- `th:fragment` 속성을 사용하면 공통 템플릿으로서 다른 템플릿에 넣을 수 있는 기능이 추가 된다
- 삽입하고자 하는 템플릿에 `th:replace` 속성을 지정하고 `템플릿 파일 명 :: th:fragment 속성으로 지정한 명칭`을 써줌으로써 템플릿 삽입이 가능하다
  - th:replace="form_errors :: formErrorsFragment"의 의미는 form_errors라는 파일의 th:fragment가 formErrorsFragment로 지정된 엘리멘트로 교체하라는 의미이다. 

#### question_create_form.html 수정

![image](https://user-images.githubusercontent.com/93081720/173396837-8d18ba73-e0af-423c-b285-4155e4c74924.png)

<br>

#### question_detail.html 수정

![image](https://user-images.githubusercontent.com/93081720/173397931-d20fc2be-946d-41d5-90fb-17819f77a9eb.png)

<br>

## 11_Navbar 추가

#### navbar.html 추가 및 layout.html 삽입(bootstrap.js 적용)

![image](https://user-images.githubusercontent.com/93081720/173400839-aebf97fd-9de5-4210-af34-0b51e58bd12a.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/173401151-93bef6f4-d764-4a0b-8ae4-0ec9042489af.png)

<br>

## 12_페이징

### 대량의 테스트 데이터 만들기







----

### Spring Initializer

스프링 프로젝트 개발을 도와주는 웹 페이지

초기 개발환경을 설정하여 압축파일로 제공해준다

[Spring Initializer(스프링 이니셜라이저)](https://start.spring.io/)

