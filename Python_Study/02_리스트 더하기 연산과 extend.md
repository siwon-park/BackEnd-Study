# 02_리스트 더하기 연산과 extend

리스트에서 더하기 연산을 사용할 때 주의할 점과 extend 메서드

## 리스트 더하기(+) 연산

### 기존 리스트에 더하기 연산을 할 경우

더하기 전과 더한 후의 `주소값이 변경`되었음을 알 수 있다.

그 이유는 기존 리스트에 값을 더한 새로운 리스트를 생성하고 이 새로운 리스트의 주소를 할당했기 때문이다.

즉, 예시에서 `lst1 + [4, 5]`가 새로운 리스트를 생성한 것이다. 그리고 이 새로운 리스트의 주소를 lst1에 할당했기 때문에 `주소가 바뀌는 것`이다.

![image](https://user-images.githubusercontent.com/93081720/236688757-4b9db1bc-1ce4-4a0c-af81-b4b7c5be97a7.png)

![image](https://user-images.githubusercontent.com/93081720/236688775-283c89c9-05ec-4876-bd7e-b0cf15a55001.png)

<br>

### 축약 연산자로 더하기 연산을 할 경우

더하기 전과 더한 후의 `주소값이 동일`하다.

그 이유는 똑같이 더하기 연산이지만, 축약 연산자를 통해서 계산하는 것은 기존의 리스트에 값만 덧붙인 `추가`의 개념이기 때문이다. 따라서 `lst1에 [4, 5]를 그대로 이어 붙인 것`이기 때문에 `주소값의 변경이 없는 것`이다.

![image](https://user-images.githubusercontent.com/93081720/236688963-dd6fe5c2-bff5-4d92-a6fc-338ffe498e25.png)

![image](https://user-images.githubusercontent.com/93081720/236688977-412dc935-9189-4cf1-bdba-a1c4002b3b87.png)

<br>

## extend 메서드

`list.extend(_list)`

`.extend()` 메서드를 사용하는 것도 `+=` 축약 연산자를 사용한 것과 마찬가지로 주소값이 변경되지 않는다.

![image](https://user-images.githubusercontent.com/93081720/236689293-5f9db729-2540-49e8-8ae5-022ba676e0ef.png)

![image](https://user-images.githubusercontent.com/93081720/236689309-393f4663-fdae-42fb-a83f-d11185734a41.png)

<br>

## 결론

어떤 것을 쓰는 것이 더 효율적일까?

`.extend()`메서드를 사용하는 것이 메모리, 속도면에서 더 효율적이기 때문에 리스트에 리스트를 추가하고자 할 때는 `.extend()`메서드를 사용하자.
