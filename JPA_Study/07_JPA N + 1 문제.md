# 07_JPA N + 1 문제

## 1. N + 1 문제 (N + 1 Problem)

### N + 1 문제란?

> 1번의 쿼리로 N개의 데이터를 가져왔는데, 조회된 데이터의 개수인 N개만큼 연관 관계 조회 쿼리가 N번 실행되어 총 N + 1개의 쿼리가 실행되는 문제를 말한다.

![image](https://user-images.githubusercontent.com/93081720/209461614-1c4f81a4-297f-4edb-ba20-b91ae0727352.png)

- N + 1 문제의 예시. 분명 쿼리문이 처음에 1개 실행됬는데, 이후에 N개가 더 실행됨을 확인할 수 있다.

<br>

### 언제 발생하는가?

JPA Repository 인터페이스 메서드를 통해 1:N 또는 N:1 연관 관계를 가진 Entity를 조회할 때 발생

<br>

### 왜 발생하는가?

JPA Repository에서 정의한 인터페이스 메서드를 실행하면 JPA는 메서드 이름을 분석한 다음 JPQL을 가지고  SQL구문을 생성해서 실행한다.

그런데 이때, JPQL은 기본적으로 글로벌 Fetch 전략을 무시하고 JPQL만을 이용해 SQL을 생성하기 때문에 N + 1 문제가 발생하게 된다.

즉, Fetch 전략을 어떻게 가져가더라도 N + 1 문제가 발생하게 된다.

#### Eager Fetch 전략(즉시 로딩)일 경우

1. `findAll()`이 들어간 인터페이스 메서드 실행
2. `SELECT * FROM <참조 테이블>`이라는 SQL 쿼리문이 생성되고 실행
3. DB에서 결과를 받아서 해당 테이블과 관련된 Entity 인스턴스를 생성
4. Eager Fetch (즉시 로딩) 전략이므로, 해당 Entity와 연관되어 있는 다른 모든 Entity도 추가 조회한다.
   - 영속성 컨텍스트에 관련 Entity가 있는지 확인한 다음, 없다면 3번에서 만들어진 Entity 인스턴스의 개수인 N개만큼 SQL 구문이 생성되어 실행된다.
   - `SELECT * FROM <연관 테이블> WHERE [참조 테이블_id = ?]`와 같은 SQL 구문이 생성/실행된다.
5. 총 N + 1개의 쿼리가 생성/실행되었으므로 N + 1 문제 발생



- 예시)

