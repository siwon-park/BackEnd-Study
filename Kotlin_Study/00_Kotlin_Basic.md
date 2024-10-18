# 00_Kotlin_Basic

> 코틀린 기본 정리

## 1. 변수

<br>

## 2. 조건문/반복문

코틀린의 조건문은 java와 동일하며, 반복문의 경우 python과 유사하나 차이가 조금 있다.

### 1) 조건문

java와 완전히 동일함

<br>

### 2) 반복문

#### (1) 정방향

- until (마지막을 포함하지 않음)

```kotlin
for (i in 0 until n) { // 0부터 n - 1까지 반복 → for i in range(n)과 동일
}
```

- `..` (마지막을 포함함)

```kotlin
for (i in 0..n) { // 0부터 n까지 반복
}

// ※ 연속된 범위 구간을 표기할 때도 ..을 사용한다. (x <= n <= y)
if (n in x..y) { // if (x <= n && n <= y)와 동일
}
```

#### (2) 역방향

- downTo (마지막을 포함함)

```kotlin
for (i in n downTo 0) {
}

// 굳이 마지막을 포함하지 않고 싶다면 downTo에서 마지막 숫자를 조정하거나 until과 reversed()를 이용한다.
for (i in (0 until 10).reversed()) { // until이 9까지 가기 때문에 9부터 0까지 역순함
    println(i) // 9, 8, 7, 6, 5, 4, 3, 2, 1, 0
}
```

#### (3) step

step에 숫자를 지정하여 숫자를 건너뛰면서 반복할 수도 있다.

```kotlin
for (i in 1..10 step 2) {
    println(i) // 1, 3, 5, 7, 9
}
```

<br>

## 3. 배열

코틀린의 배열 선언은 java와 python 처럼 간단해보이지 않는다.

### 1) java 및 python

#### (1) java

```java
int[] arr = new int[3];
```

#### (2) python

```python
lst = [0 for i in range(n)]
```

<br>

### 2) Kotlin (1차원)

#### (1) arrayOf()

생성과 동시에 초기화를 진행할 때 사용 (자료형은 생략 가능)

```kotlin
val arr: Array<Int> = arrayOf(1, 2, 3)
val arr2: Array<String> = arrayOf("siwon", "park")
```

#### (2) arrayOfNulls()

Null 값을 채운 크기가 n인 배열을 선언하고 싶을 때 사용 (단, Null 외에 들어갈 타입 명시는 필요)

```kotlin
val arr: arrayOfNulls<Int>(10)
```

#### (3) Array()

특정 값으로 크기가 n인 배열을 선언 및 초기화 (람다 표현식을 활용한다)

```kotlin
val arr: Array<Int> = Array(5) {0} // 크기가 5, 요소가 0인 배열 선언 -> [0, 0, 0, 0, 0] 

// 람다식을 통한 생성 python의 [i for i in range(n)]과 같은 list comprehension
val arr2: Array<Int> = Array(3) {i -> i} // [0, 1, 2]
```

<br>

### 3) Kotlin (2차원, 3차원)

배열 선언 안에 또 다른 배열 선언하는 방식으로 사용

#### (1) 2차원

```kotlin
// 크기가 3 x 5, 요소가 0인 배열 선언
val arr = Array<Array<Int>>(3) {Array<Int>(5) {0}} // -> [[0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]]

val arr2 = Array(3) { Array(5) {0} }
```

#### (2) 3차원

```kotlin
var arr3 = Array(2) { Array(3) { Array(5) {0} } }
```

