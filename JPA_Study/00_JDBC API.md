# 00_JDBC API

> JDBC; Java DataBase Connectivity

JPA를 학습하기 전에 JDBC에 대해 알아보자.

`Spring Data JDBC`, `Spring Data JPA` 등과 같은 기술이 등장하면서 JDBC API를 직접적으로 사용하는 일은 줄어들었다. 하지만, Spring Data JDBC, Sprind Data JPA와 같은 기술도 데이터베이스와 연동하기 위해 `내부적으로 JDBC를 이용`하기 때문에 JDBC의 동작 흐름에 대해 알 필요가 있다.

## 1. 개요

Java 어플리케이션에서 데이터베이스와 연결하여 자원을 사용할 수 있도록 해주는 표준 API로, 데이터베이스에 자료를 저장, 업데이트 또는 데이터베이스에 저장된 데이터를 Java에서 사용할 수 있도록 해준다.

즉,  **다양한 형태의 관계형 데이터베이스에 접속하여 자바 프로그래밍 언어와 데이터베이스 사이에 데이터를 주고받을 수 있도록 지원하는 표준 자바 응용 프로그래밍 인터페이스**이다.

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/3e271a00-815a-4bf5-9da9-3069caab4be6)

JDBC는 3가지 기능을 표준 인터페이스로 정하여 제공한다.

- java.sql.Connection : 연결
- java.sql.Statemnet : SQL 구문 전달
- java.sql.ResultSet : SQL 결과 응답

<br>

## 2. JDBC의 흐름

### 1) 동작 흐름

Java 어플리케이션 내에서 JDBC API를 사용하기 위해서는 JDBC 드라이버를 먼저 로딩하여 데이터베이스와 연결한 뒤에 JDBC API를 사용해서 데이터베이스에 접근한다.

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/e1c38262-1c7a-4086-882b-855bbbebe9ec)

### 2) 사용 흐름

JDBC API의 구성 요소들의 동작 흐름은 다음과 같다.

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/6e09d05a-a748-44bb-a6bc-9c0e380a6c32)

- 사용하고자 하는 `JDBC 드라이버`를 로딩
- DriverManager를 통해 데이터베이스와 연결되는 Session(세션)인 `Connection 객체 생성`
- SQL 쿼리 문자열을 입력으로 받아 쿼리문을 실행하기 위한 `Statement 객체 생성`
- 생성된 Statement 객체를 이용해 Query 실행
- 실행된 SQL 쿼리문에 대한 결과 데이터 셋인 `ResultSet객체 조회`
- ResultSet, Statement, Connection 객체를 순서대로 Close (생성한 역순으로 Close함)

<br>

## 3. 커넥션 풀(Connection Pool)

JDBC API를 사용하여 데이터베이스와 연결하면서 매번 Connection 객체를 생성하고 소멸시키는 것은 비용이 많이 드는 작업이다.

### 1) 커넥션 객체 생성 과정

- 어플리케이션에서 JDBC 드라이버를 통해 커넥션을 조회한다.
- JDBC 드라이버는 DB와 TCP/IP 3-way handshake와 같은 과정을 거쳐 네트워크를 연결한다.
- 네트워크 연결이 성공적으로 이루어지면, 드라이버는 ID, PW, 기타 부가 정보들을 DB에 전달한다.
- DB는 내부 인증 작업을 거친 후 내부에 DB 스키마 등을 생성한다.
- DB는 커넥션 생성이 완료되었다는 응답을 보내고, JDBC 드라이버는 커넥션 객체를 생성하여 클라이언트에 반환한다.

이처럼 Connection 객체를 생성하고 연결하는 작업은 단순한 작업이 아니기 때문에 비용이 많이 들고, 비효율적이다.

따라서 JDBC는 `커넥션 풀(Connection Pool)`이라는 것을 사용하여 Connection 객체를 미리 생성해서 보관하고, 필요할 때마다 꺼내서 사용할 수 있도록 Connection 객체를 관리한다.

<br>

### 2) 커넥션 풀 동작 과정

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/cb95f389-20f3-4e97-b971-e7a89b5155a4)

- 어플리케이션을 시작하는 시점에 필요한 Connection 객체를 미리 생성하여 Connection Pool에 보관한다.
  - 서비스의 특징과 스펙에 따라 생성되는 Connection 객체의 수는 다르지만 일반적으로 기본 값인 10개를 생성한다.
- Connection Pool에 있는 Connection 객체는 TCP/IP로 DB와 연결되어 있는 상태이기 때문에 요청이 오면 즉시 SQL을 DB에 전달할 수 있는 상태이다.
- Connection 요청이 오면 Connection Pool에 있는 Connection 객체 중 하나를 반환하여 응답한다.
  - 매번 요청이 올 때마다 새로운 Connection 객체를 생성하지 않고, 이미 생성되어 있는 Connection 객체를 참조하여 사용한다.

<br>

## 4. HikariCP

HikariCP는 가벼운 용량과 빠른 속도를 가지는 우수한 성능의 JDBC Connection Pool Framework이다.

Springboot 2.0 이후부터는 커넥션 풀을 관리하기 위해 HikariCP를 사용하고 있다.

아마 Springboot로 프로젝트를 하다가 DB쪽과 관련된 에러가 발생하면 IDE 터미널 창에 HikariCP 어쩌구 저쩌구 하는 것을 본 적이 있을 것이다.

HikariCP는 미리 정해놓은 크기만큼의 Connection을 Connection Pool에 담아 놓고, 이후 요청이 들어오면 Thread가 Connection을 요청하고, Connection Pool에 있는 Connection을 연결해주는 방식으로 동작한다.

<br>