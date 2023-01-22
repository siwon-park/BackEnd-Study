# 02_코틀린에서 null을 다루는 방법

## 01_코틀린에서 null 체크

### 자바에서 null 처리

자바에서는 문자열에 null이 들어올 수도 있기 때문에 NullPointException을 방지하기 위해 다음과 같이 null에 대한 처리를 해줘야 한다.

```java
public boolean startsWithA1(String str) {
    if (str == null) {
        throw new IllegalArgumentException("null이 들어왔습니다");
    }
    return str.startsWith("A");
}


public Boolean startsWithA2(String str) {
    if (str == null) {
        return null;
    }
    return str.startsWith("A");
}


public boolean startsWithA3(String str) {
    if (str == null) {
        return false;
    }
    return str.startsWith("A");
}
```

### 코틀린에서 null 처리

코틀린에서는 null이 가능한 타입을 ?(물음표)를 붙여서 명시해준다.

또한 **null이 가능한 타입을 완전히 다르게 취급**한다.

```kotlin
fun main() {

}

fun startsWithA1(str: String?): Boolean {
    if (str == null) {
        throw IllegalArgumentException("null");
    }
    return str.startsWith("A");
}

fun startsWithA2(str: String?): Boolean? {
    if (str == null) {
        return null;
    }
    return str.startsWith("A");
}

fun startsWithA3(str: String?): Boolean {
//    str.startsWith("A"); // 에러 발생
    if (str == null) {
        return false;
    }
    return str.startsWith("A");
}
```

<br>

## 02_Safe Call & Elvis 연산자

### Safe Call

null이면 실행하지 않고 null을 반환하고, null이 아니면 실행하여서 실행 결과를 반환함

![image](https://user-images.githubusercontent.com/93081720/198813146-c1ddf010-d7dc-40f3-9fc9-ea2f3088999e.png)

<br>

### Elvis

결과가 null이 아니면 원래 결과를 반환하고 null이면 :뒤의 값을 반환함

※ Elivs 연산자라고 불리는 이유는 ?:을 시계방향으로 90도 꺾었을 때 그 모습이 엘비스 같다고 해서 붙여진 이름

![image](https://user-images.githubusercontent.com/93081720/198814475-0df5cd37-274b-401e-91a0-1b3c89542f5d.png)

<br>

## 03_null이 아님을 단언할 수 있는 경우

!!(느낌표 2개)를 써서 null 타입이 아님을 단언할 수 있다.

예를 들어 DB에 처음 값이 없을 땐 null이지만, 그 이후에는 무조건 값이 있는 경우

```kotlin
fun startswithA5(str: String?): Boolean {
    return str!!.startsWith("A")
}
```

<br>

## 04_코틀린에서 자바 코드 참조

@Nullable, @NotNull 어노테이션을 코틀린에서 이해해서 컴파일할 수 있다.

그러나 어노테이션이 붙어있지 않으면 해당 타입이 null인지 아닌지 알 수 없는 플랫폼 타입이기 때문에 유의해야한다.