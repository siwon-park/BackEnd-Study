# 00_OOP

자바에서의 객체 지향 프로그래밍(Object Oriented Programming)

캡슐화, 상속, 다형성, 추상화

<br>

## 1. 캡슐화(Encapsulation)

>  외부에서 내부 접근을 제한하여 정보 은닉, 데이터 무결성을 유지함

변수 또는 함수, 클래스 앞에 `private`, `public`, `protected` 키워드를 붙여 접근할 수 있는 범위를 제한하는 것을 말한다.

<br>

## 2. 상속(Inheritance)

> 자식 클래스가 부모 클래스가 가진 특성과 기능을 그대로 물려 받아 사용함으로써 코드의 재사용성과 유지 보수성을 향상시킴

`extends` 키워드를 통해 자식 클래스가 부모 클래스를 상속받을 수 있다.

자식 클래스에서는 부모 클래스에서 정의했던 메서드나 기능들을 재정의할 수 있는데 이를 `오버라이딩(Overriding)`이라고 한다.

```java
pubilc class Car extends Vehicle {
    // ...(중략)...
}
```

<br>

## 3. 다형성(Polymorphism)

> 하나의 변수 또는 함수가 상황에 따라 다양한 형태 또는 의미로 사용될 수 있음

간단히 말해, 동일한 이름의 클래스나 메서드 등을 다양한 형태로 사용할 수 있다는 의미이다. 전달받는 매개변수의 개수나, 종류가 다르기 때문에 같은 이름의 메서드라도 다른 기능을 할 수 있다.

```java
public class Calculator {
    
    public int add(int a, int b) {
        return a + b;
    }
    
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

분명 하나의 클래스 안에 `add`라는 메서드가 있지만 매개변수의 개수가 다르기 때문에 다른 기능을 하는 메서드로 사용이 가능하다.

또한 인터페이스를 작성하고 해당 인터페이스의 메서드를 구현했을 때, 구현한 클래스별로 동일한 이름의 메서드이지만 기능을 달리할 수 있기 때문에 이 또한 다형성에 해당한다.

<br>

## 4. 추상화(Abstraction)

> 객체의 공통적인 특징을 추출하여 상위의 개념을 정의함

`interface`와 `abstract` 키워드를 사용하여 해당 인터페이스를 상속받은 클래스는 반드시 오버라이딩을 통해 구현의 강제성을 가진다.

인터페이스를 구현하려는 클래스는 `implements` 키워드를 통해 인터페이스의 내용을 구현해야 한다.

Animal 인터페이스는 다양한 동물들이 가지는 공통적인 기능들을 추상화하여 정의한 셈이다.

```java
interface Animal {
    String bark();
}
```

```java
class Dog implements Animal {
    public String bark() return "멍멍";
}

class Cat implements Animal {
    public String bark() return "야옹";
}
```