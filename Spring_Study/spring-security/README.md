# Spring-Security

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