![image](https://user-images.githubusercontent.com/93081720/209462088-a094def7-13a8-45d2-b8dd-ab3169bea8a1.png)

![image](https://user-images.githubusercontent.com/93081720/209462108-cd60fc64-b4d3-4669-a80e-070c1439d899.png)

Eager Fetch 전략은 findAll() 메서드를 호출하면 N + 1 문제가 즉시 발생함을 알 수 있다.

<br>

#### Lazy Fetch 전략(지연 로딩)일 경우

1. `findAll()`이 들어간 인터페이스 메서드 실행
2. `SELECT * FROM <테이블>`이라는 SQL 쿼리문이 생성되고 실행
3. DB에서 결과를 받아서 해당 테이블과 관련된 Entity 인스턴스를 생성
4. Lazy Fetch (지연 로딩) 전략이므로, 해당 Entity와 연관되어 있는 Entity를 지금 가져오지는 않는다.
5. 그러나, 해당 Entity에서 연관 관계에 있는 Entity의 인스턴스를 사용하려고 할 때 추가적인 조회가 발생함
   - 3번에서 만들어진 Entity 인스턴스의 개수인 N개만큼 SQL 구문이 생성되어 실행된다.
   - `SELECT * FROM <연관 테이블> WHERE [참조 테이블_id = ?]`와 같은 SQL 구문이 생성/실행된다.
6. 결국에는 총 N + 1개의 쿼리가 생성/실행되었으므로 N + 1 문제 발생
   - Eager Fetch와 발생 시점만 다를 뿐, 결국에는 N + 1 문제가 발생하는 것은 마찬가지



- 예시)

![image](https://user-images.githubusercontent.com/93081720/209462120-1168aa9e-1c74-4323-b9c8-96061e2ee6ca.png)

![image](https://user-images.githubusercontent.com/93081720/209462135-6bed2a64-0121-4cbe-87dd-d97b57287943.png)

Lazy Fetch 전략은 findAll() 메서드 호출 당시에는 1개의 쿼리문만 실행되지만, 이후 해당 엔티티를 통해 연관 관계에 있는 객체에 접근하면 N개의 쿼리문이 추가 실행됨을 확인할 수 있다.

<br>

### 해결 방법

#### 1. Fetch Join(패치 조인)

JPQL을 사용해서 DB에서 데이터를 가져올 때, 처음부터 연관 관계에 있는 데이터까지 전부 가져오게 하는 방법이다. (SQL의 JOIN 개념)

별도의 메서드를 만들고 `@Query` 어노테이션을 사용해서 `join fetch 엔티티.연관관계_엔티티` JPQL 구문을 직접 작성해줘야 한다.

- 예시)

![image](https://user-images.githubusercontent.com/93081720/209462441-13d0ae57-279b-40c2-a103-eee0506c7f54.png)

![image](https://user-images.githubusercontent.com/93081720/209462458-eaea4f1f-b748-4924-8da5-69b38f9bffa0.png)

해당 메서드를 실행한 결과 쿼리문이 1번만 실행되었으며, SQL inner join 구문이 실행됨을 확인할 수 있다.

<br>

#### 2. @EntityGraph 어노테이션

Fetch Join과 마찬가지로 별도의 메서드를 만들고 `@EntityGraph`어노테이션을 작성하고, attributePaths 옵션에 쿼리 수행 시 바로 가져올 필드명을 지정하여 Eager Fetch로 가져오게 한다.

`@Query` 어노테이션을 사용해서 JPQL 구문도 직접 작성해줘야 한다.

```java
@EntityGraph(attributePaths = "users")
@Query("select t from Team t")
List<Team> findAllEntityGraph();
```

![image](https://user-images.githubusercontent.com/93081720/209462579-25758ccb-b174-4582-90ee-3ae000367756.png)

실행 결과를 보면 SQL의 Outer Join이 발생했음을 알 수 있다.

<br>

#### Fetch Join과 EntityGraph 사용 시 주의점

두 방법 모두 Join 사용한다는 것에 있어 비슷하지만 차이가 있다.

- 두 방법 다 번거롭게 쿼리문을 작성해줘야 한다.
- 성능면에서 봤을 때, Inner Join이 Outer Join보다 성능 최적화에 유리하므로, Fetch Join 방법이 낫다. 또한 어노테이션 및 추가 옵션을 작성해줘야 하므로 Fetch Join이 더 사용하기 편리하다.
- Fetch Join의 경우
  - 쿼리 한 번에 모든 데이터를 가져오기 때문에 JPA가 제공하는 Paging API 사용이 불가능하다(Pageable 사용 불가)
  - 1:N 연관 관계가 두 개 이상인 경우 사용이 불가능하다
  - Fetch Join 대상에게 별칭(alias) 부여가 불가능하다
- @EntityGraph의 경우
  - 어노테이션에 추가적인 옵션을 설정해줘야 한다.

그러나, 가장 큰 주의점은 **두 방법 공통적으로 카타시안 곱이 발생**하여 성능이 떨어질 수 있다.

따라서 중복을 제거하기 위해

- JPQL 구문을 작성할 때, `distinc`를 사용한다.
- OneToMany의 필드 타입을 List가 아니라 `Set`형으로 선언하여 중복을 제거한다.

```java
@EntityGraph(attributePaths = "users")
@Query("select DISTINCT t from Team t")
List<Team> findAllEntityGraph();
```

```java
@OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
private Set<User> users = new HashSet<>(); // 만약 순서 보장이 필요하다면 LinkedHashSet으로 선언하면 된다.
```

<br>

#### 3. @BatchSize 어노테이션

하이버네이트가 제공하는 `org.hibernate.annotations.BatchSize` 의 `@BatchSize` 어노테이션을 사용하여 연관 관계에 있는 Entity를 조회할 때, 지정된 size만큼 SQL의 IN절을 사용하여 조회한다.

이 방법은 N + 1 문제를 해결하는 방법은 아니고, N + 1번 발생할 것을 보다 더 줄여서 N + 1 문제를 최적화하는 것이다.

단점은 사실상 연관 관계 데이터의 최적화를 위한 데이터 사이즈가 얼마인지 모르기 때문에 적절한 size를 설정하기 쉽지 않다는 것이다.

- 예시)

```java
@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String name;

    @BatchSize(size=5)
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private Set<User> users = new LinkedHashSet<>();
}
```

`size`는 IN절에 올 수 있는 최대 인자 개수를 의미한다. 즉, 만약 어떤 팀에 속한 유저가 10명일 경우 IN절에 한 번에 5개까지 인자를 넣을 수 있으므로, 총 2번의 IN절이 실행된다.

위 예시는 `즉시 로딩`이므로 해당 Entity 조회 시, 연관 관계에 있는 Entity를 동시에 조회하고

만약에 `지연 로딩`이라면 지연 로딩된 Entity의 최초 사용 시점에 size만큼의 건수를 미리 로딩해두고, size + 1 시점에 다음 SQL 구문이 추가 실행된다.

즉, 위의 예시에서 만약 지연 로딩이었다면 최초 사용 시점에 5개를 미리 로딩해두고, 이후에 6번째 Entity 사용 시점에 SQL이 추가로 실행되는 것이다.

- 다음과 같이 `application.yml` 또는 `application.properties`에 옵션을 지정하여 프로젝트 전체에 기본적으로 `@BatchSize`옵션을 적용할 수도 있다.

```yml
spring:
  jpa:
    properties:
      hibernate:
        default_batch_fetch_size: 100
```

```yml
spring.jpa.properties.hibernate.default_batch_fetch_size=100
```

<br>

### 결론

JPA는 편리하긴하지만 만능은 아니다.

상황에 맞게 적절한 방법을 사용하여 최적화된 쿼리를 유도하는 것이 중요하다.

QueryDSL같은 QueryBuilder를 이용해서 쿼리 최적화 및 동적 쿼리를 짜는 것을 추천한다.