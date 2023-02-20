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



### user-service에서 order-service 호출하기

#### 라이브러리 추가

![image](https://user-images.githubusercontent.com/93081720/220154326-fd1bf966-3496-4e49-9545-3a1e948f05d0.png)

#### 어노테이션 추가

메인 어플리케이션에 `@EnableFeignClients`어노테이션 추가

![image](https://user-images.githubusercontent.com/93081720/220154655-67a83823-c352-4979-be4e-2e575bbec908.png)