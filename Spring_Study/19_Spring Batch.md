![image](https://user-images.githubusercontent.com/93081720/211203284-31b8adf1-c3ee-4f4a-a61e-9abff5d0f50d.png)

# 19_Spring Batch

## 1. Batch?

> 요청이 들어올 때마다 실시간으로 데이터를 처리하는 것이 아니라, **일괄적으로 모아서 하는 작업을 Batch(배치) 작업**이라고 한다.

이러한 특성 덕분에, 배치 작업은 일반적으로 **정해진 시간에 대량의 데이터를 일괄적으로 처리한다**는 특징을 가지고 있다.

여기서 착각하지 말아야 할 것이, 배치는 **스케쥴러의 개념을 포함하고 있지 않다**는 것이다.

스케쥴러는 '주기성(periodic)'을 가지고 있어, 주기적으로 일을 처리할 때 쓰인다.

스케쥴러는 배치 작업을 보완하기 위해서 쓰이며, 주기적으로 특정 작업을 일괄적으로 처리하고자 할 때 배치에 스케쥴러를 덧붙여 사용한다.

<br>

## 2. Spring Batch Architechture

![image](https://user-images.githubusercontent.com/93081720/211204820-d8d47557-4d66-4d39-9624-71b3543742d9.png)

### Application



### Batch Core



### Batch Infrastructure



<br>

## 3. 