# 10_Data Synchronization

데이터 동기화 방법

## 1. Multiple MicroService

예를 들어, Order Service가 2개 기동한다고 했을 때, 각각의 Order Service에 대한 데이터 처리를 어떻게 할 것인가?

### 해결 방법

#### 1. 하나의 DB 사용

하나의 DB를 사용하는 것은 괜찮지만, `동시성 문제`와 `트랜잭션 처리`문제가 숙제로 남게 된다.

#### 2. DB 간의 동기화

여러 개의 DB를 사용하더라도, RabbitMQ, Kafka와 같은 Message Queuing Server를 활용하여 데이터 동기화를 시켜준다.

![image](https://user-images.githubusercontent.com/93081720/220374736-bfab26cd-e158-4c1f-b3fc-86fcd86be417.png)

#### 3. Kafka Connector + 하나의 DB

각 서비스에서 발생했던 데이터를 Message Queuing Server에 보낸 다음, 일정 시간이 지난 이후 DB로 보냄(배치 작업)

![image](https://user-images.githubusercontent.com/93081720/220375496-ac8172df-d488-430b-8f14-0d4bf580814a.png)