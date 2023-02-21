# 07_Spring Cloud Bus

## 1. Spring Cloud Bus

- 분산 시스템의 노드(=마이크로 서비스)를 경량 메세지 브로커(ex- Rabbit MQ)와 연결
- 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달(Broadcast)

메세지 큐잉 방법?

### 동작 과정

#### Spring Cloud Bus 도입 이전

설정 파일이 변경되었을 경우, 각각에 해당하는 마이크로 서비스들이 actuator/refresh를 호출해야 해당 서비스의 구성 파일만 바뀌는 형태 => 모든 변경 사항을 제대로 적용시키기 위해서는 변경된 서비스들이 전부 refresh를 호출해줘야 했음

#### Spring Cloud Bus 도입 이후

클라이언트가 `busrefresh`를 호출하고 아무 마이크로 서비스 하나에 대해 변경 사항이 적용되고, 변경 사항이 있음을 Spring Cloud Bus에게 알려주고, 다른 변경 사항까지 감지하여 변경 사항을 적용시켜야 하는 마이크로 서비스들에게 변경 사항을 적용할 수 있도록 전달함

<br>

## 2. AMQP

Advanced Message Queuing Protocol, 메세지 지향 미들웨어를 위한 개방형 표준 응용 계층 프로토콜

- 메세지 지향, 큐잉, 라우팅(P2P, Publisher - Subscriber), 신뢰성, 보안
- Erlang, RabbitMQ 등에서 사용

### RabbitMQ

- 메세지 브로커
- 초당 20+ 메세지를 소비자(=메세지를 받겠다고 한 시스템)에게 전달
- 메세지 전달 보장, 시스템 간 메세지 전달
- 브로커, 소비자 중심

<br>

### Kafka

Scalar 기반의 오픈 소스 메세지 브로커 프로젝트

- 초당 100k+ 이상의 이벤트 처리
- Pub/Sub, Topic에 메세지 전달
- Ack를 기다리지 않고 메세지 전달 가능
- 생산자 중심

분산형 스트리밍 플랫폼

대용량의 데이터 처리가 가능한 메세징 시스템

<br>

## 3. RabbitMQ 설치 및 실행

Windows에 설치하기 보다 Docker를 통해서 실행하기

※ 공식문서에 설치하기를 눌러서 들어갔을 때도 도커 이미지를 통해 실행하는 방법이 제일 위에 있다

![image](https://user-images.githubusercontent.com/93081720/214313711-2902664b-9fb9-456c-817f-bdee8df4421e.png)

```bash
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.11-management
```

username, password의 기본값은 둘 다 `guest`이다.

※ `-it`옵션을 적용시켰으므로 터미널을 계속 켜둔 채로 실습을 진행할 것을 권장한다. 그게 아니라면 `-it` 대신 `-d`옵션을 써서 detached 모드로 실행시킨다.

<br>

### 프로젝트 적용

#### config-service

`spring-cloud-starter-bootstrap`, `spring-boot-starter-actuator`, `spring-cloud-starter-bus-amqp` dependency 추가

※ bus-amqp 사용 시 에러가 있을 경우  `spring-boot-starter-amqp`를 적용해 볼 것

![image](https://user-images.githubusercontent.com/93081720/214321526-a51bc76f-b9c9-4e6e-a902-2efa8bf3fbc0.png)

#### users-service, gateway-service

`spring-cloud-starter-bus-amqp` dependency 추가

<br>

### application.yml 설정 추가

#### config-service, users-service, gateway-service

![image](https://user-images.githubusercontent.com/93081720/220346859-333e78b7-ee18-45b9-9f27-8d6dca6ea55a.png)

- actuator 옵션에는 `busrefresh`옵션을 추가해준다.

![image](https://user-images.githubusercontent.com/93081720/214324191-197ced18-19e2-4610-87df-967f38b804d7.png)

- config-service가 사용할 외부 application.yml

![image](https://user-images.githubusercontent.com/93081720/220347122-5a62f5bc-58a8-4d74-a7fe-37f047df9ee4.png)



### 테스트

#### 실행 순서

1. RabbitMQ 실행

2. config-service 실행

3. discovery-service (eureka) 실행

4. gateway-service 실행

5. 그 외 필요한 마이크로서비스 실행

- 프로젝트 적용 후 실행 성공 시 다음과 같이 rabbitmq에 대한 설정이 적용되었음을 알 수 있다.

![image](https://user-images.githubusercontent.com/93081720/214323953-6a9d7aba-6492-4d7a-86aa-823aed181ccc.png)

### 

