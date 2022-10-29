# 03_코틀린 타입

## 01_기본 타입

코틀린에서는 선언된 기본값을 보고 타입을 추론한다

### 차이

자바는 기본 타입 간 변환이 암시적으로 이루어질 수도 있다

```java
int num1 = 4;
long num2 = num1;
System.out.println(num1 + num2);
```



코틀린에서 기본 타입간 변환은 **명시적으로** 이루어져야 한다.

to변환타입() 메서드를 사용해서 변호나

```kotlin
val num1 = 5;
val num2: Long = num1.toLong();
println(num2);
```

#### null이 들어갈 수 있는 경우

Safe call이나 Elivs 연산을 통해 적절한 null처리가 필요하다.

```kotlin
val num3: Int? = 4;
val num4: Long = num3?.toLong() ?: 0L
println(num4);
```

<br>

## 02_일반 타입

### 타입 캐스팅

#### is

value **is** Type

- value가 해당 타입이면 true
- value가 해당 타입이 아니면 false

반대는 `!is`

![image](https://user-images.githubusercontent.com/93081720/198816518-0b80a87b-590b-4244-9a8e-fea9d4e0ca4a.png)

#### as

value **as** Type

- value가 해당 타입이면 해당 타입으로 캐스팅
- value가 해당 타입이 아니면 예외 발생

#### as?

value가 해당 타입이 아니거나, null이면 null 처리 => 안전한 타입 캐스팅

![image](https://user-images.githubusercontent.com/93081720/198816531-ce96fb2a-270b-4a7e-8f3c-e2f26fdbe12c.png)

<br>

## 03_코틀린의 특이한 타입 3가지

### Any

자바의 Object와 같은 역할 => 모든 객체의 최상위 타입

Any에 equals, hashCode, toString 메서드가 존재

### Unit

자바의 void와 동일한 역할

단, void와는 다르게 Unit은 그 자체로 타입 인자로서 사용 가능함

### Nothing

Nothing은 함수가 정상적으로 끝나지 않았다는 사실을 표현하는 역할

무조건 예외를 반환하는 함수 / 무한 루프 함수 등에 사용 가능 => 실제 사용되는 경우는 적다

<br>

## 04_Interpolation & Indexing

### Interpolation 

`${변수}` 형태로 사용하면 값을 할당할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/198816751-22a0a8a9-49ca-4d3f-81dc-bb7f0cb66016.png)

- 중괄호는 경우에 따라 생략해 줄 수도 있다.
-  그러나 생략하지 않는 것이 가독성, 일괄 변환, 정규식 활용에 유리하기 때문에 중괄호를 쓰는 것을 권장한다.

### Indexing

자바의 문자열에서 인덱싱은 `charAt()`메서드를 사용해야 하지만, 코틀린에서는 파이썬과 같이 [] 대괄호로 인덱싱을 할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/198816914-9e926153-17d9-4caf-a3af-cd13131d8518.png)