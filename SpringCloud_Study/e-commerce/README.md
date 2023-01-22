# Spring Cloud로 개발하는 MSA

## MSA

### discovery-service



<br>

### apigateway-service



<br>

### user-service

#### Features

- 신규 회원 등록
- 회원 로그인
- 상세 정보 확인
- 회원 정보 수정/삭제
- 상품 주문
- 주문 내역 확인



#### BCryptPasswordEncoder

- password를 해싱하기 위해 Bcrypt 알고리즘 사용
- 랜덤 SALT를 부여하여 여러 번 Hash를 적용한 암호화 방식
- Application에 @Bean으로서 passwordEncoder()를 등록시켜주고, 사용하려는 Service에서 의존성 주입을 해줘야함



#### 유의사항

h2는 1.3대 버전으로 사용함(1.3.176)

h2 DB는 1.4.198버전 이후에는 보안 문제로 자동으로 데이터베이스를 생성하지 않는다. 따라서 웹으로 h2에 접속하여 이전에는 기본적으로 생성되었던 testdb에 접근하려고 하면 testdb가 없다고 나온다.

- 1.3.176버전으로 설정하고 바로 Test Connection을 눌렀을 경우 Test successful이라는 메세지가 나온다.

![image](https://user-images.githubusercontent.com/93081720/212921614-49beab7f-4982-4be8-b0b1-25873088cc69.png)

<br>

Springboot 2.6.2 버전 + Spring Cloud 2021.0.0 버전 조합으로 사용할 경우 Validation 체크를 하고 싶으면 다음과 같이 dependency를 따로 추가시켜줘야 한다.

- 버전은 3.0.1을 사용하거나 2.0.2버전을 사용해주면 된다.

```groovy
implementation 'jakarta.validation:jakarta.validation-api'
```

<br>

@JsonInclude(JsonInclude.Include.NON_NULL)

- Json 데이터에서 Null인 데이터는 제외 => Null 값인 데이터는 반환하지 않음
- 예를 들어, 엔티티를 DTO화할 때, Null인 데이터는 제외하고 반환하는 것임

<br>

### catalogs-service

#### Features

- 상품 목록 조회

#### 프로젝트 생성

![image](https://user-images.githubusercontent.com/93081720/213919875-3d4ea279-054c-4adf-b5a4-4a35c047ebda.png)

Springboot 버전은 2.4.1로 하고, dependency 설정은 아래와 같이 추가한다.

![image](https://user-images.githubusercontent.com/93081720/213919954-325e9e78-0683-4a7c-9aa4-048919a67b97.png)

<br>

### orders-service

#### Features

- 사용자별 상품 주문
- 사용자별 주문 내역 조회

#### 프로젝트 생성

![image](https://user-images.githubusercontent.com/93081720/213923030-f4d28de6-21eb-48b3-acd3-48d344f89711.png)

catalogs-service와 마찬가지로, springboot 버전은 2.4.1, dependency도 동일하게 추가시켜 준다.

![image](https://user-images.githubusercontent.com/93081720/213919954-325e9e78-0683-4a7c-9aa4-048919a67b97.png)
