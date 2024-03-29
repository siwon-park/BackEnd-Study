# 01_JPA 소개

JPA란?

## 1. SQL 중심적인 개발의 문제점

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/0d8e5bc6-6cc4-4820-ab00-b1b0554a8806)

일반적으로 자바 어플리케이션은 `JDBC API`를 이용해 SQL을 데이터베이스에 전달하고 결과를 받는다.

<br>

### 1) 무한 반복, 지루한 코드

JPA를 사용하지 않고 개발할 경우 JDBC를 통해 SQL을 다뤄야 하는데, 자바 객체를 SQL로, SQL을 자바 객체로 바꾸는 작업의 무한 반복이다.

- 간단한 CRUD만 하더라도 많은 양의 코드를 작성해야만 한다.
- 비즈니스 로직 수정에 따른 자바 객체 수정 필요 시, 이와 연관된 쿼리문도 전부 다 바꿔줘야 한다.
- 엔티티의 신뢰성에 의문이 생긴다. 엔티티를 다른 사람이 만들었을 때, 해당 쿼리문에 대해서 잘 알지 못하니 메서드를 통해 해당 엔티티를 호출하는 것에 의문이 생긴다.

결국, SQL 의존적인 개발을 피하기 어렵다.

<br>

### 2) 패러다임의 불일치(객체 지향 vs 관계형 데이터베이스)

객체 지향 프로그래밍과 관계형 데이터베이스의 패러다임이 다르다.

객체 지향 프로그래밍은 추상화, 캡슐화, 정보은닉, 상속 등의 객체지향적 사고를 지향하는 반면에, 관계형 데이터베이스의 목적은 데이터를 잘 저장하는 것에 있다.

- 상속; 객체는 상속이 있지만, 관계형은 없음(슈퍼타입 - 서브타입 관계는 있음)
  - 객체의 상속관계를 테이블에 맵핑하려면 쿼리문을 2번 작성해야함
- 연관관계; 객체는 참조를 통해 연관관계를 가져올 수 있지만, 관계형은 PK, FK, JOIN 등을 활용함
  - 객체는 역참조가 불가능 할 때가 있지만, 관계형은 참조/역참조가 가능하다.
  - 객체를 관계형 데이터베이스에서 하듯이 FK를 통해 객체를 찾게 한다면, 객체 지향적 특징을 잃어버리게 된다.
  - 그렇다고 데이터베이스를 객체 답게 모델링을 하면, 맵핑 작업만 늘어난다.

결국 이러한 패러다임의 불일치를 해결하기 위해  코드를 더 작성하거나, 작업을 반복해야 할 수도 있다.

하지만 JPA를 사용한다면, 개발자는 자바 컬렉션을 사용하듯이 간단하게 JPA에 객체를 저장하면 된다.

따라서 JPA를 사용하면 객체 지향적 특징을 살리고, 패러다임의 불일치도 함께 해결할 수 있다.

#### 객체 그래프

다음과 같은 객체 그래프가 있다고 할 때,

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/d6604556-9dbb-4012-86b1-b3f4753a80aa)

Java에서는 객체 참조를 통해 자유롭게 객체 그래프를 이동하여 객체를 참조할 수 있다.

그러나, SQL에서는 처음 실행하는 SQL 구문이 무엇이냐에 따라 탐색할 수 있는 객체 그래프의 범위가 정해지기 때문에 제약이 많다. 단순 조인만으로 해결이 불가능할 수도 있다.

#### 비교

데이터베이스에서 같은 행을 조회했을 때, 조회할 때마다 동일한 인스턴스를 반환하지 않는다. `new`키워드로 새롭게 맵핑된 자바 객체를 반환하기 때문이다.

하지만, JPA를 사용하여 객체를 조회하게 되면 항상 같은 객체임을 보장해준다.

따라서 검증 로직이나, 테스트 코드를 작성함에 있어 더욱 편의성을 가져갈 수 있다.

<br>

## 2. JPA (Java Persistence API)

>  Java Persistence API

JPA는 다음과 같이 자바 어플리케이션과 JDBC API 사이에서 동작한다. JPA를 사용한다고 해서 JDBC를 사용하지 않는 것은 아님을 유의해야 한다.

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/ede999cd-a51e-489d-b329-b740cd18b58b)

### 1) ORM (Object Relational Mapping)

> 객체 관계 맵핑

말 그대로 객체와 관계형 데이터베이스를 맵핑한다는 의미이다.

객체는 객체대로 설계하고, 관계형 데이터베이스는 관계형 데이터베이스대로 설계하면, ORM 프레임워크가 중간에서 이를 맵핑시켜주는 형태로 동작한다.

#### JPA 표준 인터페이스

JPA는 Hibernate, EclipesLink, DataNucleus 구현체를 구현한 **표준 인터페이스**이다.

![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/8e0119d3-f4dd-4c06-a98c-c97a36d48167)

<br>

### 2) 구조 및 동작

JPA는 어플리케이션과 JDBC 사이에서 동작하는 구조

쿼리를 만들고 JDBC API를 활용해서 맵핑 => **패러다임의 불일치 해결**

<br>

### 3) JPA를 사용해야하는 이유

#### 생산성

이미 만들어진 코드를 불러와서 쓰면 됨.

find, findByXXX, findByXXXAndXXX 등 이미 만들어진 메서드를 통해 쿼리를 보다 편리하게 활용할 수 있으며 코드 또한 간단함.

#### 유지보수

필드를 추가해도 따로 쿼리문을 수정할 필요가 없음.

#### 패러다임의 불일치 해결

객체와 관계형 데이터 베이스의 패러다임 불일치를 해결(상속, 연관관계 등)

엔티티의 신뢰성 문제 해결 => 동일한 트랜잭션에서 조회한 엔티티는 같음을 보장

#### 성능

3가지 최적화 기능 제공

- 1차 캐시와 동일성 보장
  - 같은 트랜잭션 안에서는 같은 엔티티를 반환
- 트랜잭션을 지원하는 쓰기 지연
  - 트랜잭션을 커밋할 때까지 INSERT SQL을 모아서 JDBC BATCH SQL 기능을 사용해 한번에 SQL 전송
- `지연 로딩(Lazy Fetch)`과 `즉시 로딩(Eager Fetch)`
  - 지연 로딩: 객체가 실제 사용될 때 로딩
    - 즉, 참조된 객체가 사용되기 전까지는 DB에서 데이터를 불러오지 않음
    - 예) memberDAO에서 JPA로 뭔가를 가져왔을 때는 Member에 대해서만 SELECT 해오다가, Member와 연관된 다른 테이블의 내용이 필요하서 참조 호출했을 때, 그 테이블에서 SELECT 해옴
  - 즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회

<br>

## 3. 요약

JPA를 배우더라도 RDB을 잘해야함

ORM은 객체와 RDB를 연결해주는 역할임

객체(프레임워크, 언어)는 다른 것으로 대체될 수 있지만, 데이터를 저장하는 RDB는 바뀌지 않을 가능성이 매우 매우 높음. 따라서 RDB에 대한 공부도 계속해서 해줘야함.