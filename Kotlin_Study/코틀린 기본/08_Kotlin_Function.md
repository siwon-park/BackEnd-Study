# 08_코틀린 함수

**※ 코틀린에서 별도 접근 제한자를 붙이지 않으면 모두 public이다. (public 생략 가능)**

## 01_함수 선언 문법

함수가 하나의 결과값을 반환한다면 block 대신에 `=`을 사용할 수 있다.

`=`을 사용해서 함수를 표현했을 때 반환값을 추론할 수있다면 반환값을 따로 명시해주지 않아도 된다.

그러나 block을 사용한 경우에 반환 타입이 Unit(=void)이 아니면, 명시적으로 타입을 작성해줘야한다.

![image](https://user-images.githubusercontent.com/93081720/198879455-a92e1075-f0c5-4e48-9a53-5fd14cc8229e.png)

`=`을 사용하고 if-else문의 표현식이 간단하면, 다음과 같이 축약도 가능하다

![image](https://user-images.githubusercontent.com/93081720/198879481-5bee4d8b-4d5e-4203-b50f-cb8a5103b8e3.png)

<br>

## 02_기본 인자(default argument)

자바는 메서드 이름은 같지만 매개 변수를 달리해줌으로써 동일한 이름의 메서드를 여러 개 만들 수 있다. => 메서드 오버로딩(OverLoading)

같은 메서드를 여러 개 만드는 것은 어떻게 보면 낭비일 수도 있음

파이썬의 함수 사용 방법과 유사함 => **기본 인자의 개념이 있어서 인자의 값을 입력하지 않았을 경우, 기본 인자가 해당 값을 대신 가능** 

![image](https://user-images.githubusercontent.com/93081720/198879599-53d40c6a-620d-4a7a-9add-e9217c5b2683.png)



<br>

## 03_named argument

파이썬 함수에서와 마찬가지로 코틀린 함수에서 named argument를 사용할 수 있다.

단, 자바 함수를 불러와서 사용할 경우에는 name argument를 사용할 수 없다

![image](https://user-images.githubusercontent.com/93081720/198879759-13660a89-8eba-4a87-b2af-10e4167491cf.png)

![image](https://user-images.githubusercontent.com/93081720/198879812-6a0fe3b2-87f3-4b49-ae07-7702fa0d13fa.png)

**Builder를 직접 만들지 않고 Builder를 사용하는 것과 같은 장점을 지니게 된다.**

<br>

## 04_가변 인자(같은 타입의 여러 파라미터)

### 자바

 `...`연산자를 사용해서 가변 인자를 표현한다.

배열을 직접 넣을 경우에는 배열만 넣으면 된다.

![image](https://user-images.githubusercontent.com/93081720/198879938-36eba2b1-7285-49f9-ab09-d8ed7e220946.png)

### 코틀린

`vararg` 키워드를 사용해서 가변 인자를 표현할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/198880042-17f784cd-6d14-4a4f-bbf8-9a50bb4e93eb.png)

단, 배열을 직접 넣을 경우 spread 연산자(*)을 사용해야 한다.

![image](https://user-images.githubusercontent.com/93081720/198880067-3ab00d19-4870-4d10-9dd5-6be2e35600c4.png)