# 12_Java_Exception

>  자바 예외

## 1. 에러(Error)와 예외(Exception)

> 에러와 예외의 개념과 차이점

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/c9958d67-8a3e-4e6d-8ae2-f6febfce1fbd)

### 1) 에러(Error)

> 프로그램의 실행에 있어 오작동이나 비정상적 종료를 초래하는 것을 에러(혹은 오류)라고 한다.

그러나 보통 에러(Error)라고 한다면, 메모리부족(OutOfMemoryError)와 스택오버플로우(StackOverFlowError)와 같이 복구할 수 없는 오류를 말한다.

#### (1) 컴파일 에러(Compile Error)

코드를 컴파일 할 때 발생하는 에러

#### (2) 런타임 에러(Runtime Error)

컴파일 성공 후 프로그램을 실행하는 도중에 발생하는 에러

#### (3) 논리적 에러(Logical Error)

컴파일 성공 후 프로그램이 정상적으로 실행되나, 의도한 것과는 다르게 동작하는 에러

<br>

### 2) 예외(Exception)

> 프로그램 코드에 의해서 수습될 수 있는 다소 미약한 오류

그러나 보통 예외(Exception)이라고 한다면, 코드 상의 실수로 예기치 않은 상황이 발생했을 때를 말한다.

자바에서는 실행(Runtime) 환경에서 발생할 수 있는 오류를 에러(Error)와 예외(Exception)으로 구분해놓았는데, 차이는 다음과 같다.

- 에러(Error): 프로그램 코드에 의해서 수습될 수 없는 심각한 오류
- 예외(Exception): 프로그램 코드에 의해서 수습될 수 있는 다소 미약한 오류

예외(Exception)의 예를 들면 범위를 벗어난 예외(ArrayIndexOutOfBoundsException), 값이 null인데 참조를 하는 예외(NullPointerException), 존재하지 않는 파일을 입력해서 발생하는 예외(FileNotFoundException) 등이 있다.

예외는 크게 2가지로 구분한다.

- Checked Exception
- Unchecked Exception

<br>

## 2. Checked / Unchecked Exception

> Checked Exception과 Unchecked Exception

자바의 모든 예외는 `Exception`클래스와 그 하위 클래스로 구성되어 있다.

- Exception 클래스 밑에는 RuntimeException 클래스와 RuntimeException 클래스가 아닌 Exception 클래스들로 구성되어 있다.

### 1) Checked Exception

> Exception 클래스의 하위 클래스이면서, RuntimeException 클래스가 아닌 Exception들

Checked Exception의 가장 큰 특징으로는

- **`반드시 예외를 처리`해줘야** 한다.
  - try/catch와 throw(s) 등
- **`컴파일 시점에 예외가 자동적으로 확인`**된다.
  - 즉, 예외처리를 하지 않으면 자바에서 컴파일할 때 자동적으로 check하여 정상적으로 실행되지 않을 수도 있는 프로그램의 실행을 컴파일 단계에서 막는다.

Checked Exception의 가장 큰 예는 다음과 같다.

- IO입력에 대한 예외(IOException)
- 실수로 클래스의 이름을 잘못 적어, 존재하지 않는 클래스를 호출함(ClassNotFoundException)
- 존재하지 않는 파일의 이름을 입력(FileNotFoundException)

<br>

### 2) Unchecked Exception

> RuntimeException의 하위 클래스들

Unchecked Exception의 가장 큰 특징으로는

- **`명시적인 예외 처리를 강제하지 않는다.`**
  - 개발자들에의해 코드 상에서 발생하는 문제이기 때문에 예외 처리를 강제하지 않는다.
  - 개발자가 스스로 예외를 발생하지 않도록 코드를 설계하거나, 발생할 수 있는 예외를 인지하고 처리를 해주면 된다.
    - 예외 처리가 필요한 상황인데 하지 않는다면, 프로그램이 비정상적으로 종료되거나 의도한대로 동작하지 않기 때문에 예외 처리가 필요한 상황에서는 개발자 스스로 예외 처리를 해줘야 한다.
- **`실행 단계에서 예외가 확인된다.`**
  - 따라서 프로그램의 컴파일과 실행 자체는 보장하지만, 실행 도중에 예외로 인해서 비정상적으로 프로그램이 종료되거나 의도한대로 동작하지 않을 수 있다.

Unchecked Exception의 가장 큰 예는 다음과 같다.

- 정적으로 할당한 배열의 범위를 벗어남(ArrayIndexOutOfBoundsException)
- 값이 null인 변수를 참조함(NullPointerException)

<br>

### 3) Rollback

Checked / Unchecked Exception의 차이점 중 하나는 바로 Rollback 여부이다.

트랜잭션(Transaction) 수행 도중 예외 발생 시, 이를 Rollback하느냐 마느냐에 차이가 있다.

- Checked Exception은 rollback하지 않는다.
  - 왜냐하면 Checked Exception은 코드 레벨에서 처리되어야 할 예외이기 때문에, 비즈니스 로직을 다루고 이에 대한 처리를 수행하는 트랜잭션과는 거리가 멀기 때문이다.
  - Checked Exception을 처리해주지 않아서 예외가 발생한 순간, 프로그램은 비정상적으로 종료되기 때문에 굳이 rollback을 할 필요가 없다.
