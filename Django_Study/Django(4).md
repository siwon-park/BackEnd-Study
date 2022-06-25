# Django(4)

## 1. N:M 관계

### ※ 모델링?

- 현실 세계를 최대한 유사하게 반영하기 위한 것

- 일상과 가까운 예시를 통해서 DB를 모델링을 연습하고, 데이터 흐름을 제어하는 것이 중요함



### ※ 1:N의 한계

1:N 관계를 가진 모델로 N:M 관계를 구현에는 한계가 있음

- 하나의 외래키 테이블에 2개의 pk값을 넣을 수 없음

- id가 다른 새로운 객체를 생성하여 외래키를 연결하는 행위는 전혀 다른 데이터를 새로 만드는 것이므로 N:M의 연결 관계에 있다고 보기 어려움

예) 의사(1) : 환자(N)의 관계에서

- 하나의 환자 레코드에 의사 필드(외래키)에 외래키 값을 2개 넣을 수 없음
- id만 다르고 나머지 내용은 같은 환자 객체를 하나 더 생성하여 다른 의사 외래키와 연결하는 것은 N:M관계 X
  - 전혀 다른 환자 객체를 생성한 것임

----

※ N: M 관계는 누구 하나가 종속적인 관계가 아님 => 어느 한쪽에서 역참조를 해서 조작하거나 다른쪽에서는 참조해서 조작하는 방식으로 동작함

### 1.  중개 모델 작성(중개 테이블 직접 생성)

![image](https://user-images.githubusercontent.com/93081720/163815967-62ae2d58-49ca-4c65-9f8b-7d98e7820416.png)

- 중개 테이블
  - 중개 테이블에는 N과 M의 데이터 테이블의 id 관계를 정의한 데이터만 삽입됨

![image](https://user-images.githubusercontent.com/93081720/163754078-51992bfe-58f9-4cc4-936c-408b4dc45657.png)

```python
# ORM 예시
doctor1 = Doctor.objects.create(name='justin')
patient1 = Patient.objects.create(name='tony')

Reservation.objects.create(doctor=doctor1, patient=patient1) # 1번 의사의 1번 환자

doctor1.reservation_set.all()
patient1.reservation_set.all()
```



-----

### 2. ManyToManyField(중개 테이블 간접 생성)

#### 1. 개념 및 특징

- 多 대 多 관계 설정 시 사용하는 모델 필드

- 하나의 필수 위치(관계를 설정할 모델 클래스)인자가 필요
- 테이블의 이름은 필드의 이름과 이를 포함하는 모델의 테이블 이름을 조합하여 생성됨
  - 어느 클래스에 필드를 생성했냐에 따라 테이블 명의 순서, 역참조 관계만 바뀔 뿐 사실 상 동일
  - `중개테이블명_모델1_모델2`, `중개테이블명_모델2_모델1`

<br>

ManyToManyField를 통해서 중개 모델 클래스 없이 중개 테이블을 구현 가능함

django는 ManyToManyField를 통해서 중개 테이블을 자동으로 생성함

필드 작성 위치는 두 클래스 중 어느 곳이든 가능함

- 예) 환자 클래스에 하나만 작성
  - doctors = models.ManyToManyField(Doctor)

=> 닥터 클래스에 변수를 바꿔서 작성해도 무방함 => 테이블의 이름만 바뀔뿐 큰 차이 없음

=> 필드를 어느 클래스에 두느냐에 따라 역참조/참조 관계가 바뀜

- **단점: N과 M의 관계만을 나타내기 때문에 예약 시스템을 만든다고 했을 때, 관계 뿐만 아니라 예약 시간 등과 같은 부가적인 정보를 넣어야할 때 그렇게 하지 못함**

![image](https://user-images.githubusercontent.com/93081720/163817229-df57a594-7609-4c29-8d0c-4676da187ec0.png)

#### 2. related_name

- target model(관계 필드를 가지지 않은 모델)이 source model(관계 필드를 가진 모델)을 참조할 때 사용할 manager의 이름 설정 => 역참조 시에 사용하는 manager의 이름을 정의하는 것

- ForeignKey의 related_name과 같음
- 정의 후 migrate하고 나면 기존의 클래스명_set과 같은 형태의 manager는 더 이상 사용 불가

<br>

#### 3. through

- 중개 테이블을 직접 작성하는 경우, through 옵션을 사용하여 중개 테이블을 나타내는 django 모델을 지정할 수 있음
- 일반적으로 중개 테이블에 추가 데이터를 사용하는 N:M 연결일 경우에 사용됨

예) 예약 중개 테이블에 증상과 예약 일자라는 추가 데이터를 설정하는 경우

![image](https://user-images.githubusercontent.com/93081720/163819513-431f7fc8-5acb-4c08-947d-ac145af9b08e.png)

<br>

#### 4. symmetrical

- 값이 True일 경우 source 모델의 인스턴스가 target 모델의 인스턴스를 참조하면, target 모델의 인스턴스도 source 모델의 인스턴스를 자동으로 참조함
  - 예) facebook의 팔로우 기능
  - 내가 당신을 친구 추가하면, 당신도 자동으로 내가 친구 추가가 되는 개념
- 값이 False일 경우 자동으로 참조하지 않음
  - 예) Instagram의 팔로워, 팔로잉의 개념
  - 내가 당신을 팔로우 했지만, 당신은 나를 자동적으로 팔로잉하지는 않음

<br>

#### 5. Related Manager

1:N, N:M 관계에서 사용되는 매니저, 1:N에서는 target 모델의 인스턴스만 사용 가능하지만 N:M은 둘 다 가능

