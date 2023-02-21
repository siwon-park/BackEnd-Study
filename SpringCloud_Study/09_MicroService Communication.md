# 09_Microservice Communication

마이크로서비스 간 통신

## 1. Communication Type

클라이언트의 요청을 처리하는 방식

### 동기 방식(Synchronous)

요청을 처리하기 전까지 서버는 아무것도 하지 못함

### 비동기 방식(Asynchronous)

요청을 처리하는 중에도 다른 일을 할 수 있음

<br>

## 2. Rest Template

> 요청함

스프링에서 제공하는 http 통신에 유용하게 쓸 수 있는 템플릿

스프링 3.0부터 지원, REST API 호출 이후 응답을 받을 때까지 기다리는 `동기 방식`

※ 스프링 5.0부터는 WebFlux 스택과 함께 WebClient 라는 새로운 HTTP 클라이언트를 도입하여 기존의 동기식 API를 제공할 뿐 만 아니라 효율적인 비차단 및 비동기 접근 방식을 지원하여, 스프링 5.0 이후부터는 RestTemplate는 deprecated 되었습니다. (WebClient 사용 지향)

<br>

## 3. FeignClient

Rest Call을 추상화한 Spring Cloud Netflix 라이브러리. 선언적 HTTP Client

- 사용 방법
  - 호출하려는 HTTP Endpoint에 대한 Interface를 생성
  - `@FeignClient` 선언
- Load balanced 지원

<br>

### user-service에서 order-service 호출하기

#### 라이브러리 추가

![image](https://user-images.githubusercontent.com/93081720/220154326-fd1bf966-3496-4e49-9545-3a1e948f05d0.png)

#### 어노테이션 추가

메인 어플리케이션에 `@EnableFeignClients`어노테이션 추가

![image](https://user-images.githubusercontent.com/93081720/220154655-67a83823-c352-4979-be4e-2e575bbec908.png)

#### client 인터페이스 생성

users-service에서 client 패키지를 생성하고 OrderServiceClient 인터페이스를 생성한 후, 아래 내용을 작성

![image](https://user-images.githubusercontent.com/93081720/220360559-8d0e62b4-6769-452d-b326-18a3e93bbd50.png)

#### 결과

users-service에서 orders-service로 해당 유저에 대한 orders 조회 완료

- orders-service의 getOrders 호출 완료

![image](https://user-images.githubusercontent.com/93081720/220359088-c36509e8-71c1-4e7e-83fd-afe2d0fec996.png)

<br>

### Feign Client 로그 사용

#### application.yml 추가

![image](https://user-images.githubusercontent.com/93081720/220362999-4af66eab-76c7-4706-ba4c-ccf721346dd0.png)

#### Main Application에 Bean으로 등록

`import feign.Logger`의 Logger를 호출하여 Bean으로 등록

![image](https://user-images.githubusercontent.com/93081720/220363159-53fff531-c1d9-402c-91f1-976040996672.png)

<br>

### Feign Client 예외 처리

#### 예외 처리를 해야하는 이유

지금 현재 단일 유저 조회를 통해 ㅐorders-service의 getOrders를 호출하여 유저의 orders 정보를 확인하고 있다. 

![image](https://user-images.githubusercontent.com/93081720/220364316-f0d76e53-5a76-4c26-8564-f04817b0b85e.png)

그런데 만약에 예외가 발생한다면, 단일 유저를 조회했을 때 유저 정보는 그대로 나와야 함에도 불구하고, 현재로서는 500 Internal Server 에러가 발생한다.

따라서 이에 대해 예외 처리를 해줌으로써 유저의 주문 정보 조회에 대한 에러가 발생하더라도 유저 정보는 정상적으로 출력해야한다.

- 변경 전

![image](https://user-images.githubusercontent.com/93081720/220365509-9bad7925-1241-4644-8169-8e15de73b0b4.png)

- 변경 후
  - Service에 `@Slf4j` 추가, try-catch 구문으로 예외 처리

![image](https://user-images.githubusercontent.com/93081720/220366227-8f23c4d7-b004-4369-aaf1-520b0b70e7b9.png)

<br>

### ErrorDecoder를 활용한 예외 처리

ErrorDecoder를 활용하면 특정 메서드에 대한 http status별로 예외를 처리할 수 있다.

#### FeignErrorDecoder 생성

error 패키지를 생성한 후 `ErrorDecoder`를 구현한 FeignErrorDecoder를 생성한다.

![image](https://user-images.githubusercontent.com/93081720/220369486-b45a6db5-59b6-4797-aeb4-2c3a5841e43c.png)

#### Main Application에 Bean으로 등록

![image](https://user-images.githubusercontent.com/93081720/220369713-2c628b68-28ff-4b21-805b-e4e6ac731d36.png)

service에서 기존의 방식과 동일하게 사용한다. getOrders 메서드에 대한 예외가 발생하면 FeignErrorDecoder가 catch하여 http 상태 코드에 따라 예외를 분기 처리한다.

![image](https://user-images.githubusercontent.com/93081720/220369911-054e4705-46b9-41d4-8e0f-de8852f5dd5e.png)