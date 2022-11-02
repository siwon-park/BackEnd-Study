# 18_코틀린에서 컬렉션을 함수형으로 다루는 법

## 01_필터와 맵

![image](https://user-images.githubusercontent.com/93081720/199526196-868cf600-0e27-49b4-9ef8-d80dd390147d.png)

### filter

조건부 필터링

![image](https://user-images.githubusercontent.com/93081720/199524639-c282a8f9-ee8b-4920-8ee1-b8dd20d88e34.png)

### filterIndexed

조건부 필터링 + 인덱스

![image](https://user-images.githubusercontent.com/93081720/199524714-edf38c27-164f-4407-b202-8c97db13c07a.png)

### map

필터링 + 맵핑

![image](https://user-images.githubusercontent.com/93081720/199524789-e5e9adc5-efdf-478f-b839-2e8932a24b5d.png)

### mapIndexed 

필터링 + 맵핑 + 인덱스

![image](https://user-images.githubusercontent.com/93081720/199525265-19ba1347-bb99-4363-bbce-4233d137dba7.png)

### mapNotNull

필터링 + 맵핑 + notnull

![image](https://user-images.githubusercontent.com/93081720/199525361-e942cf5f-42da-4437-b996-905764c0b57b.png)

※ 여기서 nullOrValue는 커스텀 함수

<br>

## 02_다양한 컬렉션 처리 기능

![image](https://user-images.githubusercontent.com/93081720/199526357-445693f1-9700-4bd3-bffb-8c6ccfcbd682.png)

### all

컬렉션 안에 있는 데이터가 조건부에서 지정한 조건과 `모두 일치`하는지 확인

![image](https://user-images.githubusercontent.com/93081720/199526437-376c913d-2fb7-445f-8ba6-42f3287e3f65.png)

### any

조건을 `하나라도 만족`하면 true, 그렇지 않으면 false

![image](https://user-images.githubusercontent.com/93081720/199527083-c62a1f4f-ac89-4200-a75d-343cc990043e.png)

### none

주어진 조건을 `모두 불만족`하면 true, 그렇지 않으면 false

![image](https://user-images.githubusercontent.com/93081720/199526761-199dfa04-41f0-4029-a3d7-17752636f1ae.png)

### count

컬렉션의 개수를 리턴함. 리스트에서 size와 비슷

![image](https://user-images.githubusercontent.com/93081720/199527481-85409492-3978-4ddc-9271-9fd8317e2009.png)

### sortedBy

오름차순 정렬

![image](https://user-images.githubusercontent.com/93081720/199527891-0f7ecae1-e721-40c3-8875-6c35f09998b4.png)

### sortedByDescending

내림차순 정렬

![image](https://user-images.githubusercontent.com/93081720/199528199-97f69422-75d5-49e1-b082-68b39d2f0e3e.png)

### distinctBy

변형된 값을 기준으로 중복을 제거

![image](https://user-images.githubusercontent.com/93081720/199528591-a9bf3d6d-4f98-4aca-bdbe-f40d68437d72.png)

### first

첫번째 값을 가져온다. 단, 무조건 null이 아니어야 함

![image](https://user-images.githubusercontent.com/93081720/199529090-48bb52ee-9687-4b5d-8f67-ab6a6e851c67.png)

### firstOrNull

첫번째 값 또는 null을 가져온다

### last

마지막 값을 가져옴

### lastOrNull

마지막 값 또는 null을 가져옴

<br>

## 03_리스트를 맵으로 바꾸는 방법

### groupBy

리스트에서 조건을 기준으로 그룹화하여 맵으로 전환

`=`이 아니라`:`임을 유의 ![image](https://user-images.githubusercontent.com/93081720/199530490-636286c4-78fe-4097-9e69-a83122e7cfff.png)

- 함수형으로 파라미터를 받을 때는 일반 괄호를 사용한다.

![image](https://user-images.githubusercontent.com/93081720/199531344-f3596887-b4f6-489e-9a57-1fec286b298f.png)

### associateBy

중복되지 않는 키를 가지고 맵을 만들 때 사용

![image](https://user-images.githubusercontent.com/93081720/199530663-7ea5dc74-d12f-40f0-987e-3412bacbf08b.png)

- 함수형으로 파라미터를 받을 때는 일반 괄호를 사용한다.

![image](https://user-images.githubusercontent.com/93081720/199531717-567065a8-e848-4553-a43e-8cdc24b301f0.png)

<br>

## 04_중첩된 컬렉션 처리

![image](https://user-images.githubusercontent.com/93081720/199533470-e2040fe4-e8e1-4b8c-8316-1824f652b94c.png)

### flatMap

![image](https://user-images.githubusercontent.com/93081720/199533706-10407519-f449-4c3e-879f-e1dcca371ab5.png)

위 코드는 다음과 같이 리팩토링 할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/199536573-e19f28dc-904c-4e9e-80e7-fd94e2edee72.png)

![image](https://user-images.githubusercontent.com/93081720/199536659-cb4de7fd-d7ba-4dbd-a8ba-ae105e62b4ac.png)

![image](https://user-images.githubusercontent.com/93081720/199536768-d0dacd2d-a81c-46d4-91a8-dda387e4b282.png)

### flatten

`List<List<Type>>`을 `List<Type>`으로 바꿈

![image](https://user-images.githubusercontent.com/93081720/199537367-87824512-db47-4bce-a6b3-e1b0fdb31e6d.png)

<br>