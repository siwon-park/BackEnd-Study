# 14_코틀린의 다양한 클래스들

## 01_Data Class

> DTO

데이터(필드), 생성자와 getter, equals, hashCode, toString 등으로 구성됨

![image](https://user-images.githubusercontent.com/93081720/199056494-b2d46754-0393-40bf-90eb-55071f65a3ea.png)

=> `data class`를 선언하면 equals, hashCode, toString이 자동적으로 만들어짐

<br>

## 02_Enum Class

> 클래스처럼 보이게 하는 상수, 서로 관련있는 상수들끼리 모아 상수들을 정의하는 것, 열거형 클래스

추가적인 클래스를 상속받을 수 없다. 인터페이스는 구현할 수 있으며, 각 코드가 싱글톤임

※ When은 enum class를 사용할 때 빛을 더 발한다.

- if-else보다 가독성이 우수함
- 컴파일러가 해당 클래스의 모든 타입을 알고 있기 때문에 다른 타입에 대한 로직을 else구문으로 작성하지 않아도 됨 

![image](https://user-images.githubusercontent.com/93081720/199056799-ddfd76b6-1a39-4816-98d5-cbe498785afc.png)

<br>

## 03_Sealed Class & Sealed Interface

> 추상 클래스의 한 종류로, 상속 받는 자식 클래스의 종류를 제한하는 특성을 가진 클래스

**컴파일 때 하위 클래스의 타입을 모두 기억**한다. 즉, 런타임 때 클래스 타입이 추가될 수 없다.

하위 클래스는 같은 패키지에 있어야 한다.(외부에서는 sealed class를 상속받을 수 없다.)

Enum Class와 다른 점

- 클래스를 상속받을 수 있다.
- 하위 클래스는 멀티 인스턴스가 가능하다.

![image](https://user-images.githubusercontent.com/93081720/199057191-e85e9bde-e24a-44a5-92fe-7fb205a38dcd.png)

추상화가 필요한 Entity나 DTO에 Sealed Class를 활용 가능함



### Sealed Class를 사용하는 이유?

일반적인 클래스에서는 자식 클래스가 부모 클래스를 상속받았을 때, 컴파일러는 부모 클래스를 상속받은 자식 클래스들이 있는지 알지 못한다.

예를 들어, 사람의 상태를 클래스로 표현하고자 할 때,

```kotlin
abstract class PersonState

class Running : PersonState()
class Walking : PersonState()
class Idle : PersonState()
```

다음과 같이 else 로직을 반드시 작성해줘야 한다.

```kotlin
fun getState(personState: PersonState): String {
    return when(personState) {
        is Running -> TODO()
        is Walking -> TODO()
        is Idle -> TODO()
        else -> TODO() // 반드시 else 로직을 작성해줘야함 => 컴파일러가 PersonState를 상속받은 하위 클래스의 종류를 알지 못하기 때문
    }
}
```

또한 다음과 같이 분기 처리를 하나 지워줘도 컴파일 상에서 문제가 발생하지 않는다. 이는 실제 앱의 동작에 문제를 발생시킬 수 있음에도 컴파일러는 알지 못한다. 왜냐하면 위와 마찬가지로 컴파일러가 PersonState를 상속받은 하위 클래스의 종류를 알지 못하기 때문

```kotlin
fun getState(personState: PersonState): String {
    return when(personState) {
        is Running -> TODO()
        // is Walking -> TODO() // Walking을 지워도 컴파일러 상에서 오류를 잡아내지 못한다 => 실제 앱의 동작에 치명적인 에러를 발생시킬 가능성
        is Idle -> TODO()
        else -> TODO() // 반드시 else 로직을 작성해줘야함 => 컴파일러가 PersonState를 상속받은 하위 클래스의 종류를 알지 못하기 때문
    }
}
```

=> PersonState를 abstract class가 아니라 `sealed class`로 작성하면 문제가 해결된다.

<br>