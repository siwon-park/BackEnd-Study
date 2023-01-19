# 03_ERD 설계

## 1. 설계 팁

### 관계

- 메인 데이터가 무엇인지 파악할 것
- 메인 데이터 간 관계
  - 주어 + 목적어 + 서술어 형태로 생각
  - 예) `'유저는' + '다른 유저를' + '팔로우할 수 있다'`
- 자식 입장(귀속 당하는 입장, 외래키(FK)로 참조되는 입장)에서 해당 관계를 고려해서 ERD를 설계함
- `'된다'`와 `'할 수 있다'`로 생각해서 구분하면 관계를 보다 쉽게 정의할 수 있음

<br>

### 중계 테이블

- 중계 테이블의 역할은 `라우터/주소록의 개념`이 기본이며, 원하는 데이터 필드를 삽입하여 사용 가능하다
- 多:多 관계(M2M 관계)의 해소 => 중계 테이블을 만드는 것

<br>

## 2. 범례

### Optional vs. Mandatory

| 아이콘                                                       | 설명             | 예시                                             |
| ------------------------------------------------------------ | ---------------- | ------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/213460552-a3099c6d-3f6f-48cf-90c0-bf05d59a08bb.png) | Optional한 관계  | 예) 사용자가 댓글을 달 수도 있고 안 달 수도 있다 |
| ![image](https://user-images.githubusercontent.com/93081720/213460492-974bd44d-e4f4-4cde-89c4-5e4ff4b7fa4d.png) | Mandatory한 관계 | 예) 1명의 선생님은 반드시 한 반을 맡는다         |

<br>

### 관계도

| 아이콘                                                       | 설명           | 예시 |
| ------------------------------------------------------------ | -------------- | ---- |
| ![image](https://user-images.githubusercontent.com/93081720/213461261-3da0c95b-2dad-4169-b2e2-660eb0cf8af3.png) | One            |      |
| ![image](https://user-images.githubusercontent.com/93081720/213461357-fc185834-f90b-4aa3-86f6-b1b9061a3c15.png) | Many           |      |
| ![image](https://user-images.githubusercontent.com/93081720/213461413-9b1bc650-339c-4184-bde9-e561e0975877.png) | One (Only One) |      |
| ![image](https://user-images.githubusercontent.com/93081720/213461463-f01ee6d5-174a-4255-9f2d-c6d4cb226c88.png) | Zero or One    |      |
| ![image](https://user-images.githubusercontent.com/93081720/213461524-312d2afb-6f8e-4015-8c0c-04917b94a20b.png) | One or Many    |      |
| ![image](https://user-images.githubusercontent.com/93081720/213461593-639f9969-6f94-4a5f-a15e-d9cc9ef44c8a.png) | Zero or Many   |      |

<br>

### 식별(Identifying) vs. 비식별(Non-Identifying)

#### 식별(Identifying)

- 실선
- B 개체의 존재 여부가 A 개체의 존재 여부에 의존적인 관계
  - 자식이 부모에 대해 `존재 종속적(Existence Dependency)`이며, 동시에 `식별 종속적(Identification Dependency)`이다.
  - 존재 종속적
    - 예) '주문상품'은 '주문'이 존재해야 존재할 수 있다
  - 식별 종속적
    - 예) '주문상품'을 검색하기 위해서는 '주문'을 먼저 식별해야한다. => 주문에 있는 상품코드를 통해 주문상품을 식별함
- 끊어질 수 없는 식별 관계, 부모의 키를 반드시 FK로 갖고 있어야 하는경우
  - 예) 회사와 부서 간의 관계

![image](https://user-images.githubusercontent.com/93081720/213463227-ce0ad63c-84dd-46bb-bd50-0c86fa9658b9.png)

<br>

#### 비식별(Non-Identifying)

- 점선
- B 개체의 존재 여부가 A 개체의 존재 여부와 상관 없는 관계
  - 자식이 부모에 대해 `존재 종속적(Existence Dependency)`이지만, `식별 종속적(Identification Dependency)`은 아니다.
    - 예) '사원'은 '부서'에 존재 종속적이지만, '사원'의 한 객체를 식별하기 위해서 '부서'를 식별할 필요가 없다. => '사원' 테이블에서 원하는 사원을 검색(식별)하면 됨
- 끊어지더라도 상관 없는 관계, 종속성이 강제되지 않아 독립적으로 존재 가능한 관계
  - 예) 사원과 부서 간의 관계
    - 각 사원은 부서가 없더라도 독립적으로 존재 가능하며, 부서 또한 사원이 없더라도 존재 가능함(새로 생긴 부서의 경우 사원이 없을 수도 있기 때문)
  - 예) 사원과 사원 간의 멘토-멘티 관계(재귀 관계); 
    - 각 사원은 멘토-멘티 관계가 아니더라도 독립적으로 존재 가능

![image](https://user-images.githubusercontent.com/93081720/213463477-9611284a-90bc-4209-a25e-6761d1cdb290.png)

