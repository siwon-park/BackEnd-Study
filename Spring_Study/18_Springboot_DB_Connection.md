# 18_스프링부트 DB 연결

별 건 아니지만, DB 사용 시 연결에 대해 기록하고자 작성함

## applictaion.properties

![image](https://user-images.githubusercontent.com/93081720/205891987-41df11ed-aaf9-4770-b081-a20c6a5340c9.png)

### url

`jdbc:mysql://localhost:3306/security?serverTimezone=Asia/Seoul&characterEncoding=UTF-8`

예시로 설명

#### mysql

DB 종류

#### localhost:3306

DB의 주소

배포된 서버의 DB일 경우 localhost 대신 배포 서버의 도메인을 작성해주면 된다.

#### security

database이름

만약 database이름이 example일 경우에는 다음과 같이 작성한다.

`jdbc:mysql://localhost:3306/example?serverTimezone=Asia/Seoul&characterEncoding=UTF-8`

#### serverTimezone

DB의 서버 시간대를 설정

#### characterEncoding

문자열 인코딩 방식을 정의

<br>

### username / password

DB에 설정한 계정명과 비밀번호

<br>

### driver-class-name

(mysql 사용 한정) 원래 com.mysql.jdbc.Driver와 com.mysql.**cj**.jdbc.Driver 두가지가 있는데, 전자는 Deprecated 되었으므로, **com.mysql.cj.jdbc.Driver**를 사용

<br>

## application.yml

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/example?serverTimezone=UTC&characterEncoding=UTF-8
    username: root
    password: 0000
```

