# 09_GenerationType

> JPA의 엔티티 ID 생성 전략

| 전략     | 특징/설명                    | 주요 DB   | 비고                           |
| -------- | ---------------------------- | --------- | ------------------------------ |
| AUTO     | JPA가 자동으로 선택          | 모든 DB   | DB가 바뀌면 전략도 바뀜        |
| IDENTITY | DB의 auto increment 사용     | MySQL 등  | 배치 insert 불가능             |
| SEQUENCE | DB의 sequence 객체 사용      | Oracle 등 | 배치 insert 가능               |
| TABLE    | 별도의 테이블에서 pk 값 관리 | 모든 DB   | 느리고 거의 안 씀              |
| UUID     | 전역 유일한 16바이트 문자열  | 모든 DB   | 전역 유일하나 인덱싱 성능 저하 |

<br>

## 1. AUTO

> GenerationType.AUTO

Hibernate 등과 같은 JPA 구현체가 현재 연결된 DB의 특성에 따라 가장 적합한 전략을 택한다. 선택 기준은 DB 메타데이터 정보를 읽어서 판단한다.

### 1) 동작

Hibernate 기준

- JDBC의 DatabaseMetaData를 읽어 DB의 타입을 파악하고, Dialect를 통해 지원 여부를 감지하여 전략을 선택한다.
- 각 DB에 따라 다음과 같은 전략을 택한다.
  - H2, PostgreSQL, Oracle, HSQL: `SEQUENCE`
  - MySQL, MariaDB, SQL Server, MSSQL: `IDENTITY`
  - DB2: IDENTITY 혹은 SEQUENCE
  - 지원하지 않는 경우: TABLE

### 2) 특징(주의점)

DB를 변경하거나 Dialect를 바꿀 경우 AUTO 전략의 동작이 달라질 수 있기 때문에 운영 환경에서 해당 전략을 사용하는 것은 좋지 않다. 당연하게도 PK라는 매우 중요한 데이터의 채번 전략이 변경되는 것은 매우 크리티컬하기 때문이다.

<br>

## 2. IDENTITY

> GenerationType.IDENTITY

DB의 `auto increment` 속성이 지정된 칼럼(MySQL, MariaDB)나 `IDENTITY 칼럼`(MSSQL, H2, DB2 등)의 기능을 활용하는 전략.

### 1) 동작

- insert 시점에 DB가 PK값을 자동으로 생성
- 내부적으로 insert 쿼리를 날릴 때, id에 대해서는 null을 입력
  - `insert into ... values (null, ...)`
  - null을 전달했지만 DB가 자동으로 채번 규칙에 따라 값을 채움
- JPA는 영속성 컨텍스트에 엔티티가 저장될 때 PK 값을 알게 됨
  - insert 이후에 생성된 pk 값을 select 해서 가져옴

### 2) 특징(주의점)

- 배치 insert 불가
  - 여러 엔티티를 한 번에 insert 하면 각각 pk를 전부 받아와야 하므로 효율이 떨어짐
- 엔티티의 persist() 호출 시 즉시 insert SQL이 나감

<br>

## 3. SEQUENCE

> GenerationType.SEQUENCE

DB의 시퀀스 객체를 사용하여 PK를 생성하는 전략.

시퀀스 객체란, DB 내에 존재하는 독립된 객체로서 단순히 숫자를 자동으로 증가시키는 역할만 수행한다.

다음과 같은 SQL로 생성할 수 있다.

```sql
CREATE SEQUENCE my_seq START WITH 1 INCREMENT BY 1;
```

### 1) 동작

- JPA가 insert 전에 DB의 시퀀스 객체로부터 미리 PK 값을 할당받아서 해당 PK 값으로 insert를 진행한다.
- Java 클래스에서는 `@SequenceGenerator` 어노테이션과 함께 작성하여 사용한다.
  - name은 자바 코드 내부에서 시퀀스 제너레이터를 구분하기 위한 논리적인 이름이다.
  - sequenceName이 실제 DB 상에 정의한 시퀀스 객체의 이름을 의미한다.

