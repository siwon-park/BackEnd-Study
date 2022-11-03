# 19_코틀린 기타 개념들

## 01_Type Alias, as import

긴 이름의 클래스, 함수 타입이 있을 때, 축약하거나 더 좋은 이름으로 쓰고 싶을 경우에 사용 가능

### typealias

![image](https://user-images.githubusercontent.com/93081720/199063487-871e0866-899c-4863-9dcc-ab456793e84e.png)

다음과 같이 이름이 긴 클래스를 컬렉션에 사용할 때도 간단히 줄일 수 있다.

![image](https://user-images.githubusercontent.com/93081720/199758204-d7569b56-cb33-4772-a13d-2627ecbe5e08.png)

### as import

> 어떤 클래스나 함수를 임포트할 때 이름을 바꾸는 기능

다른 패키지의 이름이 같은 함수를 동시에 가져오고 싶을 경우 사용. 파이썬의 `import OOO as XXX`와 같음

![image](https://user-images.githubusercontent.com/93081720/199759700-cd0955fe-d12b-4377-859b-6f0e4e4acafc.png)

<br>

## 02_구조 분해, componentN 함수

### 구조 분해

> 복합적인 값을 분해하여 여러 변수를 한 번에 초기화하는 것

![image](https://user-images.githubusercontent.com/93081720/199760259-8b79dfe7-f50d-4929-9767-d3c9e6bf1e61.png)

### componentN 함수

data class는 componentN이라는 함수도 자동으로 만들어준다.

구조 분해 할당을 한다는 것은 결국 componentN 함수를 호출하여 사용한다는 것과 같은 의미

![image](https://user-images.githubusercontent.com/93081720/199760843-ead50c0a-4f93-4ae5-bbf8-1c3251438da8.png)

단, 순서를 바꿔서 넣을 경우 해당 변수를 인식하는 것이 아니기 때문에 순서가 바뀌게 된다.

```kotlin
val person = Person("박시원", 30)
val (age, name) = person // 나이: 박시원, 이름: 30
```

#### componentN 함수 직접 구현

직접 componentN 함수를 구현할 수도 있음

이 때, 일반 클래스여야 하며, `operator`키워드를 붙여줘야 한다.

![image](https://user-images.githubusercontent.com/93081720/199762089-2dad43bb-c200-41df-b9f4-51acf5323726.png)

<br>

## 03_Jump와 Label

### return / break/ continue

코드의 흐름을 제어하는 키워드

#### return

기본적으로 가장 가까운 enclosing function 또는 익명 함수로 값이 반환된다.

#### break

가장 가까운 루프가 제거된다.

#### continue

가장 가까운 루프를 다음 step으로 보낸다.



※ 기본적인 코드에서는 break나 continue를 쓸 수 있으나, `forEach` 구문에서는 사용하지 못한다.

단, 라벨링을 통해 강제적으로 사용하게 할 수 있다

- 예) return@run, return@forEach



### Label

특정 expression에 **라벨이름@**을 붙여 하나의 라벨로 간주하고, break, continue, return 등을 사용하는 기능

#### break

원래 break는 가장 가까운 루프를 멈추게 하나, 라벨링을 통해 특정 루프를 멈추게 할 수 있음

![image](https://user-images.githubusercontent.com/93081720/199764390-92618b34-13ee-47d8-95cc-b0fe126b05a6.png)

![image](https://user-images.githubusercontent.com/93081720/199767367-e49f2886-8df1-42a9-b719-04d4e147f137.png)

#### continue

foreach로 다시 돌아가게 함으로써 continue와 같은 동작을 하게 만듦

![image](https://user-images.githubusercontent.com/93081720/199767730-6d180f7a-c627-40f8-a06c-28464ef3d45b.png)

### 주의!

단, 라벨을 사용한 jump 기능은 사용하지 않는 것을 강력 권장함

코드의 흐름이 위, 아래로 계속 움직일수록 복잡도가 엄청나게 증가하여 유지 보수가 힘들어짐

<br>

## 04_takeIf와 takeUnless

![image](https://user-images.githubusercontent.com/93081720/199766821-87f7a4bb-9760-40ca-aef1-91b3df356289.png)

### takeIf

주어진 조건을 만족하면 그 값이 나오고, 그렇지 않으면 null이 반환된다.

![image](https://user-images.githubusercontent.com/93081720/199766946-e4466713-9918-45c0-abd9-c857294b0182.png)

### takeUnless

주어진 조건을 만족하지 않으면 그 값을 반환하고, 만족하면 null을 반환한다.

![image](https://user-images.githubusercontent.com/93081720/199766646-801628a3-deee-4ea2-922b-2e409b7ef9fe.png)

