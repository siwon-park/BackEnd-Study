# 06_Stream

Java는 `객체 지향 언어(OOP; Object Oriented Programming)`이기 때문에 기본적으로 `함수형 프로그래밍(functional programming)`이 불가능하다.

<br>

## 1. Stream API

하지만 JDK8부터 `Stream API`를 통해 함수형 인터페이스를 지원하면서 Java를 이용해서 함수형 프로그래밍을 할 수 있는 API를 제공해주고 있다.

`Stream API`는 `데이터를 추상화하고, 처리하는데 자주 사용되는 함수들`을 정의해두었다.

- 데이터를 추상화했다는 것은 데이터 종류에 상관 없이 같은 방식으로 데이터를 처리할 수 있다는 것을 의미한다. 그에 따라 재사용성을 높일 수 있다.
- Stream이란 '데이터 처리 연산을 지원하도록 소스에서 추출된 연속된 요소'로 볼 수 있다.

### Stream의 필요성



<br>

### Stream API의 장점

- 선언형 - 보다 간결한 코딩이 가능해지므로 가독성이 향상된다.
- 함수의 조립 - 유연성이 향상된다.
- 병렬화 - 별도의 멀티 스레드 구현 없이 병렬 처리가 가능하다.

<br>

### Stream API의 특징

- 원본 데이터를 변경하지 않는다.
  - 원본 데이터를 조회하여, 별도의 요소들로 stream을 생성한다.
  - 그래서 원본 데이터로 부터 `읽기만` 할 뿐이며(`READ ONLY`), 정렬이나 필터링 등의 작업은 다른 stream 요소들에 의해 처리가 된다.
- 일회용이다.
  - 한 번 사용이 끝나면 재사용이 불가능하다. 따라서 stream이 또 필요한 경우에는 stream을 다시 생성해주어야 한다. 
  - 만약 닫힌 stream을 다시 사용한다면, `IllegalStateException`이 발생한다.
- 내부 반복으로 작업을 처리한다.
  - 반복 문법을 메서드 내부에 숨겨놓았기 때문에 보다 간결한 코드 작성이 가능하다.

<br>

### 컬렉션과의 비교

| 구분             | 컬렉션                                       | Stream API                                    |
| ---------------- | -------------------------------------------- | --------------------------------------------- |
| 데이터 계산 시점 | 컬렉션에 추가되기 전에 계산이 완료 되어야 함 | 요청이 있을 때만 요소를 계산함                |
| 반복 가능 유무   | 같은 소스에 대해 여러 번 반복 가능           | 하나의 스트림에 대해서는 단 한 번만 사용 가능 |
| 반복 형태        | 외부 반복                                    | 내부 반복                                     |

<br>

## 2. Stream API의 연산

### Stream API 연산 과정

Stream API는 크게 `생성하기` → `가공하기` → `결과 만들기`로 이루어져있다.

![image](https://user-images.githubusercontent.com/93081720/224634462-bea8f009-037c-4196-812d-17de6a3c9360.png)

#### 1. 생성하기

`.stream()`을 통해 stream 객체를 생성

#### 2. 가공하기

원본 데이터를 별도의 데이터로 가공하기 위한 중간 연산 과정

연산 결과를 Stream 객체로 반환하기 때문에 연속적으로 중간 연산을 이어갈 수 있다.

이렇게 중간 연산들을 계속 이어서 나가는 것을 `파이프라이닝(pipelining)`이라고 한다.

최종 연산을 실행해야만 중간 연산에 대한 결과물이 만들어지는 것이다.

#### 3. 결과 만들기

가공된 데이터로부터 원하는 결과를 만들기 위한 최종 연산으로 마지막 호출 메서드에서 세미콜론(`;`)을 찍으면 된다.

<br>

### Stream 생성하기

#### Collection

Collection 인터페이스를 구현한 객체들을 대상으로 `.stream()` 메서드를 사용하면, stream 객체를 생성할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/224643379-e29828ff-e09d-4df7-be10-e94e0fa7344a.png)

#### Array(배열)

Stream의 `.of()`메서드나 Arrays의 `.stream()` 메서드를 사용하면 된다.

![image](https://user-images.githubusercontent.com/93081720/224643226-4ef68a4d-4b8c-4a59-9d82-cad87d24d7ee.png)

<br>

### Stream 가공하기(중간 연산)

#### 필터링(filter)

조건에 맞는 데이터만을 정제하여 새로운 컬렉션을 만들어내는 연산

![image](https://user-images.githubusercontent.com/93081720/224898845-e6d90329-1d23-48cb-9d60-782b0ff9d22e.png)



#### 변환(map)

지정한 값들을 특정 함수를 적용하여 변환하고자 할 때 사용

![image](https://user-images.githubusercontent.com/93081720/224899926-48b99f5b-9b5d-47c7-99a0-b42dc1c42cc9.png)



#### 정렬(sorted)

- 순차 정렬

![image](https://user-images.githubusercontent.com/93081720/224901263-6779fb0d-8910-4c2c-b9c6-59c915dafe3c.png)

- 역순 정렬

![image](https://user-images.githubusercontent.com/93081720/224901497-5f99eb09-ab40-48bb-808e-ca97deed3220.png)



#### 중복 제거(distinct)

중복을 제거한다.

단, 직접 생성한 class의 경우 `equals`와 `hashCode`를 Override하여야만 원하는 대로 동작한다.

![image](https://user-images.githubusercontent.com/93081720/224902103-a380bee3-f9a5-4354-9ae9-70031e134559.png)



#### 특정 연산 수행(peek)

주어진 특정 연산을 수행하되, 결과에 아무 영향도 끼치지 않음

아래 예시에서 `.peek()`를 통해 stream을 출력하긴 하지만, sum에 대한 결과에 어떠한 영향도 끼치지 않는다.

![image](https://user-images.githubusercontent.com/93081720/224907154-88f64b99-7502-40f2-8b5f-7827657fbdcb.png)

<br>

### Stream 결과 만들기(최종 연산)

#### 최댓값(max), 최솟값(min), 합계(sum), 평균(average), 집계(count)

stream에 대한 연산을 최댓값, 최솟값, 합계, 평균, 집계로 구하기 위해서 존재하는 최종 연산 메서드

`.max()`, `.min()`, `.sum()`, `.average()`, `.count()`

- sum이나 count의 경우 stream이 비어있으면 결과로 `0`이 반환된다.



#### 데이터 수집(collect)

`.collect()` 메서드 안에서 `Collector`인터페이스나 `Collectors` 클래스를 통해 결과를 수집할 수 있다. 이때, `Collector` 인터페이스는 구현을 해줘야 한다.

![image](https://user-images.githubusercontent.com/93081720/224932627-e9afd6c5-0e5c-493f-b45d-cf6d5a06ca76.png)

위는 다음과 같이 `.toList()`로 간단하게 만들 수도 있다.

![image](https://user-images.githubusercontent.com/93081720/224932703-e6a0e8f6-2ccd-4fa9-ab87-2e65f42da0bf.png)



#### 매칭(match)

- `anyMatch` : 1개의 요소라도 해당 조건을 만족하는가
- `allMatch` : 모든 요소가 해당 조건을 만족하는가
- `nonMatch` : 모든 요소가 해당 조건을 만족하지 않는가
