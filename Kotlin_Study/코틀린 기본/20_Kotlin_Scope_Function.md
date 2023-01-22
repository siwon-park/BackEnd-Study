# 20_코틀린 scope function

## 01_scope function이란 무엇인가?

scope: 영역

function: 함수

=> scope function: 일시적인 영역을 형성하는 함수

람다를 사용해서 일시적인 영역을 만들어 코드를 더 간결하게 만들거나, method chaning에 활용하는 함수를 scope function이라고 한다.

<br>

## 02_scope function의 분류

### let

람다의 결과를 반환

람다 안에서 `it`을 사용

### run

람다의 결과를 반환

람다 안에서 `this`를 사용

### also

(람다의 결과가 어찌됐든) 객체 그 자체를 반환

람다 안에서 `it`을 사용

### apply

(람다의 결과가 어찌됐든) 객체 그 자체를 반환

람다 안에서 `this`를 사용



### ※ this와 it의 차이

#### this

생략이 가능한 대신, 다른 이름을 붙일 수 없다

확장 함수에서는 본인 자신을 this로 호출하고, 생략할 수 있다. 즉, run과 apply는 내부에서 확장 함수를 통해서 호출하고 있는 구조

#### it

생략이 불가능한 대신, 다른 이름을 붙일 수 있다.

let과 apply는 내부에서 일반 함수를 통해서 호출하고 있는 구조



### with

`with(파라미터, 람다)` 형태로 사용

<br>

## 03_언제, 어떤 scope function을 사용해야 할까?

### let

- 하나 이상의 함수를 call chain 결과로 호출할 때
- non-null 값에 대해서만 code block을 실행시킬 때
- 일회성으로 제한된 영역에 지역 변수를 만들 때

### run

- 객체 초기화와 반환값을 계산을 동시에 해야할 때
  - 객체를 만들어 DB에 바로 저장하고, 그 인스턴스를 활용해야할 때

![image](https://user-images.githubusercontent.com/93081720/199772265-cdae3c4d-2252-4887-92c8-5ea4b1e89677.png)

![image](https://user-images.githubusercontent.com/93081720/199772470-7e72436e-a971-46ca-8f1c-7f3d0ba6ec39.png)

=> Person 객체의 hobby 필드에 "독서"를 넣은 다음에 repository에 저장한 다음에 person 인스턴스를 사용

```kotlin
val person = personRepositoy.save(Person("박시원", 30))
```

### also

- 객체 설정을 할 때, 객체를 수정하는 로직이 call chain의 중간에 필요할 때 사용

### apply

- 객체 설정을 할 때, 객체를 수정하는 로직이 call chain의 중간에 필요할 때 사용

### with

- 특정 객체를 다른 객체로 변환해야 하는데, 모듈 간의 의존성에 의해 정적 팩토리 혹은 toClass 함수를 만들기 어려울 때 사용

```kotlin
return with(person) {
    PersonDto(
    name = name,
    age = age
    )
}
```

=> this를 생략할 수 있어 필드가 많아져도 코드가 간결해지는 장점이 있음

<br>

## 04_scope function과 가독성

동일한 동작을 하는 두 코드가 있을 때,

1번 코드가 더 가독성이 좋음

2번 코드는 숙련된 코틀린 개발자만 더 알아보기 쉽다.

또한 그래서 **1번 코드가 디버깅이 더 쉽고, 수정도 쉽다**

```kotlin
// 일반적인 if-else 구문 코드
if (person != null && person.isAdult) {
    view.showPerson(person)
} else {
    view.showError()
}

// takeIf와 let을 사용한 코틀린스러운 코드
person?.takeIf { it.isAdult }
	?.let(view::showPerson) // view.showPerson이 null을 반환한다면, showError도 불리는 버그가 발생할 가능성 존재
	?: view.showError()
```

=> 사용 빈도가 적은 관용구는 코드를 더 복잡하게 만들고, 이런 관용구들을 한 문장 내에서 조합해서 사용한다면 코드의 복잡성이 훨씬 증가한다. 그래서 scope function을 쓴다고 해서 가독성이 더 좋아지는 것은 아님.

scope function도 결국 사용하라고 만든 것이기 때문에 팀 내에서 적절한 convention과 함께 사용한다면 유용하게 활용 가능
