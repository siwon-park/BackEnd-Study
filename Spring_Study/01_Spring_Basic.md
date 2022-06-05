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



#### Question Entity 작성 예시)

![image](https://user-images.githubusercontent.com/93081720/172059209-f00b8a22-a345-429c-8c98-2d035c91b9a3.png)

<br>

- `@Id`
- `@GeneratedValue`
- `@Column`

<br>

#### Answer Entity 작성 예시)

![image](https://user-images.githubusercontent.com/93081720/172059267-819ed66f-2cbb-4c05-bf5d-97c4544ad878.png)

<br>







----

### Spring Initializer

스프링 프로젝트 개발을 도와주는 웹 페이지

초기 개발환경을 설정하여 압축파일로 제공해준다

[Spring Initializer(스프링 이니셜라이저)](https://start.spring.io/)



### Questions

- maven과 gradle의 차이점?
  - gradle: 그루비(Groovy)를 기반으로한 빌드 도구로, Maven과 같은 이전 세대 빌드 도구의 단점을 보완하고 장점을 취합하여 만든 빌드 도구
- Bean이란 무엇인가?