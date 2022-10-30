# 12_코틀린 Object 키워드

## 01_static 함수, 변수

### companion object

코틀린에서는 `static`키워드가 없다. 대신에, `companion object`라는 키워드를 사용해서 정적 함수와 변수를 만들 수 있다.

![image](https://user-images.githubusercontent.com/93081720/198891447-fec81c09-8aa9-4e8a-a827-1eb2d1567ccb.png)

※ companion object는 의미 그대로 동행 객체이므로 이름을 붙일 수도 있고, 객체에 대한 인터페이스를 구현할 수도 있음

<br>

## 02_싱글톤

단 하나의 인스턴스만 갖는 클래스

자바에서는 static 영역에 인스턴스를 만들고 이미 만들어진 인스턴스를 가져오는 방식으로 싱글톤을 구현함(인스턴스의 재생산을 방지)

코틀린에서는 다음과 같이 매우 간단하게 싱글톤을 구현할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/198891604-7a398b01-c906-4a2f-a299-6b57cf5cd6bb.png)

![image](https://user-images.githubusercontent.com/93081720/198891629-48b2907d-c15e-4295-b1de-cbcbe17de1fa.png)

<br>

## 03_익명 클래스

특정 인터페이스나 클래스를 상속받은 구현체를 일회성으로 사용할 때 쓰는 클래스

자바에서는 `new 타입명`을 사용하여 구현하였다면, 코틀린에서는 `object : 타입명`으로 구현함

![image](https://user-images.githubusercontent.com/93081720/198891997-f275d6c2-13cf-4e9a-9367-06629ff4cc31.png)