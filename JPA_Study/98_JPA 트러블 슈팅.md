# 98_JPA 트러블 슈팅

## 1. TransientObjectException

> `org.hibernate.TransientObjectException: persistent instance references an unsaved transient instance`

### 1) 의미

**영속 상태(persistent)인 객체가, 아직 저장되지 않은 비영속 상태(transient)인 객체를 참조**하고 있다는 의미.

### 2) 원인

JPA는 데이터 무결성을 위해 부모 객체를 저장할 때, 참조되는 자식 객체도 반드시 영속 상태여야 함을 원칙으로 하는데, 이를 지키지 않아서 발생한 예외이다.

#### (1) 부모를 저장하기 전에 자식을 먼저 참조한 경우

부모를 new 생성자로 생성만 하고, save()하지 않은 상태(영속화하지 않은 상태)에서 다른 엔티티에 이 객체를 참조하여 저장을 하려고 할 때 발생하는 경우

#### (2) 연관 관계 편의 메서드 실수

양방향 영관관계에서 한쪽은 영속 상태인데, 다른 반대쪽 객체가 비영속 상태인 경우 (사실상 1번 케이스와 같음)

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