# 98_JPA 트러블 슈팅

## 1. TransientObjectException

> `org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance`

### 1) 의미

**영속 상태(persistent)인 객체가, 아직 저장되지 않은 비영속 상태(transient)인 객체를 참조**하고 있다는 의미.

### 2) 원인

JPA는 데이터 무결성을 위해, 엔티티를 저장(영속화)할 때 그 엔티티가 참조하고 있는 연관 엔티티도 반드시 영속 상태여야 함을 원칙으로 하는데, 이를 지키지 않아서 발생하는 예외이다.

#### (1) 특정 객체를 저장하기 전에 다른 객체에서 먼저 참조한 경우

부모를 new 생성자로 생성만 하고, save()하지 않은 상태(영속화하지 않은 상태)에서 다른 엔티티(자식 엔티티)에서 이 객체를 참조하여 저장을 하려고 할 때 발생하는 경우

#### (2) 연관 관계 편의 메서드 실수

양방향 연관관계에서 한쪽은 영속 상태인데, 다른 반대쪽 객체가 비영속 상태인 경우 (사실상 1번 케이스와 같음)

#### (3) cascade 옵션의 부재

 부모 엔티티를 저장할 때, 자식 엔티티도 함께 저장되기를 기대했지만, 부모 엔티티 설정에 CascadeType.PERSIST 혹은 ALL이 빠져있을 경우

### 3) 언제 발생?

예를 들어 findBy 메서드로 조회를 수행할 때, JPA는 데이터 일관성을 위해 이전까지의 변경사항을 먼저 저장하려고 DB에 플러시를 시도한다. 이를 오토 플러시(auto flush)라고 하는데, 이 때 저장되지 않은 객체가 발견될 경우 해당 예외가 발생한다.

#### (1) 예시

```java
// 1. 영속성 컨텍스트에서 company를 조회
Company company = companyRepository.findByName("새회사"); // 이 객체는 영속(persistent)

// 2. Worker는 비영속 객체
Worker worker = Worker.builder().name("새직원").build(); // 비영속(transient) 상태

// 3. 둘을 조합해서 새로운 엔티티 생성
CompanyWorker companyWorker = CompanyWorker.builder()
        .company(company)
        .worker(worker) // 비영속 객체 참조
        .build();

// 4. companyWorker 저장 (cascade 옵션 없을 때)
companyWorkerRepository.save(companyWorker); // TransientObjectException 발생!!
```

### 4) 해결책

반드시 **선(先) 저장 후(後) 참조**의 원칙을 지킨다. (반드시 영속 상태인 객체를 참조할 수 있도록 한다.)

혹은 Cascade 설정을 적극 활용한다.

<br>

## 2. LazyInitializationException

>`org.hibernate.LazyInitializationException: ... 중략 ...`

※ LazyInitializationException의 경우 예외 메세지가 가변적임을 유의. 실패 대상, 실패 사유 또는 버전에 따라 표현이 조금씩 달라질 수 있다.

### 1) could not initialize proxy - no Session

영속성 컨텍스트 (세션)이 닫힌 뒤에 아직 초기화되지 않은 지연 로딩 필드에 접근하려고 할 때 세션이 이미 만료되어 접근이 불가능하여 발생하는 예외이다.

#### (1) 원인

`@OneToMany`의 경우 기본 fetch 전략은 Lazy이다.

특정 엔티티를 조회하는 시점에는 OneToMany 관계에 있는 필드를 가져오지 않고, 나중에 필요할 경우 이를 가져올 수 있도록 "프록시(가짜 껍데기)"만 채워둔다.

그리고 실제로 해당 필드에 접근하려는 순간, Hibernate가 DB에 쿼리를 날려서 값을 채우는 방식으로 동작한다.

그런데 이 값을 나중에 채우는 행위가 가능하려면 Hibernate Session이 살아있어야 한다.

- Hibernate Session = DB와 연결된 "대화창"
  - 이 세션이 열려 있는 동안만 추가 쿼리를 날릴 수 있다.

Spring Data JPA는 기본적으로 "메서드 하나당 세션 하나" 규칙을 쓴다.

이 때 `@Transactional`이 없는 메서드에서 데이터를 레포지토리의 메서드를 통해 호출하면, Spring은 그 호출 하나만을 위한 임시 세션을 만들었다가 호출이 끝나자마자 닫아버린다.

#### (2) 예시

```java
// @Transactional 없음
public List<OrgSyncExtractDTO> fetchCodeList() {   

    OrgSyncApiConfig codeApiConfig = orgSyncApiConfigRepository.findByTarget(CODE).orElse(null);
    // 1. findByTarget() 호출 → 임시 세션 열림
    // 2. DB에서 OrgSyncApiConfig 기본 정보만 SELECT (headers 필드는 @OneToMany 관계, LAZY fetch라서 안 가져옴)
    // 3. findByTarget()이 끝나는 순간 → 임시 세션 즉시 닫힘

    HttpHeaders headers = getHeaders(codeApiConfig);
    // 4. codeApiConfig.getHeaders() 호출
    // => "아직 실제 값 없네, DB에서 마저 가져와야겠다"
    // => 근데 가져올 통로였던 세션은 이미 3.에서 닫힘
    // => LazyInitializationException 발생
}
```

#### (3) 해결책

- @Transactional 어노테이션 추가

  - 읽기만 할 경우 (readOnly=true)
  - 메서드 내에서 영속성 엔티티 호출 이후 그 엔티티의 OneToMany 관계의 (fetch 전략이 Eager가 아닌) 필드를 참조할 일이 있을 경우 @Transactional 어노테이션을 추가하여 트랜잭션을 유지한다.

- Fetch Join

  - 레포지토리의 메서드를 작성할 때 Fetch Join을 통해서 필요한 데이터를 미리 로딩한다.

  - ```java
    // repository
    @Query("SELECT t FROM Team t JOIN FETCH t.members WHERE t.id = :id")
    Optional<Team> findByIdWithMembers(@Param("id") Long id);
    ```

  - fetch join을 사용할 경우 쿼리 시점에 연관 데이터까지 함께 가져오기 때문에 해당 필드의 프록시가 생기지 않는다.

- @EntityGraph

  - Fetch Join과 유사한 효과를 어노테이션으로 처리

  - ```java
    @EntityGraph(attributePaths = {"members"})
    Optional<Team> findById(Long id);
    ```

### 99) 핵심 원칙

"지연 로딩 필드는 영속성 컨텍스트가 살이있는 동안, 즉, 트랜잭션 범위 안에서만 접근해야 한다."

이게 어려울 경우에는 애초에 필요한 필드를 미리 가져오도록 하는 것이 올바른 접근 방법이다.

