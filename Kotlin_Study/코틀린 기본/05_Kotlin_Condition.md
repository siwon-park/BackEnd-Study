# 05_코틀린 조건문

## 01_if 문

사용에 있어서 자바와 큰 차이가 없다

단, 자바에서 if-else는 Statement이지만, 코틀린에서는 Expression이다

### Statement / Expression

Statement가 Expression보다 더 큰 개념

#### statement

프로그램의 문장, 하나의 값으로 도출되지 않는다

#### Expression

반드시 하나의 값으로 도출되는 문장

```java
// 자바
int score = 30 + 40; // Expression이면서 Statement
String grade = score >= 50 ? "P" : "F";
```

코틀린에서는 다음과 같이 if-else 구문을 return할 수 있음 => 3항 연산자가 없음(필요 없기 때문)

![image](https://user-images.githubusercontent.com/93081720/198839010-05bc7c72-8fd6-4c72-bbd6-c05b16ac7974.png)

<br>

#### 팁

다음 두 if 문은 같은 의미를 가진다

```kotlin
if (0 <= score && score <= 100) {
    ...
}

if (score in 0..100) {
    ...
}
```

<br>

## 02_switch와 when

자바의 switch 구문을 코틀린에서는 when으로 표현할 수 있다.

### 자바의 switch

![image](https://user-images.githubusercontent.com/93081720/198839684-521b00c4-f888-4f65-9e12-db0c84300035.png)

### 코틀린의 when

| v1                                                           | v2                                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/198839783-ecbc13c7-4c20-4ae0-bc1a-ee6d18cad375.png) | ![image](https://user-images.githubusercontent.com/93081720/198839798-c600dfed-d19c-49ac-86d4-d7389fc1ca81.png) |

```
when (값) {
	조건부 -> 어떤 구문
	조건부 -> 어떤 구문
	else -> 어떤 구문
}
```

조건부에는 expression이라도 들어갈 수 있다 => 예) is 타입

따라서 다음과 같은 특이한 구문도 가능하다

- 형 확인 및 형 변환

| 자바                                                         | 코틀린                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/198840134-1d990341-706c-4364-ae57-0088348495f4.png) | ![image](https://user-images.githubusercontent.com/93081720/198840046-8526bb54-a2c9-4692-bfc0-9c89ce707c42.png) |
| obj가 String 타입인지 확인하고 형 변환 필요                  | is String을 통과했으니 obj는 String임                        |

- 다중 조건 확인

| 자바                                                         | 코틀린                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/198840505-fefc7418-432b-4209-8c1e-f342035a441c.png) | ![image](https://user-images.githubusercontent.com/93081720/198840522-69806eb6-4b9e-4999-990f-777c3e6c5d58.png) |
| \|\|(or)로 다중 조건을 일일이 써줘야함                       | 콤마(,)로 다중 조건을 확인 가능                              |

- 값 자체는 아무것도 없는 것도 가능하다.
  - 단, 이때는 괄호를 쓰지 않고 바로 중괄호를 쓴다

![image](https://user-images.githubusercontent.com/93081720/198840666-b63ebc14-db4f-41d4-b3ed-2645bad85471.png)