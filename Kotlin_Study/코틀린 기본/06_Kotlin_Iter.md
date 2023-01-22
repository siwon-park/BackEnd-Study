# 06_코틀린 반복문

## 01_for-each문

자바

![image](https://user-images.githubusercontent.com/93081720/198841440-8bcf7930-e40f-46e0-8849-02128ab57b2f.png)

코틀린

![image](https://user-images.githubusercontent.com/93081720/198841462-bca32b73-a18e-4a0c-8068-b7684afe1d5d.png)

<br>

## 02_일반적인 for문

자바

![image](https://user-images.githubusercontent.com/93081720/198841532-bf671f04-a95b-4407-aec6-1df9b5853d89.png)

코틀린

`a..b`로 표현(단, a <= x <= b의 범위를 가진다)

![image](https://user-images.githubusercontent.com/93081720/198841543-00f28f8b-5ea4-4e43-999c-a3c5519f5efe.png)

- 내려가는 경우 => `downTo`

![image](https://user-images.githubusercontent.com/93081720/198841630-3a17e219-788c-4c68-9c42-0697cf0ed935.png)

- 건너뛰는 경우 => `step`

![image](https://user-images.githubusercontent.com/93081720/198841673-fd7bdc1e-7f0a-490e-9a57-fe7937836632.png)

<br>

## 03_Progression과 Range

위 처럼 `..`을 서서 반복문이 가능한 이유?

### Progression(등차수열)

시작값, 끝값, 공차

### Range(범위)

a부터 b까지의 범위



=> `..`연산자가 Range클래스를 의미하며, Range 클래스가 Progression 클래스를 상속받아서 범위에 해당하는 등차수열을 만드는 구조

<br>

## 04_while문

자바와 완전히 동일함

![image](https://user-images.githubusercontent.com/93081720/198842014-40551ba7-d414-4113-a461-39ddc0621be5.png)