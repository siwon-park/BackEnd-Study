# 17_코틀린 람다

## 01_자바에서의 람다

```
변수 -> 변수를 이용한 함수
(변수1, 변수2,...) -> 변수1과 변수2를 이용한 함수
```

자바에서 함수는 2급 시민으로 변수에 할당되거나 파라미터로 전달할 수 없다.

<br>

## 02_코틀린에서의 람다

코틀린에서는 함수가 그 자체로 값이 될 수 있다. => 함수를 1급 시민으로 간주

따라서 변수에 할당할 수도, 파라미터로 넘길 수도 있다.

람다의 마지막 expression 결과는 람다의 반환값이다.

![image](https://user-images.githubusercontent.com/93081720/199545490-696cf689-7c28-48ab-b2fa-e7cebd76250c.png)

![image](https://user-images.githubusercontent.com/93081720/199545798-6b5b9706-018c-46a2-9d5b-7f6770cb4e98.png)

<br>

## 03_Closure

> 람다가 실행되는 시점에 쓰고 있는 변수들을 모두 포획한 데이터 구조를 Closure라고 부른다.

자바에서 람다에 사용 가능한 변수는 final이어야 하는 제약이 있음

![image](https://user-images.githubusercontent.com/93081720/199547693-6ee6f8b1-0881-4042-a937-02b9a04a8e6a.png)

![image](https://user-images.githubusercontent.com/93081720/199547492-94ffd5c7-dfd5-4b6f-8f8b-3ba31558a0de.png)

그러나 코틀린에서는 제약이 없다.

=> **람다가 시작하는 지점에 참조하고 있는 변수들을 모두 포획하여 그 정보를 갖고 있기 때문**

![image](https://user-images.githubusercontent.com/93081720/199548146-2bd2e5ac-bb27-42c7-9d07-bc58f7fdafa8.png)

<br>

## 04_try with resources



<br>