- Unchecked Exception은 rollback한다.
  - 왜냐하면 코드 상의 이유로 예외가 발생한 것이기 때문에 프로그램이 비정상적으로 종료되지 않고 계속해서 정상적으로 실행되는 상태에 있으려면 예외를 처리해서 rollback하여 예외가 발생하기 이전에 정상적으로 수행되던 상태로 돌아가야 하기 때문이다.

<br>

### 4) Custom Exception (사용자 정의 예외)

개발을 하다보면 사용자 정의 예외를 만들어 예외 처리를 하는 경우가 많다.

이 때 무조건적으로 Exception 클래스를 확장해서 사용하기 보다는, 좀 더 구체적으로 예외를 처리하기 위해서 실행 단계에서 처리할 수 있는 예외라면 RuntimeException클래스를 확장해서 Unchecked Exception으로 다루는 것이 좋다.

그렇게 하는 것이 보다 올바른 코드 작성법이고, 무조건적으로 Checked Exception으로 던진 다음에 이를 Unchecked로 변환하여 예외 처리하는 것보다는 구체적으로 예외를 처리하기 위해 처음부터 Unchecked Exception으로 던지는 것이다.

<br>

| 구분          |                      Checked Exception                       |                     Unchecked Exception                      |
| ------------- | :----------------------------------------------------------: | :----------------------------------------------------------: |
| 분류          | Exception의 하위 클래스 중 <br />RuntimeException을 제외한 모든 예외 |                  RuntimeException 하위 예외                  |
| 처리 여부     |                  반드시 예외를 처리해야 함                   |              명시적인 예외 처리를 강제하지 않음              |
| 확인 시점     |                         컴파일 시점                          |                          실행 시점                           |
| rollback 여부 |                              X                               |                              O                               |
| 대표 예외     |                IOException<br />SQLException                 | NullPointerException<br />IllegalArgumentException<br />ArrayIndexOutOfBoundException |

<br>

## 3. 예외 처리 방법

>  예외 처리를 위한 3가지 방법

### 1) 예외 복구

다른 작업으로 흐름 유도

```java
int maxRetry = MAX_RETRY_CNT;
while (maxRetry-- > 0) { // 재시도 횟수가 0보다 큰 동안 반복함
    try {
        // 예외가 발생할 수도 있는 로직 실행
        // 예) 리소스를 할당받아 어떤 작업을 수행
    } catch (Exception e) {
        // 로그 출력 및 대기
    }
    finally {
        // 예외 발생 여부와 상관없이 반드시 실행되는 로직
        // 예) 사용했던 리소스를 반납 및 원복
    }
} throw new MaxRetryFailedException();
```

위 코드는 예외가 발생했을 시, 재시도를 통해서 예외를 복구하는 코드의 예이다.

예외가 발생하면 그 예외를 잡아서 바로 처리하는 것이 아니라, 발생한 예외에 대한 로그를 출력하고 일정 시간 대기 후 다시 남은 재시도 횟수만큼 재실행하다가 최대 재시도 횟수를 초과하면 사용자 정의 예외를 발생시킨다.

이렇게 구현하면 예외가 발생하더라도 재시도를 통해 정상적인 흐름으로 복구할 수 있도록 하거나, 다른 흐름으로 넘어갈 수 있도록 유도하여 정상적으로 작업을 종료할 수 있을 것이다.

<br>

### 2) 예외 처리 회피

예외를 처리하지 않고 호출한 쪽으로 throw

```java
public void add() throws SQLException {
    // ... 구현 로직
}
```

`throws` 키워드를 통해 메서드 선언부에서 바로 호출한 쪽으로 예외를 throw하는 방법으로, 예외를 처리하지 않고 회피하는 것이다. 즉, 여기선 add 함수를 호출한 쪽으로 SQLException을 throw한 것이다. 그러면 호출한 쪽에서는 throw 받은 Exception을 처리해야 하는 상황이다.

이렇게 무책임하게 예외를 바로 throw하는 것은 위험하다. 호출한 쪽에서 예외를 처리하는 것이 최선의 방법이라는 확신이 있을 때만 사용해야 한다.

<br>

### 3) 예외 전환

명확한 의미 전달을 위해 보다 명확한 다른 예외로 전환 후 throw

```java
catch (Exception e) {
    // ...
    throw DuplicateUsernameException();
}
```

예외를 던질 경우, 호출한 쪽에서 예외를 처리해야 하는데 이 때의 예외를 보다 명확하게 인지하여 처리할 수 있도록 돕는 예외 throw 전략이다. 어떤 예외인지 분명해지면 이에 대한 처리가 보다 수월해질 수밖에 없다.

<br>

### 4) 예외 처리가 중요한 이유

단순히 `try/catch`구문을 사용해서 예외를 잡아놓고, catch에서 아무것도 하지 않고 비워두거나 단순히 에러 메세지만 출력하는 방식으로 구현해두면 위험한 행동일 수도 있다.

왜냐하면 컴파일 오류가 나지 않아서 실행은 잘 되겠지만, 예외가 발생했을 때 이에 대한 정확한 원인을 파악하고 개발 및 유지보수를 함에 있어 어려움을 유발하는 행동이기 때문이다.
