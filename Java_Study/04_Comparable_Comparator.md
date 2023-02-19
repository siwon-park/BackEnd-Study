# 04_Comparable_Comparator

비교에 대한 기준을 정의하는 함수형 인터페이스

## 1. 자바 정렬의 핵심

> 자바에서 정렬은 특별한 정의가 되어있지 않는 한 '**오름차순**'을 기준으로 한다.
>
> 대소비교, 연산 결과를 통한 양수, 음수, 0 반환 결과를 기준으로 정렬 작동 방식이 달라진다.

- **음수**일 경우: 두 원소의 위치를 **교환 안 함**
- **양수**일 경우: 두 원소의 위치를 **교환 함**

기본적으로 '오름차순'을 기준으로 하므로

- 앞에 있는 원소에서 뒤에 있는 원소를 뺐을 때 양수가 나오면 그 위치를 교환하는 것이고
  - 대소 비교: 앞에 있는 원소 `>` 뒤에 있는 원소
- 앞에 있는 원소에서 뒤에 있는 원소를 뺐을 때 음수가 나오면 그 위치를 교환하지 않는 것이다.
  - 대소 비교: 앞에 있는 원소 `<` 뒤에 있는 원소

![image](https://user-images.githubusercontent.com/93081720/208082518-a05adfc8-8e49-4ca2-bcc2-db134a0ad0ba.png)

※ 꼭 -1, 0, 1일 필요는 없다. 음수, 양수, 0이면 된다.

### ※ 주의점

위의 예시처럼 대소비교가 아니라 다음과 같이 연산에 대한 반환값으로도 표현이 가능한데,  이렇게 표현할 때 반드시 주의해야할 것이 있는 게 바로 자료형의 범위를 넘어 버리는 **언더 플로우(UnderFlow)**와 **오버 플로우(OverFlow)**이다.

![image](https://user-images.githubusercontent.com/93081720/208083971-87c78c4b-93aa-40a1-8503-7ea1553348d9.png)

따라서 연산 방식으로 구현할 경우, 언더 플로우나 오버 플로우가 발생할 여지가 있는지를 반드시 먼저 확인한 다음에 사용해야 한다. 만약에 불확실하다면, `>`, `<`, `==`로 대소비교를 해주는 것이 안전하면서도 일반적으로 권장되는 방식이다.

<br>

## 2. Comparable

> **compareTo** 메서드를 반드시 구현해야한다.
>
> 자기 자신과 타입이 동일한 매개변수를 비교한다. (**자기 자신과 비교**)

### 사용법

- 다음과 같이 Comparable 인터페이스를 구현하여 사용한다.
  - 만약 Comparable을 구현하지 않고, 정렬 메서드를 호출하면 정렬 기준이 없기 때문에 예외가 발생한다.

![image](https://user-images.githubusercontent.com/93081720/208085552-c2fb9fcd-9b9a-4d12-9b98-a2339aadc5ec.png)

- 반드시 `compareTo`메서드를 오버라이드하여 구현한다. 이 때, 매개변수는 1개이다.

![image](https://user-images.githubusercontent.com/93081720/208085702-782d20b7-8601-43eb-ad17-f31a173d7b02.png)

- (선택) 테스트를 위해 toString 메서드도 오버라이딩한다.

![image](https://user-images.githubusercontent.com/93081720/208085950-d29b97e6-f57b-4857-93b3-bde7a7534951.png)

### 예시

객체의 타입에 따라 `Arrays.sort()`나 `Collections.sort()`를 사용하여 정렬한다.

![image](https://user-images.githubusercontent.com/93081720/208086160-ce40e1c5-d693-498c-a93a-b583db1bc794.png)

<br>

## 3. Comparator

>**compare** 메서드를 반드시 구현해야한다.
>
>타입이 동일한 서로 다른 두 매개 변수를 비교한다. (**서로 비교**)

### 사용법 1 (Comparator 직접 구현)

- 기본적으로는 다음과 같이 Comparator인터페이스를 구현하여 사용한다.

![image](https://user-images.githubusercontent.com/93081720/208086818-6f69fe37-f22d-4c3b-bd4a-bf72f0eb87e7.png)

- 반드시 `compare`메서드를 오버라이드하여 구현한다. 이 때, 매개 변수는 2개이다.

![image](https://user-images.githubusercontent.com/93081720/208087068-178431cc-1d52-4fcb-9c3d-15ecfdd434d0.png)

### 예시

- 이렇게 사용할 경우, 객체를 생성하고, 객체를 통해 메서드를 호출해야한다.
  - 예) `pair1.compare(pair1, pair2)`, `pair2.compare(pair2, pair3)`
- 그런데 일관성이 떨어지므로, 다음과 같이 비교만을 위한 객체를 생성해서 일관성있게 사용하기도 한다.
  - 일관성을 유지하기 위해 굳이 비교 용도 외에는 쓸모 없는 객체를 생성하는 것은 자원 측면에서 낭비다.

![image](https://user-images.githubusercontent.com/93081720/208088348-1267c8c6-2afc-40ec-9359-3012d2d428f1.png)

<br>

### 사용법 2(익명 객체 생성을 통한 Comparator 기능 분리)

- 직접 Comparator를 클래스 레벨에서 구현하지 않고, 클래스만 선언

![image](https://user-images.githubusercontent.com/93081720/208089474-52ab1280-b539-4434-8710-77d5cb85667b.png)

- **익명 객체**를 생성하고, `compare` 메서드를 오버라이딩한다.
  - 익명 객체 생성 시, 중괄호(`{}`) 끝에 세미 콜론(`;`)을 반드시 꼭 찍어주자

![image](https://user-images.githubusercontent.com/93081720/208089740-7085faab-6621-4526-b1cd-a30240ff3617.png)

### 예시

- `comp.compare(pair1, pair2)`와 같이 사용해도 되고, sort()안에 정렬 기준으로 적용 가능하다.

![image](https://user-images.githubusercontent.com/93081720/208090437-4974ec26-02c1-49c2-985d-9efb43a0f371.png)

### 결론

익명 객체를 사용하여 비교 기준을 정의하는 것이 보다 바람직하다. 그 이유는

- 코드의 일관성을 유지할 수 있다.
- **비교 기준(정렬 기준)이 다른 익명 객체를 여러 개 생성하여 사용 가능**하다.

<br>