메서드 종류에는 add(), remove(), create(), clear() 등이 있음 

- add()
  - 지정된 객체를 관련 객체 집합에 추가함
  - 이미 존재하는 관계에 사용하면 관계가 복제되지 않음(집합과 같이 중복 허용 x)
  - 인스턴스, 필드 값(pk)을 인자로 허용함
- remove()
  - 관련 객체 집합에서 지정된 모델 객체를 제거함
  - 인스턴스, 필드 값(pk)을 인자로 허용함


<br>

#### ※ 중개 테이블의 필드 생성 규칙

- source model 및 target model이 다른 경우 => 예) 환자와 의사의 관계
  - containing_model_id
  - other_model_id
- ManyToManyField가 동일한 모델을 가르키는 경우 => 예) 유저 모델의 팔로잉(팔로워)
  - from_model_id
  - to_model_id

![image](https://user-images.githubusercontent.com/93081720/163822029-26bdca24-7478-4844-8a7a-40f0886e2924.png)

![image](https://user-images.githubusercontent.com/93081720/163822192-d37df398-a7dd-4ed5-b098-5d900c424ef7.png)



---

## 2. 좋아요(Like)와 팔로잉(Following) 기능 구현하기

### 1. 좋아요 기능 구현

※ 주의) 게시판 모델을 아래와 같이 정의할 경우 migration 에러 발생함

- 이유
  - user필드와 like_user필드의 역참조 매니저가 이름 같게 생성되어 충돌(clashes)가 발생함
    - 역참조 매니저 이름은 `참조모델_set`형태이기 때문임
    - user필드의 역참조 매니저는 .article_set이고 like_user의 역참조 매니저도 .article_set으로 생성됨 

![image](https://user-images.githubusercontent.com/93081720/163758151-21db2fd6-badf-4953-bb58-960ff209d4fc.png)

![image](https://user-images.githubusercontent.com/93081720/163758110-29e6a66d-ec1e-4b08-98e9-9014dd427a4a.png)

<br>

- 해결 방법: 둘 중 하나의 필드에 related_name 속성으로 역참조 매니저의 이름을 재정의해줘야함

![image](https://user-images.githubusercontent.com/93081720/163823106-b6044f7c-248c-4d67-bd17-d58295bd7e88.png)

=> 현재 사용 가능한 DB APIs

- article.user => 게시글을 작성한 유저 → 1:N

- user.article_set => 유저가 작성한 게시글 역참조 → 1:N
- article.like_users => 게시글을 좋아요한 유저 → N:M

- user.like_articles => 유저가 좋아요한 게시글 역참조 → N:M

<br>

#### 1. url.py 작성

![image](https://user-images.githubusercontent.com/93081720/163824442-668b45fd-22f0-4337-a9c4-957f21421ce1.png)

#### 2. views.py 작성

![image](https://user-images.githubusercontent.com/93081720/163824826-574fa84e-3be6-49e7-98db-5bdaf363af86.png)

##### ※ exsits()

- QuerySet에 결과가 포함되어 있으면 True를 반환하고, 그렇지 않으면 False를 반환함
- OO in OOO과 같은 방식은 결국 완전 탐색을 해야하므로 시간적으로 보다 비효율적임

#### 3. index.html 수정(내용 추가)

![image](https://user-images.githubusercontent.com/93081720/163825236-78c87626-3d26-4108-aad5-a345770fa330.png)

<br>

---

### 2. 팔로우 기능 구현

#### 1. profile 페이지 구현

![image](https://user-images.githubusercontent.com/93081720/163825728-b40b7463-8913-4ce6-8c56-b115209d99b0.png)

※ 주의) profile의 path가 문자열 인자를 변수로 하여 url을 구성하고 있기 때문에, 해당 path를 맨 위로 올리면 accounts앱으로 들어오는 (숫자가 없는)모든 ulr이 profile의 path로만 들어가게됨

![image](https://user-images.githubusercontent.com/93081720/163826036-c2696efa-99f8-486e-a560-c6c509050a33.png)

![image](https://user-images.githubusercontent.com/93081720/163826753-836a8504-8aaa-4a7e-8251-826e43359ae2.png)

<br>

※ article.user.username의 이해

![image](https://user-images.githubusercontent.com/93081720/163827194-807a2769-9ff4-41a7-8961-9f8044dc7bea.png)

`article.user`는 article객체를 통해서 user 모델(User 모델의 소문자 단수형 표기)을 참조하는 것이며, 결국 `article.user.username`은 User 모델의 username이라는 속성에 접근한 것

<br>

#### 2. follow 기능 구현

##### 1. User 모델 재정의

![image](https://user-images.githubusercontent.com/93081720/163827669-7b063326-0c9d-407f-a8c6-64cf27e62e6c.png)

##### 2. urls.py 작성

![image](https://user-images.githubusercontent.com/93081720/163827918-90ccd2ca-056b-45ff-971c-bb5edbad6c0e.png)

##### 3. views.py 작성

헷갈리지 않도록 me와 you로 작성함

![image](https://user-images.githubusercontent.com/93081720/163828090-270e174f-c17b-4afc-9f52-e32e73472d7a.png)

##### 4. profile 페이지에 follow 표시

![image](https://user-images.githubusercontent.com/93081720/163828339-dd7f1169-965c-453a-8e76-40d0d80adba8.png)

※ `{% with %} {% endwith %}` 태그

반복적으로 사용하는 긴 DTL 변수에 대해 해당 태그 내에서만 새롭게 변수명을 재정의하여 사용 가능함

(구버전은 `{% with as %} {% endwith %}`였으나 최신버전은 `=`으로 재정의)