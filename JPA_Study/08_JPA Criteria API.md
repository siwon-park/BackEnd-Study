# 08_Criteria API

Criteria API는 Java Persistence API의 일부로, 타입 안전한(type-safe) 방식으로 데이터베이스 쿼리를 구성할 수 있게 해주는 프로그래밍 인터페이스이다.

<br>

## 1. Criteria API의 특징

JPQL(Java Persistence Query Language)의 대안으로 제공되며, 문자열 기반 쿼리 대신 자바 객체와 메서드를 사용해 쿼리를 작성할 수 있습니다.

### 1) JPQL

@Query 어노테이션을 사용하여 문자열로 쿼리를 짜는 방법이 JPQL이다.

![image](https://github.com/user-attachments/assets/487fda98-8748-4227-b028-d7516aeb3ce1)

- JPQL 방식은 문자열로 쿼리를 작성하다보니 컴파일 시점에 잘못된 부분을 검증할 수 없다.
  - 단순 오타 때문에 프로그램이 정상 동작하지 않을 수 있다는 점에서 매우 크리티컬하다.
- 또한 동적 쿼리 생성을 할 수 없다. 모든 쿼리에 대해서 직접 처리를 해줘야 한다.

<br>

### 2) JPA Repository

JPA Repository 방식은 매우 편리하긴 하지만 한계가 존재한다.

- 직접 쿼리를 짤 필요 없이 JPA가 제공하는 기본적인 메서드 명명 규칙에 따라 메서드를 생성하여 쿼리를 작성할 수 있다.
  - 다만, 쿼리가 조금이라도 복잡해지면 메서드 명칭이 매우 길어져서 가독성이 떨어진다.
  - 이런 방식을 통해 동적 쿼리를 작성할 수는 있지만 제한적일 수밖에 없다.
- 만약에 JPA Repository에서 제공하지 않는 쿼리가 필요하다면 어쨌거나 JPQL 방식으로 직접 쿼리를 작성해야 한다. (JPQL 방식과 동일)

<br>

### 3) Criteria API

- 타입 안전한(Type-Safe) 방식을 사용하여 컴파일 시점에 오류를 잡을 수 있다.
- 동적 쿼리를 생성하기에 유용하다.
- 객체 지향적인 방식으로 쿼리를 작성 가능하다.
- 단, JPA Repository와 JPQL에 비해 복잡하고 장황한 코드를 만들어낼 가능성이 있다.

<br>

## 2. Criteria API의 인터페이스 종류

![image](https://github.com/user-attachments/assets/f7b24d53-fc08-409b-b0d4-d46fea10f9a7)

jakarta.persistence.criteria 패키지 하위에 모든 인터페이스를 확인 가능하며, 대표적으로 CriteriaBuilder, Subquery, Join, Predicate, Root, Path 등이 있다.

### 1) 사용 예시

```java
// EntityManager 주입
@PersistenceContext
private EntityManager entityManager;

public List<User> findActiveUsersByName(String name) {
    // CriteriaBuilder 생성
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    
    // 쿼리 정의 (User 엔티티에 대한 쿼리)
    CriteriaQuery<User> query = cb.createQuery(User.class);
    
    // FROM 절 정의
    Root<User> user = query.from(User.class);
    
    // WHERE 조건 생성
    Predicate namePredicate = cb.like(user.get("name"), "%" + name + "%");
    Predicate activePredicate = cb.equal(user.get("active"), true);
    
    // 조건 결합
    Predicate finalPredicate = cb.and(namePredicate, activePredicate);
    
    // 쿼리에 WHERE 조건 적용
    query.where(finalPredicate);
    
    // 결과 정렬
    query.orderBy(cb.asc(user.get("name")));
    
    // 쿼리 실행 및 결과 반환
    return entityManager.createQuery(query).getResultList();
}
```

<br>

## 3. Specification

> criteria API를 더 쉽게 사용할 수 있도록 추상화한 인터페이스로 조건에 따른 동적 쿼리를 생성하는데 사용된다.

`org.springframework.data.jpa.domain.Specification` 인터페이스로, `Predicate`를 생성하는 팩토리 역할을 한다.

즉, 결국 Predicate가 where 조건절을 표현하는 객체이므로 where 절의 추상화이다.

각 조건(Predicate)가 모여서 조건들(Specification<객체>)을 형성하고  JPASpecificationExecutor를 구현한 repository에 조건들을 넘겨줄 수 있다.

### 1) Predicate

`javax.persistence.criteria.Predicate` 인터페이스로 Specification 객체가 만들어내는 조건 객체이다.

쿼리의 where 절에서 사용되는 조건 표현식을 나타낸다. 동등 표현식(=), 비교연산자(<, >), 논리연산자(AND, OR, NOT), LIKE와 같은 조건을 의미한다.

<br>

### 2) Specification

Predicate를 만들어내는 인터페이스 객체이며, 여러 Specification을 모아서 복잡한 쿼리 조건을 만들어낼 수 있다.

<br>

### 3) JpaSpecificationExecutor

구상한 spec (specification)을 적용시키기 위한 인터페이스.

JpaSpecificationExecutor를 구현한 repository여야 spec을 받을 수 있다.

<br>

### 4) Predicate와 Specification을 나눈 이유?

#### (1) 관심사 분리

Predicate는 `단일 조건을 표현하는` JPA 표준 인터페이스이고, Specification은 `Predicate를 생성하고 조합하는` Spring Data JPA의 추상화 계층이다.

#### (2) 재사용성 향상

Specification은 Predicate 생성 로직을 캡슐화하여 재사용 가능한 쿼리 조건을 만들 수 있게 한다.

복잡한 쿼리 조건을 여러 작은 조건으로 분리하고 조합해서 사용할 수 있다.

#### (3) 코드 가독성과 객체 지향적

Specification 객체에서 명명한 메서드로 쿼리 조건을 간단하게 표현할 수 있어서 가독성이 높고, 일반적인 Predicate만 사용하는 것에 비해 코드를 훨씬 객체 지향적으로 나타낼 수 있다.

```java
// Predicate만 사용
Predicate namePredicate = cb.like(user.get("name"), "%" + name + "%");
Predicate activePredicate = cb.equal(user.get("active"), true);
Predicate finalPredicate = cb.and(namePredicate, activePredicate); // Predicate 간 조합 필요

// Specification 사용
Specification<User> spec = (root, query, criteriaBuilder) -> null;
spec = spec.and(UserSpecification.likeName(name));
spec = spec.and(UserSpecification.equalActive(name));
userRepository.findAll(spec);
```

