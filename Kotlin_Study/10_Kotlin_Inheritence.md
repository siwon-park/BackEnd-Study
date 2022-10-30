# 10_코틀린 상속

`:`을 통해 상속할 수 있음

단, 타입을 지정할 때와 다른 점은 타입을 지정할 때는 변수 바로 옆에 붙여줘야 하지만, 상속 시에는 클래스에서 한 칸 공백으로 띄운 다음에 `:`을 붙여준다.

![image](https://user-images.githubusercontent.com/93081720/198889326-f229c9a3-ea19-4e0c-b7fc-ecdccaa8bd1e.png)

## 01_추상 클래스

자바와 코틀린 모두 추상 클래스는 인스턴스화 할 수 없다

추상 멤버가 아니면 기본적으로 오버라이드가 불가능하다 => `open`키워드를 사용하여 오버라이드를 가능하게 만들 수 있다.

- 예시) legCount는 추상 멤버가 아니므로 상속을 받고자 한다면 `open`을 붙여줘야 한다.

![image](https://user-images.githubusercontent.com/93081720/198888568-9ec7cbbe-4b27-401e-855f-ac51340fad55.png)

![image](https://user-images.githubusercontent.com/93081720/198888586-c3d43288-9db9-43e7-b9bf-29be46e87d32.png)

<br>

## 02_인터페이스

※ 인터페이스 역시 기본적으로 public이 붙어 있으며, 이는 생략 가능하다.

인터페이스의 구현 역시 `:`을 사용하므로 상속과 똑같이 사용해주면 된다. 여러 개를 구현할 경우 `,`(콤마)로 구분할 수 있다.

자바와 코틀린 모두 인터페이스를 인스턴스화 할 수 없다.

![image](https://user-images.githubusercontent.com/93081720/198888713-f703451b-481d-493f-b7b1-0c0a3d4feb04.png)

<br>

## 03_클래스 상속 시 주의 점

상위 클래스 상속을 구현할 때 생성자를 반드시 호출해야 한다.

상위 클래스를 설계할 때 생성자 또는 초기화 블록에 사용되는 프로퍼티에는 `open`키워드의 사용을 피해야한다

![image](https://user-images.githubusercontent.com/93081720/198888852-42289454-24b1-435f-bb2c-e19eee23294c.png)

- 실행 결과:

![image](https://user-images.githubusercontent.com/93081720/198888879-7b4f5c5a-73b7-4d1a-91a0-422fe1c86b36.png)

- number가 0이 나온 이유?

number에 300을 넣어서 Derived 클래스를 인스턴스화 했지만, 상위 클래스의 init이 먼저 호출되면서 number를 호출하면 하위 클래스의 number를 가져오게 되는데, 이 시점에는 하위 클래스의 number는 300으로 초기화가 이루어지지 않은 시점이기 때문에 Int의 기본값인 0이 출력되는 것임

### 결론

상위 클래스에 constructor와 init 블록에서는 하위 클래스의 field에 접근하면 안 됨 => 하위 클래스에서 override하고 있는 프로퍼티에 접근하면 안 됨

<br>

## 04_상속 관련 지시어

- `final`: override를 할 수 없게 한다. default 값이므로 보이지 않음
- `open`: override를 열어준다.
- `abstract`: 반드시 override를 해야한다.
- `override`: 상위 타입을 오버라이드하고 있다.