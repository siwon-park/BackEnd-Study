# 07_문자열 포멧팅

| 코드 | 설명            |
| ---- | --------------- |
| %s   | 문자열(String)  |
| %c   | 문자 1개(char)  |
| %d   | 정수(Integer)   |
| %f   | 부동소수(float) |
| %%   | %문자 자체      |
| %o   | 8진수           |
| %x   | 16진수          |

<br>

## 1. String.format()

문자열 포멧 코드(`String.format("포멧팅 문자열", "문자1", 숫자, ...)`)

![image](https://user-images.githubusercontent.com/93081720/226152592-ced7c4fd-7b5b-48d8-a666-90db82f4f3cb.png)

<br>

## 2. printf()

`Sting.format()`을 출력한 것과 동일한 기능을 함.

`println`()과 `tring.format()`을 같이 써도 되지만, 중복이므로 굳이 `printf()`를 대신하여 사용할 이유가 없다.

![image](https://user-images.githubusercontent.com/93081720/226152419-59e4f014-7b6f-4a43-a1e1-05de03960410.png)

![image](https://user-images.githubusercontent.com/93081720/226152450-98f33981-1ebc-4274-b9ba-f57ab5e23304.png)

<br>

## 3. 소숫점 표현

보통 일반적으로 소숫점 n째 자리까지 표현하고자 할 때, 문자열 포멧팅을 주로 사용한다.

소숫점을 표현하는 방법은 크게 `문자열 포멧팅`과 `Math.round()`를 사용하는 방법이 있다.

### Math.round()

`Math.round()`는 기본적으로 소숫점 첫째 자리를 기준으로 반올림한다. 이를 이용해서 원하는 자리까지 소숫점을 표현할 수 있다.

- 예1) 소숫점 둘째 자리까지 표현 => 100을 곱한 것을 Math.round한 다음 100으로 나눠줌
  - 3.141529 => 314.1529 => Math.round() => 314 => 나누기 100 => 3.14
- 예2) 소숫점 넷째 자리까지 표현 => 10000을 곱한 것을 Math.round한 다음 10000으로 나눠줌
  - 3.141529 => 31415.29 => Math.round() => 31415 => 나누기 10000 => 3.1415

![image](https://user-images.githubusercontent.com/93081720/226152884-5bc3342d-5f0b-4aed-8f47-f4595e6e659b.png)

<br>

### String.format("%.nf")

`%.nf`(n에 표현하고 싶은 자리수를 넣음)로 `String.format()`을 사용해 원하는 만큼 소숫점 자리수를 표현할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/226153000-503ed322-7d9c-4ece-9f10-125ae96c8fbb.png)