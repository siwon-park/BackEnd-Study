![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 05_Entity, DAO, DTO, VO

## Entity(엔티티)

> **DB 테이블에 존재하는 칼럼(필드)들을 속성(property)로 가지는 클래스**

DB와 1대1로 대응되며, 따라서 DB테이블이 칼럼으로 가지지 않는 것을 필드로 가져서는 안 된다. 또한 다른 클래스를 상속 받거나 인터페이스의 구현체여서는 안 된다.

원칙적으로 Controller에서 Entity를 매개변수(parmeter)로 받거나 return값으로 반환하면 안 된다. => 객체의 영속성을 깨뜨리는 행위이기 때문임
그래서 Entity는 Service Layer에서 주로 사용한다.(DTO와는 Layer에 따른 역할 분리)

JPA를 사용할 때는 `@Entity` 애너테이션을 붙여서 Entity임을 명시적으로 정의할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/173801732-862e2452-da82-4996-88b0-69375db8a192.png)

### Getter, Setter

Entity는 외부에서 최대한 Entity의 Getter를 사용하지 않도록 Domain 로직만 구현하고, Presentation 로직은 구현하지 않는다

Entity를 만들 때, 최대한 Setter를 만드는 것을 지양하는데, 그 이유는 Setter를 사용할 경우 Setter를 호출하여 Entity의 인스턴스 값들이 언제든지 바뀔 수 있으므로 로직에 따른 명확한 변화 시점을 알 수 없게된다.(무조건 쓰지 말라는 것은 아니고, public setter를 사용하게 될 경우 주의하라는 의미)

만약 Entity 필드의 내용을 변경하고자 할 때는 setter 대신에 보다 직관적인 메서드를 선언하고 해당 메서드를 사용해서 변경할 수 있도록 한다

- 예) setter 대신 changeInfo라는 메서드를 선언해서 해결

![image](https://user-images.githubusercontent.com/93081720/188266770-50115fdd-1c4f-4103-ae7b-785ef500de1c.png)

### Builder 패턴

그래서 엔티티 클래스에 생성자를 만들고 Build패턴을 통해 필요한 값을 집어 넣는 방식으로 Entity 인스턴스를 생성하는 것이 좋다

- 예) 빌더 패턴을 통핸 인스턴스 생성

