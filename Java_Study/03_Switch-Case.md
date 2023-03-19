# 03_switch-case 구문

if-else if 구문을 여러 개 쓰지 않기 위해 switch-case 구문을 사용할 수도 있다.

## 1. 기본적인 switch-case

기본 형태는 다음과 같다.

```java
switch (입력변수) {
    case 입력값1: ... // 입력 변수의 값이 입력값1인 경우
         break;
    case 입력값2: ... // 입력 변수의 값이 입력값2인 경우
         break;
    ...
    default: ...
         break;
}
```

- 예시1
  - k 값이 0인 경우, 1인 경우, 2인 경우, 3인 경우로 분기


![image](https://user-images.githubusercontent.com/93081720/212358478-837c704f-a8bc-45dc-ba88-b008c9742257.png)

- 예시2
  - s값이 '*', '+', '/', '-'인 경우로 분기

![image](https://user-images.githubusercontent.com/93081720/226151788-fde330b4-2c50-43cd-82fc-40b3401e5b99.png)

<br>

### switch 구문

- switch 구문의 괄호에는 `char`, `byte`, `short`, `int`, `Character`, `Byte`, `Short`, `Integer`, `String`, `enum`이 올 수 있다.
  - 따라서, <span style="color:Red">case구문에 오는 값도 항상 고정된 상수 값이어야 한다.</span>
- switch 구문에는 변수 조건을 설정할 수 있다.
  - 정수형인 flag를 10으로 나눴을 때, 값에 대한 분기 처리

![image](https://user-images.githubusercontent.com/93081720/226151916-441e90dc-cc86-4fe6-88df-9501d56321e9.png)

- 단, 다음과 같이 `boolean`형은 올 수 없다.

![image](https://user-images.githubusercontent.com/93081720/226152006-1e767059-49c6-4564-a57a-2c30c745f95d.png)

<br>

## 2. 향상된 switch-case

자바 17부터 새로운 형태의 switch-case 구문으로 사용 가능하다.

```java
타입 변수명 = switch (입력변수) {
    case 입력값1 -> ...;
    case 입력값2 -> ...;
    ...
    default-> ...;
};
```

- 예시

![image](https://user-images.githubusercontent.com/93081720/212358176-fd13a3c7-fc90-4463-9483-b9688098a234.png)