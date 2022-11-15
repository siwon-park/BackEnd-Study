![image](https://user-images.githubusercontent.com/93081720/201936889-41b94b97-8921-428b-81e3-e187e87212f2.png)

# 16_Query DSL

## 01_JPQL과 Spring Data JPA

### JPQL의 단점

![image](https://user-images.githubusercontent.com/93081720/201936372-100db7ba-0b99-4014-9586-10f5b945e485.png)

- 가독성이 별로 좋지 않다 => 문자열로 작성하기 때문에 오타 등으로 인해 잘못 썼을 경우 버그를 찾기 어렵다.
- 문법이 일반 SQL과 조금 달라서 복잡한 쿼리를 작성할 때마다 레퍼런스를 참고해야한다.
- 조건이 복잡한 동적쿼리를 작성할 때마다 함수가 계속해서 늘어난다. => 필드가 늘어날 때마다 계속해서 해당 함수도 추가해줘야함
  - `findByOOO`, `findByOOOAndXXX`, `findByOOOAndXXXAnd△△△`, ... (계속)

### Spring Data JPA의 단점

![image](https://user-images.githubusercontent.com/93081720/201939583-b3c8bc33-9420-42b1-b33a-2341dfc978fc.png)

- 프로덕션 코드 변경에 취약하다 => 도메인 종속적이기 때문에 도메인 코드 변경에 취약하다.

<br>

## 02_Query Dsl

### Query DSL의 등장

JPQL의 단점을 극복하기 위해 Query DSL이란 것이 등장함

Spring Data JPA와 Query Dsl을 함께 사용해서 서로를 **보완**해야하는 개념

![image](https://user-images.githubusercontent.com/93081720/201939935-d6e100be-ca0f-4f9c-a651-39590c194290.png)

<br>

### Query DSL의 개념

>  해당 언어의 코드로 쿼리를 작성하게 해 줄 수 있는 프레임워크

build.gradle과 더불어 config 파일을 작성하여 사용을 위한 설정을 사전에 해줘야함

#### QueryDsl.config

QueryDSL 적용을 하기위한 설정 config파일

![image](https://user-images.githubusercontent.com/93081720/201942338-9ca5643a-8189-437b-ab4c-28fe28fa735f.png)

<br>

### Query DSL의 적용 - 1

| 기존                                                         | Query Dsl 적용 - 1                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/201515598-6dfb6eab-c0a1-4075-ba7a-998f5f14cb5b.png) | ![image](https://user-images.githubusercontent.com/93081720/201516031-3c36ecd1-0bde-4fc0-b7b3-f21261077590.png) |

#### 첫 번째 Query DSL 적용 방법의 장점

첫 번째 방법으로 적용한 Query DSL은 서비스 단에서 JPARepository와 CustomRepository를 상속받고 구현한 Repository 하나만 호출하여 사용하면 된다.

#### 첫 번째 Query DSL 적용 방법의 단점

커스텀 repository 인터페이스와 이를 구현한 클래스를 항상 같이 만들어줘야 한다.

<br>

### Query DSL의 적용 - 2

QueryDsl 클래스를 생성하여 사용

![image](https://user-images.githubusercontent.com/93081720/201942909-5d85c75d-3e99-4108-ba3d-d9fadcbf0ac9.png)

#### 두 번째 Query DSL 적용 방법의 장점

하나의 클래스만 만들어서 사용하면 되므로 훨씬 간단하다.

#### 두 번째 Query DSL 적용 방법의 단점

필요에 따라서 여러 repository를 불러와야할 수도 있다.

<br>

### 어떤 방법이 더 좋은가?

성능적으로는 차이가 없지만, 멀티 모듈을 사용하는 경우 모듈별로만 해당 repository를 쓰는 경우가 많기 때문에 사용이 더 간단한 두 번째 방법을 추천

<br>

### JPQL(@Query)을 쓰지 않은 구문도 Query DSL로 옮겨야할까?

=> **동적 쿼리의 간편함** 덕분에 변환해주는 것을 권장!

- 조건이 복잡한 동적쿼리를 작성할 때마다 함수가 계속해서 늘어난다는 단점을 해결 가능함
  - `findByOOO`, `findByOOOAndXXX`, `findByOOOAndXXXAnd△△△`, ... (계속)
- 예) A필드는 필수적으로 들어오지만, B, C, D, E 필드는 선택적으로 들어온다고 했을 때
  - `findByA` (필수)
  - `findByAAndB`, `findByAAndBAndC`, ... , `findByAAndBAndCAndDAndE`까지 함수를 만들어야할 수도 있음
  - 2^4개의 함수가 생성됨 => 만약 필드가 더 늘어난다면? 2^n개