# 05_Spring Cloud Gateway

## 1. API Gateway Service

> MSA에서 요청을 어느 서비스로 보낼지 경로를 지정하는 기능, 역할을 하는 서비스(로드 밸런서)

eureka server(discovery-serivce)에서 등록/해제된 서비스들의 정보를 주기적으로 전달(fetch-registry=true인 서비스)하면 apigateway-service는 해당 정보를 기억하고 있다가 요청이 들어오면 해당 서비스로 요청을 보내게 됨



API Gateway Service의 기능

- 인증 및 권한 부여
- 서비스 검색 통합
- 응답 캐싱
- 정책, 회로 차단기 및 QoS 재시도
- 속도 제한
- 부하 분산
- 로깅, 추적, 상관 관계
- 헤더, 궈리 문자열 및 청구 변환
- IP 허용 목록에 추가

<br>

## 2. Netflix Ribbon

### Spring Cloud에서의 MSA간 통신

#### RestTemplate

```java
RestTemplate restTemplate = new RestTemplate();
restTemplate.getForObject("http://localhost:8080/", User.class, 200);
```

<br>

#### Feign Client

`@FeignClient`어노테이션을 통해 MSA의 이름을 가지고 통신을 할 수 있음

```java
@FeignClient("stores")
public interface StoreClient {
    @RequestMapping(method = RequestMethod.GET, value = "/stores")
    List<Store> getStores();
}
```

<br>

### Ribbon

> Client Side Load Balancer

- 서비스의 이름으로 호출
- Health Check => 해당 서비스가 정상적으로 작동하는지 확인
- 비동기 처리가 잘 되지 않아 사용성이 낮음
  - Springboot 2.4버전에서 Maintenance 상태로 더 이상 유지보수가 되지 않고 있음(보류 상태)

## 3. Netflix Zuul

### Zuul

- Springboot 2.4버전에서 Maintenance 상태로 더 이상 유지보수가 되지 않고 있음(보류 상태)
- 비동기를 지원하지만 스프링과의 호환성에 문제가 있어서 사용성이 낮아졌음

<br>

## 4. Spring Cloud Gateway

### 프로젝트 생성 및 설정

![image](https://user-images.githubusercontent.com/93081720/212527539-b247bc38-f58f-4068-bb53-2d332ef433b7.png)

springboot 버전은 2.4.1, lombok과 eureka client, spring cloud routing의 gateway를 추가시켜준다.

![image](https://user-images.githubusercontent.com/93081720/212694008-1ac894ad-39fa-4592-8455-c49dfb2780fd.png)

<br>

### application.yml

![image](https://user-images.githubusercontent.com/93081720/212693448-c83248c7-2f2b-4691-a468-951f4b576665.png)

- routes: 연결할 곳을 지정
  - predicates: 조건문 => 해당하는 Path에 지정된 양식으로 들어왔을 때, 연결된 uri로 보냄
- 단, 필터를 사용할 경우 spring.cloud 이하는 전부 주석처리해도 된다. 필터가 해당 역할을 대신하기 때문이다.

<br>

gateway-service 실행 시 `TomCat`이 실행되는 것이 아니라 비동기 처리에 특화된 `Netty`라는 서버가 실행된다.

<br>

### Filter

![image](https://user-images.githubusercontent.com/93081720/212528180-a40361e9-5c4b-44f2-988a-1e2ab6e88c64.png)

<br>

Filter 코드

- Request헤더나 Response헤더에 key-value쌍을 추가하여 헤더를 추가할 수 있다.
- 필터 적용을 헤제하려면 간단하게 @Configuration과 @Bean 어노테이션을 주석처리하면 된다.

![image](https://user-images.githubusercontent.com/93081720/212528435-41bd637e-feb1-4ee4-9191-5771cf4909a6.png)

<br>

다음과 같이 yml 파일에서 filters 옵션을 추가하여 yml 파일로만 필터를 적용할 수도 있다. (마찬가지로 key-value쌍이다.)

![image](https://user-images.githubusercontent.com/93081720/212547549-46b09bbc-013b-479b-ac89-f3a606ffd182.png)

<br>

### 커스텀 필터 등록

개별적으로 등록해야하는 커스텀 필터

#### 코드 작성

※ 유의점: `ServerHttpRequest`와 `ServerHttpResponse`는 `org.springframework.http.server.ServerHttpRequest`이 아니라 `org.springframework.http.server.reactive.ServerHttpRequest`에서 import 해와야 한다. `reactive` 필수!

`AbstractGatewayFilterFactory`를 상속받아서 `apply`메서드를 구현한 커스텀 필터를 작성한다.

![image](https://user-images.githubusercontent.com/93081720/212548909-c5501ad7-7a40-4c64-b033-94942d4b030f.png)

#### 적용

application.yml 파일에 filters 옵션에 CustomFilter를 등록해준다.

![image](https://user-images.githubusercontent.com/93081720/212548986-08d83154-a841-4a0a-9905-926d7eec7bbe.png)

<br>

### 글로벌 필터

개별적으로 일일히 등록할 필요 없이 공통적으로 적용되는 필터

모든 필터 중에서 가장 먼저 동작하며, 가장 마지막에 종료되는 필터

코드 작성 후 application.yml에 글로벌 필터를 등록시켜줘야 한다.

- 작성 예시

![image](https://user-images.githubusercontent.com/93081720/212549576-77b19ae2-9e19-4dd0-8e7d-f879cd37392a.png)

<br>

## 5. Spring Cloud Gateway - Eureka 연동

![image](https://user-images.githubusercontent.com/93081720/212616395-13016360-5a02-43a0-8896-8abd73fe20b9.png)

### 로드 밸런싱

Spring Cloud Gateway에서 사용하는 로드 밸런싱 기법은 `라운드 로빈(Round Robin)` 방식과 `랜덤(Random)` 방식이다.

#### application.yml 적용

`lb:/[Eureka서버에 등록한 어플리케이션 명]`형태로 로드 밸런싱을 적용할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/212629987-dcadadcf-83cf-4e2d-8380-1ef97b9a5d79.png)





