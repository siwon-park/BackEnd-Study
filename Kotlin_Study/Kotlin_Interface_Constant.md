# Kotlin interface 상수

> 자바의 interface에서는 상수를 정의할 수 있으나, 코틀린의 interface에서는 불가능하다. 왜 그럴까?

## 01_Java interface 상수

자바의 interface에서는 상수를 정의할 수 있다.

`static final 타입` 또는 `타입`을 붙여서 상수를 정의할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/205498323-428fd1f4-debc-4aa7-bec7-f2ba8d537c7a.png)

**그러나 Effective Java에서는 interface에 상수를 정의하지 말라고 한다. 그 이유는 다음과 같다.**

- interface에 정의한 상수는 상속받는 각각의 클래스에서도 그대로 복사된다.
- 상속받은 각각의 클래스에서도 interface의 상수가 아닌 각각의 클래스의 상수 접근이 가능하다.
- interface에서는 정의를 넘어 상수를 사용하게 되면 사용자에게 혼란을 줄 수 있다.
- 외부 구현이 아닌 내부 구현에 대한 상수들로 인해 혼란을 줄 수 있다.

### 예

#### 예시1)

```java
public class JavaInterfaceTest {

    @Test
    public void test() {
        System.out.println(JavaInheritance.EXTRA_NAME); // extra_name 출력
        System.out.println(JavaInheritance.EXTRA_VALUE); // extra_value 출력
    }

    interface JavaTest {
        String EXTRA_NAME = "Name";
        String EXTRA_VALUE = "extra_value";
    }

    class JavaInheritance implements JavaTest { // 클래스 안의 EXTRA_NAME이 우선

        private static final String EXTRA_NAME = "extra_name";
    }
}
```

JavaInheritance 내부에 있는 `EXTRA_NAME`이 우선이기 때문에 이를 출력하게 된다.

허나 위 코드에는 설계상 2가지 문제점이 있다.

- 외부에서 interface만 보는 사람은 EXTRA_NAME은 항상 Name 일 것으로 예측하고 사용한다.
- EXTRA_VALUE는 상속받은 JavaInheritance에 정의하지 않았지만 접근 가능하고, 동일한 메모리 주소를 바라보겠지만 변수가 하나 더 추가되었다.

#### 예시2)

위 코드는 inner class로 정의했기 때문에 test함수에서 접근이 가능했다. 하지만 만약 outer class에서 정의한다면 어떻게 될까?

```java
public class JavaInterfaceTest {

    @Test
    public void test() {
        JavaInheritance inheritance = new JavaInheritance();
        System.out.println(inheritance.isSameExtraName(JavaTest.EXTRA_NAME)); // false
        System.out.println(inheritance.isSameExtraValue(JavaTest.EXTRA_VALUE)); // true
    }
}


interface JavaTest {
    String EXTRA_NAME = "Name";
    String EXTRA_VALUE = "extra_value";
}

class JavaInheritance implements JavaTest {

    private static final String EXTRA_NAME = "extra_name";

    public boolean isSameExtraName(String key) {
        return EXTRA_NAME.equals(key);
    }

    public boolean isSameExtraValue(String key) {
        return EXTRA_VALUE.equals(key);
    }
}
```

위의 예시에서는 `JavaInheritance` 클래스는 JavaTest를 구현했지만, `EXTRA_NAME`은 `extra_name`이기 때문에 

`JavaTest`인터페이스의 `EXTRA_NAME`과는 다르다. 따라서 `false`를 리턴하게 된다.

JavaTest interface를 사용하는 입장에서는 당연하게도 EXTRA_NAME, EXTRA_VALUE 모두가 true가 나와야 한다. 하지만 누군가 실수로 위의 예시처럼 동일한 이름으로 JavaInheritance에 똑같은 이름의 `EXTRA_NAME`을 새로 정의하면서 false, true가 리턴되었다.

### 정리

> 결국 Java의 interface에 정의할 수 있는 상수는 2가지 문제점을 안고 있다.

- interface에 상수를 정의하더라도, 상속받은 클래스에서 상수를 다시 한번 재정의 할 수 있다.
- interface에 상수를 정의하고, 이를 상속받는 자식들 역시 클래스 내에 상수를 다 가지고 있다.

<br>

## 02_Kotlin Interface 상수

kotlin interface는 인터페이스 본연의 작업을 가질 수 있도록 정의만을 허용한다.

다음과 같이 interface에 상수를 정의하려 하면 즉시 오류가 발생한다.

![image](https://user-images.githubusercontent.com/93081720/205499034-54c8c024-27d9-46b0-a0b4-24363688e1d0.png)

![image](https://user-images.githubusercontent.com/93081720/205499046-c7811db0-01e6-43c6-bb60-f683dbbeb4ce.png)

interface에 정의하는 Property에서는 초기화를 할 수 없다. 말 그대로 정의만 하라는 의미다.

그래도 상수는 필요하다. 단, 임시 방편으로 아래와 같이 사용할 수는 있다.

### 임시 방편

#### companion object

```kotlin
interface KotlinTest {

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_VALUE = "extra_value"
    }
}
```

이 방법이 임시 방편인 이유는 KotlinTest 인터페이스를 구현할 경우 여전히 동일한 이름으로 내부에서 정의하는 것이 가능하기 때문이다.

```kotlin
class KotlinInheritance : KotlinTest { // KotlinTest interface 구현

    companion object {
        private const val EXTRA_NAME = "extra_name" // 동일한 네이밍으로 재정의 가능
    }

    fun isSameExtraName(key: String): Boolean {
        return EXTRA_NAME == key
    }
}
```



### 결론

인터페이스에서 상수를 정의하지 않는 것이 가장 확실한 방법이다.