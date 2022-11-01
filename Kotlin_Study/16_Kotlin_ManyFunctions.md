# 16_코틀린의 다양한 함수

## 01_확장 함수

코틀린의 목표는 자바와 100% 호환 => 자바의 클래스나 함수를 확장하기 위한 함수를 확장 함수라고 함

```kotlin
// 확장 함수 => String 클래스를 확장함
fun String.lastChar(): Char {
    return this[this.length - 1] // this: 수신 객체, String: 확장하려는 클래스, 수신 객체 타입
}

fun main() {
    val str = "ABC"
    println(str.lastChar())
    
}
```

확장 함수는 클래스에 있는 private 또는 protected 멤버를 가져올 수 없음 => 이게 가능하게 되면 캡슐화가 깨지게 됨(public인 확장 함수가 수신 객체 클래스의 private 함수를 가져오기 때문)



확장 함수와 멤버 함수의 이름이 동일하면 멤버 함수가 먼저 호출된다.



해당 변수의 현재 타입, 즉 정적인 타입에 의해 어떤 확장 함수가 호출될지 결정된다.



자바에서는 코틀린의 확장 함수를 static 함수를 사용하듯이 사용할 수 있음

![image](https://user-images.githubusercontent.com/93081720/199268238-b5d17ee7-ed0e-4dec-8f91-5ae6e714d3ad.png)

<br>

## 02_infix 함수

중위 함수 => 함수를 호출하는 새로운 방법

예) downTo, step과 같은 함수들

![image](https://user-images.githubusercontent.com/93081720/199268928-3e4c0ca8-140c-46b1-8e9e-a39508e36ef7.png)

<br>

## 03_inline 함수

함수가 호출되는 대신, 함수를 호출한 지점에서 함수 본문을 그대로 복사하고 싶은 경우 사용

<br>

## 04_지역 함수

함수 안에 함수를 선언할 수 있음

depth가 깊어지면서, 코드를 난잡하게 쓰게 된다는 단점이 있다.

![image](https://user-images.githubusercontent.com/93081720/199270187-c4e78ffa-25ed-4c43-b06a-e520dd7764ce.png)