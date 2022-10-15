# Spring-Security

## 스프링 시큐리티

### 1. MySQL DB 및 사용자 생성

```sql
create user 'cos'@'%' identified by 'cos1234';
GRANT ALL PRIVILEGES ON *.* TO 'cos'@'%';
create database security;
use security;
```

<br>

### 2. 프로젝트 생성

Springinitializer 사용

설정

- spring boot devtools
- lombok
- spring data jpa
- mysql driver
- spring security
- mustache
- spring web

<br>

### 3. application.properties 설정

```
server.port=8080
server.servlet.context-path=/
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.force=true
server.servlet.encoding.enabled=true

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/security?serverTimezone=Asia/Seoul
spring.datasource.username=root
spring.datasource.password=5041

spring.mvc.view.prefix=/templates/
spring.mvc.view.suffix=.mustache

spring.jpa.hibernate.ddl-auto=create
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql=true
```

<br>

### 4. IndexController 생성

```java
@Controller
public class IndexController {

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본 폴더: src/main/resources/
        // viewresolver 설정: templates (prefix), .mustache(sufix) => 생략 가능
        return "index"; // src/main/resources/templates/index.mustache
    }
}
```

<br>

서버를 실행하면 8080포트로 접속 시, 다음과 같은 화면이 나옴

스프링부트 시큐리티 설정 시 디폴트 페이지

![image](https://user-images.githubusercontent.com/93081720/195871108-1f80b747-b907-4b32-b7fa-e2e60769985f.png)

username에는 user를, 비밀번호는 서버를 실행시킬 때 생성되는 비밀번호를 복사해서 입력하면 된다.

![image](https://user-images.githubusercontent.com/93081720/195871369-5dff3506-32dd-4d7d-8986-cfcc40e6e0b5.png)

<br>

### 메서드 구현

오버라이드하는 클래스 또는 인터페이스를 불러온 다음 다음과 같이 생성 > 메서드 구현을 누르면

![image](https://user-images.githubusercontent.com/93081720/195965625-46056030-2ed0-4161-afe2-958d4e445540.png)

다음과 같이 손쉽게 구현 및 오버라이딩이 가능하다

![image](https://user-images.githubusercontent.com/93081720/195965731-ffcdfba9-f3f5-404f-a0f4-90b98b9c2903.png)

<br>

### 유저 권한 변경

sql 구문으로 유저의 권한을 변경함

```sql
update user set role = 'ROLE_MANAGER' where id = 2;
update user set role = 'ROLE_ADMIN' where id = 3;
commit;
```

<br>

## 구글 로그인(Oauth2.0)

### 1. 패키지 설치

![image](https://user-images.githubusercontent.com/93081720/195970493-9b614537-a194-4bd1-afba-df8b1f3dd87a.png)

<br>

### 2. 구글 콘솔 api 등록

단, oauth2-client 라이브러리 설치 시 리디렉션 uri는 고정임

![image](https://user-images.githubusercontent.com/93081720/195970455-31f7f047-b310-4dbc-8520-72c5fade0652.png)

<br>

### 3. application.properties 설정 추가

![image](https://user-images.githubusercontent.com/93081720/195970748-e43162eb-838d-4ce8-b6ed-adf6aecbd602.png)
