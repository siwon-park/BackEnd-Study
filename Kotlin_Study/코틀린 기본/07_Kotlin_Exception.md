# 07_코틀린 예외 처리

try-catch도 if-else처럼 return을 한 번만 쓸 수도 있다.

- 자바

![image](https://user-images.githubusercontent.com/93081720/198877668-5af7423c-a1e7-4746-b8f7-7222425a92a5.png)

- 코틀린

![image](https://user-images.githubusercontent.com/93081720/198877644-88a83b9e-d2ea-46d6-ab65-bc59da15f997.png)

<br>

## 02_Check Exception & Unchecked Exception

코틀린에서는 Check Exception과 Unchecked Exception을 구분하지 않는다. => **모두 Unchecked Exception**이다.

따라서 throws 키워드를 쓸 필요가 없다

- 자바 => BufferedReader가 발생시킬 IOException에 대해 명시해줘야함

![image](https://user-images.githubusercontent.com/93081720/198878087-cf90ad78-7f13-4bfb-90ee-548f541a2b98.png)

- 코틀린

![image](https://user-images.githubusercontent.com/93081720/198878105-ee375bc9-1c0d-44bc-9cce-3b07baedcb8f.png)

<br>

## 03_try with resources구문

코틀린에서는 try with resource 구문이 없다 => use라는 inline 확장 함수를 사용한다.

- 자바의 try with resource 구문(JDK7버전 부터 추가)

![image](https://user-images.githubusercontent.com/93081720/198877312-ad14d6b6-58ea-42a6-8555-437c87a1ef22.png)

- 코틀린

![image](https://user-images.githubusercontent.com/93081720/198877343-25055d52-b225-4f74-9174-b2102e8e01ca.png)