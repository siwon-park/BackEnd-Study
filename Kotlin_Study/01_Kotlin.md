# 01_코틀린 변수, 타입, 연산자

코틀린은 정적 타입 언어이다. 프로그램 구성 요소의 타입을 컴파일 시점에 알 수 있고, 프로그램 안에서 필드나 메서드를 사용할 대 컴파일러가 타입을 검증해준다.

※ 코틀린에서 별도 접근 제한자를 붙이지 않으면 모두 public이다(public 생략 가능)

<br>

## 01_코틀린에서 변수를 다루는 방법

### 01_ 변수 선언 키워드 var & val

Tip) 모든 변수는 우선 val로 만들고 꼭 필요한 경우 var로 변경한다.

자바에서 long vs. final long => 수정 가능하냐(가변) vs. 수정 가능하지 않냐 차이(불변)

#### var

>  variable

한 번 값을 지정해주고 나더라도 값을 변경해줄 수 있음(가변)



#### val

> value

한 번 값을 지정해주고 나면 더 이상 할당(assign) 불가능(불변)

val 컬렉션에는 element를 추가해줄 수 있다. => 리스트 자체는 못 바꾸지만 리스트에 값을 추가하는 것은 가능하다는 의미



코틀린에서는 컴파일러가 타입을 알아서 지정해주기 때문에 별도로 표기하지 않아도 되지만, 명명해줄 수도 있다.

```kotlin
var number3 : Int = 5;
```

코틀린에서 변수 초기화 => 값을 할당하지 않는 변수 초기화는 타입을 반드시 명시해줘야한다.

```kotlin
var number4; // 에러 발생
var number4: Int; // 값을 할당하지 않는 변수 초기화는 타입을 반드시 명시해줘야한다.
```

<br>

### 02_ 코틀린에서 Primitive Type

자바에서 primitve type과 reference type

long은 primitve type과 Long은 reference type

연산을 할 때는 레퍼런스 타입을 쓰지 않는 것을 자바에서는 권장하고 있음 => 박싱과 언박싱이 일어나면서 불필요한 객체 생성이 이루어지기 때문



숫자, 문자, 불리언과 같은 몇몇 타입은 내부적으로 특별한 표현을 갖는다. 이 타입들은 실행 시에 primitive value로 표현되지만, 코드에서는 평범한 클래스처럼 보인다.

즉, 프로그래머가 boxing과 unboxing을 고려하지 않아도 되도록 코틀린이 알아서 처리해준다.

```kotlin
// Long 레퍼런스 타입으로 보이지만 코틀린 내부에서 필요에 따라 원시형(long)으로 변환시킴
var number: Long = 1000L;
var number5: Long = 1_000L;
```

<br>

### 03_코틀린에서 nullable 변수

자바에서 레퍼런스 타입 변수에는 null이 들어갈 수 있다.

코틀린에서 nullable 변수 처리는 타입 뒤에 `?`(물음표)를 붙임으로써 null을 할당할 수 있다.

이 때, 원래 타입이 아니라 아예 다른 타입으로 간주된다.

```kotlin
var number_null = 1000L;
// number_null = null; // 불가능

var null_number: Long? = 1000L; // ?(물음표)를 붙여야 null을 할당 가능
null_number = null; 
```

<br>

### 04_코틀린에서 객체 인스턴스화

자바에서는 객체 인스턴스화를 할 때 `new`키워드를 붙이지만, 코틀린에서는 붙이지 않아도 된다.

```java
Person person = new Person("시원박"); // java: new 키워드 필요
```

```kotlin
var person = Person("시원박"); // kotlin: new 키워드 불필요
```