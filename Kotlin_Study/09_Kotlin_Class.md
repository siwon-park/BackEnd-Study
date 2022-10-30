# 09_코틀린 클래스

※ 클래스도 함수와 마찬가지로 pulic이 default이다.

## 01_클래스와 프로퍼티

※ 프로퍼티(property) : 필드(field) + 메서드(method); getter, setter

생성자가 클래스와 같은 라인에 위치한다.(생략 가능)

| v1                                                           | v2                                                           | v3                                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/198882330-48446656-a72b-46c7-958a-0068b8e2f76c.png) | ![image](https://user-images.githubusercontent.com/93081720/198882397-4c21b0a0-bacd-4cd5-8f2e-904c5b733825.png) | ![image](https://user-images.githubusercontent.com/93081720/198882466-3c969c2f-799b-45a6-beae-87416c4ca8a5.png) |

코틀린에서는 필드만 만들면 getter, setter를 자동으로 만들어준다. => `.필드명`으로 getter, setter를 사용할 수 있다.(자바 클래스를 가져와도 마찬가지)

![image](https://user-images.githubusercontent.com/93081720/198882634-5bd683c5-f193-42ba-98a5-30775ca25430.png)

<br>

## 02_생성자(constructor)와  init

### 주 생성자(primary constructor)

주 생성자는 반드시 존재해야한다.

![image](https://user-images.githubusercontent.com/93081720/198882466-3c969c2f-799b-45a6-beae-87416c4ca8a5.png)

클래스에 매개 변수가 없을 경우, 주 생성자는 따로 생성해주지 않아도 된다.

| 예시                                                         | 사용                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/198882935-4fa9f062-24ac-487c-81e6-a1e62e10275f.png) | ![image](https://user-images.githubusercontent.com/93081720/198882903-8d32aeb8-688b-4379-9bb0-cbb429ab4e1d.png) |



### 부 생성자(secondary constructor)

부 생성자는 있을 수도 있고 없을 수도 있다. => `constructor`키워드를 통해 생성

body를 가질 수 있으며,`this`키워드를 통해 부 생성자는 다른 부 생성자를 호출할 수도 있고, 최종적으로는 주 생성자를 호출해야한다.

![image](https://user-images.githubusercontent.com/93081720/198883213-2f368e9a-25b1-4bc8-bd7f-56be3bc1374d.png)

![image](https://user-images.githubusercontent.com/93081720/198883227-1902f9a0-77f9-400f-b371-5c0157be63bf.png)

![image](https://user-images.githubusercontent.com/93081720/198883248-2393b45e-c579-4c7d-b339-c16c2ee6e1d1.png)

※ `Person()`을 클릭하여 선언부로 이동하면 `두 번째 부 생성자`로 이동하고, 두 번째 부 생성자에서 `this`를 클릭하면 `첫 번째 부 생성자`로 이동하고, 첫 번째 부 생성자에서 `this`를 클릭하면 `주 생성자`로 이동한다.

#### 결론

부 생성자를 생성해서 사용하는 것보다 다음과 같이 default parameter를 사용하는 것이 간결하다.

![image](https://user-images.githubusercontent.com/93081720/198883418-f4327ad2-dc4b-40b6-97ac-d96e7e517247.png)

어쩔 수 없이 부 생성자를 사용해야하는 경우에는 `정적 팩토리 메서드`를 사용하는 것을 권장함



### init

초기화; 생성자가 호출되는 시점에 호출 => 생성자에서 검증 로직을 만들 수 있게 해줌

![image](https://user-images.githubusercontent.com/93081720/198883055-3b26e6d0-8758-4ab7-a7c5-23e4e177ebc1.png)



<br>

## 03_커스텀 getter, setter

### getter

| 함수형 gettter                                               | 필드형 gettter v1                                            | v2                                                           | v3                                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://user-images.githubusercontent.com/93081720/198883543-485b8c57-124b-4713-b4b0-84dced6533b4.png) | ![image](https://user-images.githubusercontent.com/93081720/198883586-cb76aa88-751d-4334-94d9-3da128069630.png) | ![image](https://user-images.githubusercontent.com/93081720/198883715-40908e20-6305-4c78-ad33-6f8c92c1e1a3.png) | ![image](https://user-images.githubusercontent.com/93081720/198883682-82aabc4c-17bc-43d4-bd26-5b7527d0a5ee.png) |

객체의 속성이라면 필드형으로 custom getter를 작성하고, 아니라면 함수형으로 작성하는 것을 추천

### setter

![image](https://user-images.githubusercontent.com/93081720/198884533-9215fe9b-8b40-4cb9-b4a3-79c9e4896748.png)

<br>

## 04_backing field

`field`를 backing field라고 부른다.

※ 매개 변수에 `var`, `val` 키워드를 붙이면 기본적으로 getter와 setter가 만들어진다. => 커스텀 getter를 만들기 위해 val 키워드를 삭제함

![image](https://user-images.githubusercontent.com/93081720/198883961-3731dee5-3dfc-4048-a016-e20b1c4aedb0.png)

여기서 `field`자리에 name을 쓸 경우 name의 getter가 name을 참조하므로 무한 루프에 빠지게 된다. 따라서 `field` 키워드를 사용해야 한다.

단, 실제 backing field를 사용하는 것은 드물고, 다음과 같이 다른 방법으로 `field` 대신 사용 가능하다.

- this.필드

![image](https://user-images.githubusercontent.com/93081720/198884113-38e774e5-c1a9-4c80-9e47-2b4f366e162e.png)

![image](https://user-images.githubusercontent.com/93081720/198884350-11170d8e-2a10-4182-b93d-ed283d434ed0.png)

- 함수형으로 작성

![image](https://user-images.githubusercontent.com/93081720/198884281-429b1673-f7b3-430a-9a62-af69bcf6f965.png)