```java
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq_gen")
    @SequenceGenerator(name = "my_seq_gen", sequenceName = "my_seq", allocationSize = 1)
    private Long id;
    // ...
}
```

### 2) 특징(주의점)

- 배치 insert에 유리
  - insert 전에 pk 값을 미리 확보할 수 있기 때문에 배치 insert 작업이 가능함
  - IDENTITY 전략과 달리 미리 pk값을 확보할 수 있어 flush() 시점까지 SQL 수행을 미룰 수 있음
- allocationSize 값을 통해서 한 번에 미리 시퀀스 값을 가져올 수도 있다.
  - allocationSize: 미리 여러 개의 PK 값을 확보해서 가져올 수 있는 기능 (사이즈만큼 가져옴)
  - DB round-trip 횟수가 감소하여 성능이 향상된다는 장점이 있지만,
  - 다중 WAS 환경에서 allocationSize와INCREMENT BY가 서로 다를 경우 중복 혹은 낭비가 발생할 가능성이 존재한다.
    - 실제 DB 시퀀스의 INCREMENT BY가 1인데 allocationSize가 50이면, JPA는 50개의 구간을 한 번에 캐싱한다고 생각하지만, DB는 1씩 증가시키기 때문에 여러 WAS가 동일한 시퀀스 값을 가져올 위험이 생긴다.
    - 반대로 allocationSize가 1인데 INCREMENT BY가 50이라면, 49개의 값을 건너뛰게 되므로 낭비가 발생한다.
  - 보통 일반적으로는 allocationSize와 INCREMENT BY를 같은 숫자로 맞춘다.

<br>

## 4. TABLE

> GenerationType.TABLE

별도의 테이블에서 PK 값을 생성하고 관리하는 전략.

```sql
CREATE TABLE id_gen (
  gen_name VARCHAR(50) NOT NULL,
  gen_val BIGINT,
  PRIMARY KEY (gen_name)
);
```

다음과 같이 id_gen이라는 테이블을 만들고 해당 테이블에서 gen_name에 해당하는 엔티티에 대해 gen_val을 pk 값으로 사용한다.

### 1) 동작

```sql
-- PK 값 획득
SELECT gen_val FROM id_gen WHERE gen_name = 'my_entity' FOR UPDATE;
-- 값 증가
UPDATE id_gen SET gen_val = gen_val + 1 WHERE gen_name = 'my_entity';
-- insert 시 PK 값 사용
```

- PK 값을 관리하기 위한 별도의 테이블을 만들고
- JPA가 insert 전에 해당 테이블에서 pk 값에 해당하는 값을 select
- update 쿼리를 통해 해당 값을 1로 증가시키고
- update 전 select 해온 값을 pk 값으로 사용함

### 2) 특징(주의점)

- 성능이 느림
  - 테이블에서 select와 update를 해야하기 때문에 동시성 이슈, 락 이슈가 발생하며 느리다.
  - 실무에서는 거의 사용하지 않는 전략이다. (성능적으로 나은 부분이 전혀 없기 때문)

<br>

## 5. UUID

> GenerationType.UUID

JPA 3.2 (2024년 2월 릴리즈) 버전부터 표준으로 추가되어 사용 가능한 ID 생성 전략.

```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID id;
```

RFC 4122 표준의 무작위 16바이트 (128비트) 식별자를 생성하여 id 값으로 사용하는 전략.

### 1) 동작

- insert 전에 java 코드 혹은 DB 함수에서 UUID 값을 생성하여 PK 값으로 사용한다.
  - `UUID.randomUUID()`

### 2) 특징(주의점)

- 전역적으로 유일하여 분산 시스템에서도 PK 충돌 가능성이 매우 매우 낮다.
- PK 값이 무의미하기 때문에 외부 노출이 되어도 상관이 없다.
- 그러나 값이 길어서 인덱스 크기 증가 등 성능적인 이슈가 발생한다.

