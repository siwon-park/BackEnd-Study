# Java Lambda 식 정렬

Java에서 기본적으로 제공되는 정렬 외에 특정 객체에 따라 정렬 기준을 달리할 필요가 있을 때가 있다.

이 때, 일반적으로 `Comparable`, `Comparator`를 사용하지만 결국에는 하나의 정렬 기준밖에 정의하지 못한다.

예를 들어, Pair 클래스에 대해 Pair.second를 우선으로 정렬하고 그 다음 기준을 Pair.first라고 했을 때,

Pair.first를 우선적으로 하는 정렬은 어떻게 하는가? 라는 의문점이 남는다.

**(물론 람다식 정렬은 이렇게 여러 정렬 조건을 사용할 때만 사용하는 것이 아니라 그냥 바로도 사용 가능하지만, 특이 케이스를 함께 서술하기 위해 작성함)**

![image](https://user-images.githubusercontent.com/93081720/211842287-c702d861-7689-4e41-adb6-fca3c298cf36.png)

이 때, 사용 가능한 것이 정렬을 할 때, 정렬 조건을 선언하여 정렬하는 것이다.

<br>

## 익명 클래스를 활용한 정렬

![image](https://user-images.githubusercontent.com/93081720/211843649-f4e4279e-35ad-4511-ad6b-7d4bf71ec9ba.png)

<br>

## 람다식을 활용한 정렬

람다식은 `(매개변수, ...) -> {실행문...}`와 같은 형식으로 작성한다.

![image](https://user-images.githubusercontent.com/93081720/211843876-f85e5e54-b49e-4b6c-977e-55c8c8191c90.png)

- 람다식을 활용한 우선순위 큐 정렬 조건 정의

![image](https://user-images.githubusercontent.com/93081720/211844052-b2f3cac5-4f9b-4e7e-96dc-9c5c92453aff.png)

※ `Integer.compare(x, y)`

- x > y이면 양수를 return
- x == y이면 0을 return
- x < y이면 음수를 return