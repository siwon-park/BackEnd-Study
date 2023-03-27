# 08_String과 StringBuffer

## 1. String

Java의 String은 `참조형 자료(Reference Type)`이지만, 특별히 `new`키워드 없이 리터럴 표기로 표기할 수 있다.

### 불변성(Immutable)

String의 가장 큰 특징이라고 한다면 바로 <span style="color:Red">`불변(Immutable)`</span>하다는 것이다.

`불변`이라는 것은 값을 한 번 생성하고 나면 변경이 불가능 하다는 것인데, 흔히 String에 문자열을 붙여서 다른 문자열을 만들 수 있어 불변이라는 특성에 대해 착각하기 쉽다.

```java
String result = ""; // 첫 번째 생성
result += "Happy"; // 두 번째 생성
result += " "; // 세 번째 생성
result += "SIWON"; // 네 번째 생성
System.out.println(result); // "Happy SIWON"
```

위 예시를 보면 `String 객체`는 총 <span style="color:Red">`4번`</span> 생성되었다. 즉, 문자열에 문자열을 붙이는 행동은 결국 매번 새로운 객체를 만든 것이다. String이 불변 객체이기 때문에 그렇다.

결론적으로 위와 같이 사용한 것은 좋지 못한 행동이다. 왜냐하면, `객체를 매번 생성하여 쓸 데 없이 메모리를 낭비`하였기 때문이다.

<br>

## 2. StringBuffer

문자열을 추가하거나 변경하고자 할 때 사용하는 자료형이다.

String과 달리 <span style="color:Blue">`가변(Mutable)`</span>자료형이다.

```java
StringBuffer sb = new StringBuffer(); // StringBuffer 객체 생성(1번)
sb.append("Happy");
sb.append(" ");
sb.append("SIWON");
String result = sb.toString(); // String 객체를 생성하고 sb객체 값을 할당(2번)
System.out.println(result); // "HAPPY SIWON"
```

위 예시에서 객체는 총 <span style="color:Red">`2번`</span> 생성되었다. StringBuffer 1번, toString()으로 String 생성에 2번이다.

결론적으로, 문자열을 사용할 때 해당 문자열의 변경 및 추가가 많다면 `StringBuffer`를 사용하는 것이 바람직하다.

<br>

## 3. 속도, 메모리 사용량 비교

매번 String객체를 생성해서 사용하는 것과 StringBuffer를 사용하는 것에 있어서 코드 상의 속도와 메모리 사용량을 비교해보자

### 코드1

String과 += 연산 사용

총 C * (R - 1)번 String 객체를 생성

![image](https://user-images.githubusercontent.com/93081720/227853037-ede867e0-8be5-417a-9f07-5f1bbb086056.png)

<br>

### 코드2

StringBuffer 사용

총 2 * C번 객체 생성(C번 StringBuffer 객체 생성, C번 String 객체 생성)

![image](https://user-images.githubusercontent.com/93081720/227853159-cbcf33a3-353d-43cd-94e7-d60de0c6a3ec.png)

<br>

### 결과 비교

 <span style="color:Red">`빨간 밑줄`</span>이 `StringBuffer`를 사용한 것이고,  <span style="color:Blue">`파란 밑줄`</span>이 `String` 객체를 매번 생성하여 사용한 것이다.

![image](https://user-images.githubusercontent.com/93081720/227854314-5e67e9c3-dc8b-42e3-a11e-63b5a230135b.png)

메모리 차이가 거의 10배 이상 나며, 속도 면에서도 `StringBuffer`를 사용하는 것이 더 빠르다.

따라서, 문자열의 변경 연산이 많을 경우 `StringBuffer`를 사용하는 것이 직접 `String`객체를 사용하는 것보다 훨씬 이득이다.