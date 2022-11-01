# 15_코틀린 컬렉션

## 01_배열

배열은 잘 사용하지 않음

이펙티브 자바에서도 배열보다는 리스트를 사용하는 것을 권장함

### 생성

```kotlin
val array = arrayOf(100, 200)
```

### indices

배열의 인덱스들을 뽑음

```kotlin
for (i in array.indices) {
    println(i) // 인덱스
}
```

### withIndex()

 배열에서 인덱스와 요소를 뽑음. 파이썬의 enumerate와 흡사

```kotlin
for ((idx, value) in array.withIndex()) {
    println("${idx} ${value}")
}
```

### plus

배열에 요소를 추가할 수 있음

```kotlin
array.plus(300) // 배열에 요소 추가
```

<br>

## 02_리스트, 집합, 맵

컬렉션을 만들 때 불변인지 가변인지 선언해줘야 한다

### 가변 컬렉션

컬렉션에  요소를 추가, 삭제할 수 있다.

### 불변 컬렉션

컬렉션에 요소를 추가, 삭제할 수 없다.

불변 컬렉션이라도 그 안에 있는 Reference Type의 필드가 var이라면 필드값을 변경시킬 수 있다.

<br>

### 리스트

팁) 우선 불변 리스트를 만들고, 꼭 필요한 경우에만 가변 리스트로 바꿈

```kotlin
val numbers = listOf(100, 300) //  불변 리스트
val numbers2 = mutableListOf(100, 200) // 가변 리스트; 타입을 추론할 수 있어 생략 가능
val emptyList = emptyList<Int>() // emptyList는 타입을 반드시 지정해줘야함

printNumber(emptyList()) // 단, 추론 가능할 경우는 생략 가능

numbers.get(0) // 0번째 인덱스의 숫자를 가져옴
println(numbers[0])

for (number in numbers) {
    println(number)
}
```



### 집합

```kotlin
val numberSet1 = setOf<Int>(100, 200) // 불변 집합
val numberSet2 = mutableSetOf<Int>(100, 200) // 가변 집합

for (number in numberSet1) {
    println(number)
}

for ((idx, num) in numberSet1.withIndex()) {
    println("${idx} ${num}")
}
```



### 맵

```kotlin
val oldMap = mutableMapOf<Int, String>() // 가변 맵
oldMap[1] = "월요일"
oldMap[2] = "월요일"

val map = mapOf(1 to "월요일", 2 to "화요일") // 불변 맵

// 반복문
for (key in oldMap.keys) {
    println(key)
    println(oldMap[key])
}

for ((key, value) in oldMap.entries) {
    println(key)
    println(value)
}
```



<br>

## 03_컬렉션의 null 가능성

> ?의 위치에 따라  null 가능성에 대한 의미가 달라지므로 유의할 것!

- `List<Int?>`: 리스트에 null이 들어갈 수 있지만, 리스트는 null이 불가능함
- `List<Int>?`: 리스트에는 null이 들어갈 수 없지만, 리스트는 null이 가능함
- `List<Int?>?` : 리스트에 null이 들어갈 수도 있고, 리스트가 null일 수도 있음

코틀린쪽의 컬렉션이 자바에서 호출되면 불변 컬렉션, null과 관련하여 호환에 문제가 있어 이를 염두해둬야함

- 코틀린의 불변 리스트를 자바에서 가져올 경우 해당 컬렉션이 불변인지 모르기 때문에 요소를 추가할 수 있음 => 코틀린에 다시 돌아왔을 때 에러 발생
- 코틀린에 null이 불가능한 리스트를 자바에서 사용하면서 null을 넣어버리고 다시 코틀린쪽으로 주면 에러가 발생할 수 있음

=> 방어 코드를 짜거나 코틀린쪽에서 `Collections.unmodifable컬렉션명()`을 활용하면 변경을 막을 수 있음

코틀린에서 자바의 컬렉션을 가져올 때는 플랫폼 타입을 신경써야함

- 어떤 값들이 허용되는지 확인하여 wrapping





<br>