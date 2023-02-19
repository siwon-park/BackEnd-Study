# 03_Switch Case 구문

if-else if 구문을 여러 개 쓰지 않기 위해 switch-case 구문을 사용할 수도 있다.

## 1. 기본적인 switch-case

기본 형태는 다음과 같다.

```java
switch(입력변수) {
    case 입력값1: ...
         break;
    case 입력값2: ...
         break;
    ...
    default: ...
         break;
}
```

- 예시

![image](https://user-images.githubusercontent.com/93081720/212358478-837c704f-a8bc-45dc-ba88-b008c9742257.png)

<br>

## 2. 향상된 switch-case

자바 17부터 새로운 형태의 switch-case 구문으로 사용 가능하다.

```java
타입 변수명 = switch(입력변수) {
    case 입력값1 -> ...;
    case 입력값2 -> ...;
    ...
    default-> ...;
};
```

- 예시

![image](https://user-images.githubusercontent.com/93081720/212358176-fd13a3c7-fc90-4463-9483-b9688098a234.png)