# 05_Java 정렬

Java의 정렬을 정리

## 1. 기본적인 정렬

### 배열 정렬 시 유의점

배열을 오름차순 정렬한다면, `Arrays.sort(arr);`로 작성하면 되지만, 역순 정렬할 때 다음과 같이 문제가 발생한다.

![image](https://user-images.githubusercontent.com/93081720/224592059-6930f431-4122-4155-ade9-1afc136c102a.png)

만약 배열을 역순 정렬하고 싶다면, 다음과 같이 배열의 타입을 기본형(primitive)이 아니라 참조형(reference), 래퍼 클래스(Wrapper Class)로 선언해야 한다.

![image](https://user-images.githubusercontent.com/93081720/224592466-83fb58f2-b3f0-4c98-a3c1-1d6fd52e7be7.png)

<br>

## 2. Lambda 식 정렬

기본적으로 제공되는 정렬 외에 특정 객체에 따라 정렬 기준을 달리할 필요가 있을 때가 있다.

이 때, 일반적으로 `Comparable`, `Comparator`를 사용하지만 결국에는 하나의 정렬 기준밖에 정의하지 못한다.

예를 들어, Pair 클래스에 대해 Pair.second를 우선으로 정렬하고 그 다음 기준을 Pair.first라고 했을 때,

Pair.first를 우선적으로 하는 정렬은 어떻게 하는가? 라는 의문점이 남는다.

**(물론 람다식 정렬은 이렇게 여러 정렬 조건을 사용할 때만 사용하는 것이 아니라 그냥 바로도 사용 가능하지만, 특이 케이스를 함께 서술하기 위해 작성함)**

![image](https://user-images.githubusercontent.com/93081720/211842287-c702d861-7689-4e41-adb6-fca3c298cf36.png)

이 때, 사용 가능한 것이 정렬을 할 때, 정렬 조건을 선언하여 정렬하는 것이다.

<br>

### 1. 익명 클래스를 활용한 정렬

![image](https://user-images.githubusercontent.com/93081720/211843649-f4e4279e-35ad-4511-ad6b-7d4bf71ec9ba.png)

<br>

### 2. 람다식을 활용한 정렬

#### 람다식

람다식은 `(매개변수, ...) -> {실행문...}`와 같은 형식으로 작성한다.

람다식은 선언과 동시에 생성을 하는 '이름이 없는 일회용 클래스(익명 클래스)'인 `익명 객체`이다.

- 매개 변수 타입을 자동으로 인식하기 때문에 변수 타입을 삭제할 수 있다. 단, 매개 변수 타입이 자동으로 추론 가능할 때만 해당한다.
  - `Calculator cal = (int n) -> {return n + 1;};` 
    => `Calculator cal = (n) -> {return n + 1;};`
- 매개 변수가 하나일 때, 괄호()를 생략할 수 있다. 단, 두 개 이상이거나 없을 때는 반드시 필요
  - `Calculator cal = (int n) -> {return n + 1;};` 
    => `Calculator cal = n -> {return n + 1;};`
- 실행문 로직이 하나로 끝나는 경우 `{}`과 `return`을 제거할 수 있다.
  - `Calculator cal = (int n) -> {return n + 1;};` 
    => `Calculator cal = n -> n + 1;`

<br>

정렬 예시

![image](https://user-images.githubusercontent.com/93081720/211843876-f85e5e54-b49e-4b6c-977e-55c8c8191c90.png)

- ArrayList 정렬

![image](https://user-images.githubusercontent.com/93081720/213353008-2905f179-7f65-4174-82f2-8448f0ea230c.png)

- 배열 정렬

![image](https://user-images.githubusercontent.com/93081720/213353031-f6f48fc7-d494-4183-ab38-915ea10d3da7.png)

- 람다식을 활용한 우선순위 큐 정렬 조건 정의

![image](https://user-images.githubusercontent.com/93081720/211844052-b2f3cac5-4f9b-4e7e-96dc-9c5c92453aff.png)

※ `Integer.compare(x, y)`

- x > y이면 양수를 return
- x == y이면 0을 return
- x < y이면 음수를 return