![image](https://user-images.githubusercontent.com/93081720/173803180-fc4bed34-64fc-4d63-aec0-c40c7d51ab13.png)

#### ※ 빌더 패턴의 장점

- 필요한 데이터만 설정할 수 있음 => 생성자에 지정된 모든 매개변수를 넣지 않고도 생성자를 생성할 수 있음
- 유연성을 확보할 수 있음 => 매개변수에 따른 생성자를 따로 생성할 필요없이, 전체 속성값을 가진 생성자를 정의한 후 사용 가능
- 가독성을 높일 수 있음 => 어떤 항목으로 어떤 데이터가 들어가는 지 명시적으로 알 수 있음
- 변경 가능성을 최소화할 수 있음 => final 키워드를 붙이지 않고 Setter를 정의하지 않고 Build패턴을 적용

대부분의 경우에는 빌더 패턴을 적용하는 것이 좋지만 다음과 같은 사항에서는 굳이 빌더를 구현할 필요가 없다

- 객체 생성을 라이브러리로 위임하는 경우
- 변수의 개수가 2개 이하이며, 변경 가능성이 없는 경우

=> 변수의 개수와 변경 가능성 등을 중점으로 보고 빌더 패턴을 적용할지 판단하면 됨

<br>

## DAO(Data Access Object)

> **실제 DB에 접근하는 객체**

DB에 데이터를 CRUD하는 `Repository`객체들이 DAO라고 말할 수 있음(실제 개념적으로는 차이가 있음)

Persistence Layer(Repository) => DB에 data를 CRUD하는 계층

JPA 등장 이전에는 보통 생성, 검색, 수정, 삭제와 같은 기본적인 CRUD 오퍼레이션을 각 Entity마다 작성해주었다. => DAO 클래스를 각 Entity마다 작성하였음

왜? 데이터 테이블에 접근해서 데이터에 대한 오퍼레이션을 실행 당하는 대상이 자바 내의 데이터 테이블 객체인 Entity이기 때문

<br>

## DTO(Data Transfer Object)

> **계층 간 데이터 교환 역할을 하는 객체**

JSON 형태의 데이터를 Serialize(직렬화)해서 사용하는 것이 DTO

DB에서 꺼낸 데이터를 저장하는 Entity를 가지고 만드는 일종의 Wrapper 객체라고 볼 수 있음.  Controller와 같은 클라이언트 단과 직접 마주하는 계층에서 Entity를 통해서 직접 데이터를 전달하는 것이 아니라, 순수하게 계층 간의 데이터 교환이 이루어질 수 있도록 하는 객체로서 특별한 로직을 가지지 않는 DTO를 사용한다. 따라서 DTO에서 데이터를 임의로 조작할 필요가 없기 때문에 DTO에는 Getter/Setter 외에는 다른 로직의 메서드는 만들지 않는다.

### Entity와 DTO를 분리하는 이유

- **관심사의 분리(Seperation of Concerns, SoC)** :서로 다른 관심사들을 분리하여 변경 가능성을 최소화하고, 유연하며 확장가능한 클린 아키텍처를 구축하도록 도와준다

=> DTO의 관심사는 데이터를 담아서 다른 계층 또는 컴포넌트로 데이터를 전달하는 것임.

반면에 Entity 또는 도메인 객체는 핵심 비즈니스 로직을 담고 있는 비즈니스 도메인의 일부이기 때문에 로직이 새롭게 추가될 수 있으며, 오로지 DB와 1대1 맵핑에 그 핵심이 있음

- Entity가 변하면 Repository 클래스의 Entity Manager의 flush가 호출될 때 DB값이 반영되는데, 이는 다른 로직들에게도 영향을 미친다. 따라서 View와 통신하면서 필연적으로 데이터의 변경이 많은 DTO클래스를 분리해주는 것이다.

- Entity와 DTO가 분리되어 있지 않다면 Entity안에 Presentation을 위한 필드나 로직이 추가되게 되어 객체 설계를 망가뜨리게 된다. 때문에 이런 경우 DTO에 Presentation로직 정도를 추가해서 사용하고, Entity에는 그대로 두고 도메인 모델링을 깨뜨리지 않는다.

※ 단, 오직 READ만 하는 경우에는 DB테이블과 View가 같기 때문에 매번 DTO를 만드는 로직이 낭비일 수도 있기 때문에 DTO를 만들지 않고 Entity를 사용하는 경우도 있다.

<br>

## VO(Value Object)

> **특정 비즈니스 값을 담는 객체, 값 그 자체를 표현하는 객체**

DTO와 유사하나 로직을 포함할 수 있으며,  ReadOnly 속성 즉, 객체의 불변성(Immutable; 객체의 정보가 변하지 않음)을 유지하기 위해서 생성자를 제외하고는 **setter의 성격을 띄는 메서드를 선언하면 안 된다**.

값 그 자체를 표현하는 객체이므로, 일반적으로 값을 비교하기 위한 메서드인 equals, hashCode를 오버라이드한다.
- eqauls : 값 비교
- hashCode : 해시 비교

VO는 모든 속성 값이 같다면 같은 객체임을 보장한다.(비교 메서드의 결과와 비교했을 때 같으면 동일 객체)

- 예) VO 예시 => setter메서드가 없고, 같은 객체임을 비교하기 위한 로직을 담은 equals 메서드가 있다

![image](https://user-images.githubusercontent.com/93081720/188267086-5990799e-ed80-43c6-9c41-208b1fe712d5.png)

### DTO와 VO의 차이

![image](https://user-images.githubusercontent.com/93081720/188267208-285d5ca5-cc1d-478e-9436-51c7f6506b17.png)

- DTO: 데이터 전달용
- VO: 값 표현용

※ TMI) DTO와 VO를 사람들이 혼동하게 된 이유: Sun/java책 에서 DTO를 VO로 잘못 표기하는 바람에 사람들이 헷갈리게 됨(후에 책에서는 DTO로 정정함)

<br>

## 정리

![image](https://user-images.githubusercontent.com/93081720/188267703-eb4e459c-3367-46ef-aabc-2f5beb8029ae.png)