# DB - 모델 관계(model relationship)

<br>

## 01. foreign key(외래 키)

### 01. 개념

- 관계형 데이터베이스에서 한 테이블의 필드 중 다른 테이블의 행을 식별할 수 있는 키
- 참조되는 테이블(부모 테이블)의 기본 키(Primary key)를 가르킴
- 참조하는 테이블의 외래 키는 참조되는 테이블 행 1개에 대응됨
- **1:N 관계에서 외래 키는 N의 역할을 하는 테이블쪽에서 갖고 있음**
  - 참조하는 테이블의 행 여러 개가 참조되는 테이블의 동일한 행을 참조할 수 있음



### 02. 특징

- 키를 사용하여 부모 테이블(참조되는 테이블)의 유일한 값을 참조함(참조 무결성 원칙)

- 외래 키의 값이 반드시 부모 테이블의 기본 키일 필요는 없지만 유일한 값이어야 함

※ 참조 무결성

데이터베이스 관계 모델에서 관련된 2개의 테이블 간의 일관성을 말함



### 03. ForeignKey Field

Many-to-one relationship(N:1관계)

`models.ForeignKey(참조하려는 모델, on_delete 옵션)`

![image](https://user-images.githubusercontent.com/93081720/163208777-a13808de-1f8f-409a-be15-f7fdb31996ef.png)

#### on_delete 옵션

- 외래 키가 참조하는 객체가 사라졌을 때 외래 키를 가진 객체를 어떻게 처리할 것인지 정의

- 데이터 무결성(Database Integrity)을 위해서 매우 중요한 설정
  - CASCADE : 부모 객체(참조되는 객체)가 삭제 됐을 때 이를 참조하는 객체도 삭제


※ 데이터 무결성(Database Integrity)

데이터의 정확성과 일관성을 유지하고 보증하는 것을 가리키며, DB와 RDBMS의 중요한 기능

- 개체 무결성(Entity Integrity)
  - PK개념과 관련 => 모든 테이블이 PK를 가져야 하며 PK로 선택된 열은 고유한 값이어야 하고 빈값은 허용하지 않음

- 참조 무결성(Referential Integrity)
  - FK 개념과 관련 => FK의 값이 데이터베이스의 특정 테이블의 PK값을 참조함



#### 특징

ForeignKey는 makemigrations을 하면 테이블의 끝에 만들어짐

ForeignKey는 migrate되고 나면 **참조되는 객체의 소문자 단수형_id** 형태로 만들어짐

예) abcd -> abcd_id (만약 abcd_id로 만든다면 ForeignKey는 abcd_id_id로 만들어짐)

※ 단수형을 쓰는 이유?

- 누구를 참조하는지 모델 명을 알 수 있음

- 1:N에서 1을 참조하는 개념이기 때문에 단수형을 씀



### 04. 1:N 관계 related manager

#### 역참조(참조되는 쪽에서 참조하는 쪽으로 참조하는 것) - `comment_set`

예) Article(1) → Comment(N)

- article`.comment_set`형태로 사용하며 `comment_set`은 manager역할을 함

  - `참조하는 클래스명_set` 형태의 manager를 활용


why 역참조?

- 게시글에 몇 개의 댓글이 작성되었는지 모름

  - article에 comment가 있을 수도 있고 없을 수도 있음

  - 또한 실제로 Article 클래스는 참조되는 클래스이기 때문에 Comment와의 어떠한 관계도 작성되어 있지 않음

  - article`.comment_set`.all() => 1:N 관계에서의 역참조라는 것을 명시적으로 알 수 있음

    article`.comments`.all() => 1:N, M:N 어떤 관계에 해당하는 것인지 알기 힘들다

![image](https://user-images.githubusercontent.com/93081720/163719010-60aa8339-f4c3-4516-acb6-3f0e6b7fc64b.png)

※ related_name 속성 - 역참조 시 사용할 이름(model_set manager)의 이름을 변경할 수 있는 옵션

![image](https://user-images.githubusercontent.com/93081720/163698245-88bbd3f0-1560-4757-9eaf-c027d7c7c7ef.png)

위 예시와 같이 변경 시 article.comment_set 사용 불가. article.comments로 역참조를 해야함(migration과정 필요)



#### 참조

예) Comment(N) → Article(1)

- 댓글의 경우, 어떠한 댓글이든 반드시 자신이 참조하고 있는 게시글이 존재하기 때문에 comment.article과 같은 형태로 접근할 수 있음

  - comment`.article_id` = article.pk
  - **comment`.article` = article (권장)**

![image](https://user-images.githubusercontent.com/93081720/163719733-23425408-bb00-4be5-b018-3584dcfafb61.png)

----

<br>

## 02. 댓글 기능 구현

댓글 기능은 게시판의 기능 중 하나이므로 게시판(articles) 앱에서 작성한다.

### 00. 공통 - ulrs.py 추가

![image](https://user-images.githubusercontent.com/93081720/163213632-62a56d13-ea2a-4daa-aa2a-f78f092152a0.png)

### 01. CommentForm 작성

![image](https://user-images.githubusercontent.com/93081720/163212339-24fc773c-c1cb-4a00-8238-0c8ca20a774d.png)



### 02. articles의 detail 수정(views함수, template)

![image](https://user-images.githubusercontent.com/93081720/163212789-f1e2ec9e-a811-4b64-862b-1559458e6b44.png)

comments를 통해 Comment Read 구현

![image](https://user-images.githubusercontent.com/93081720/163213259-b3c3ecac-8e35-42a0-bfcf-a08208e65aa7.png)



### 03. Comment Create

![image](https://user-images.githubusercontent.com/93081720/163213854-9aff5493-a6f6-4107-a6d0-4c2c55aa9e7c.png)

- #### save(`commit=False`)

  - 인스턴스를 만들지만, DB에 저장은 하지 않음(아직 DB에 저장되지 않은 인스턴스를 반환)
  - 저장하기 전에 객체에 대한 사용자 지정 처리를 수행할 때 유용하게 사용
  - 기본값은 True



### 04. Comment Delete

- detail 템플릿 수정/추가

![image](https://user-images.githubusercontent.com/93081720/163214860-dff56b30-7767-4af6-ad57-0c670d7c1d88.png)

- delete 함수 작성

![image](https://user-images.githubusercontent.com/93081720/163215172-a6de5b31-5cdc-412e-bae5-3ca78ce9400d.png)



---

<br>

## 03. Customizing Authentication in Django

### 01. User 모델 대체하기

일부 프로젝트에서는 Django의 내장 User모델에서 제공하는 인증 요구사항이 적절하지 않을 수 있음

예) username 대신 email을 식별 토큰으로 사용하는 것이 더 적합한 경우

##### `AUTH_USER_MODEL`을 통해 기본 내장 user model을 재정의(override) 가능

Django에서는 커스텀 유저 모델을 설정하는 것을 강력하게 권장함

**※ 프로젝트의 모든 migrations, 첫 migrate를 하기 전에 이 작업을 마치고 시작해야함**

=> 그만큼 중요하며, 프로젝트가 진행되는 동안 변경이 매우 어려우므로, DB 모델링을 하는데에 엄청난 시간과 공수를 들이는 것임

 => 프로젝트가 진행되는 중간에 변경이 매우 어려운 이유는 모델 관계에 영향을 미치기 때문



#### 01. AUTH_USER_MODEL

User를 나타내는데 사용하는 모델

- 기본 값: 'auth.User' (auth앱의 User 모델을 의미)



##### 01. accounts.models.py에 모델 정의

AbstractUser를 상속받아 새로운 User모델 작성

![image](https://user-images.githubusercontent.com/93081720/163217087-cd0a9ee2-9e84-49c3-bb74-5f796fc26164.png)

##### 02. settings.py에 추가

accounts 앱의 User 모델을 사용하겠다고 설정

![image](https://user-images.githubusercontent.com/93081720/163216758-ac72baec-8336-4762-b1df-c5dbf631fc58.png)

##### 03. admin site에 Custom User 모델 등록

![image](https://user-images.githubusercontent.com/93081720/163217493-e74101da-0c59-4c94-8ca5-ef145485a20b.png)

##### 04. makemigrations & migrate 

- 만약 프로젝트 중간에 변경했다면 데이터 베이스 초기화 후 migrate진행

  - 데이터 베이스 초기화 방법

    - db.sqlite3 파일 삭제

    - migrations 파일 모두 삭제(파일명에 숫자가 붙은 파일만 삭제)


---

### 02. Custom User Form

기존 내장 UserModelForm을 썼다면 Form에 대해서도 커스텀 필요 => 왜냐하면 UserCreationForm, UserChangeForm은 기존 내장 ModelForm인데, Meta 클래스에서 참조하고 있는 모델이 Django의 기본 User 모델이기 때문이다.

#### 01. UserCreationForm, UserChangeForm을 상속받아 커스텀 Form 작성

Meta 클래스도 역시 UserCreationForm.Meta, UserChangeForm.Meta를 상속받아야함

![image](https://user-images.githubusercontent.com/93081720/163698471-5b1c67db-6929-4f2b-823e-c12797e231c2.png)

#### 02. signup view함수 수정

![image](https://user-images.githubusercontent.com/93081720/163219728-455536f8-e318-48f4-bba0-05c6097f447d.png)



#### get_user_model()

- 현재 프로젝트에서 활성화된 사용자 모델(active user model)을 반환함
  - User 모델을 커스터마이징했다면 커스터마이징한 User 모델을 반환함
- 따라서 Django에서는 User 클래스를 직접 참조하지 말고 django.contrib.auth.get_user_model()을 사용해야함을 강조함
  - User모델을 직접 참조하면 커스터마이징한 User 모델이 아니기 때문에 유의해야함 

---

<br>

## 04. 1:N 관계 설정

- User(1) - Article(N) => 사용자는 여러 개의 게시글을 작성 가능함
- User(1) - Comment(N) => 사용자는 여러 개의 댓글을 작성 가능함

#### 01. User모델 참조하기

- ##### settings.AUTH_USER_MODEL

  - 리턴값: str
  - **models.py의 User 모델을 참조할 때 사용**

![image](https://user-images.githubusercontent.com/93081720/163221970-e2d240cf-4b1a-4b36-bbf7-fd40fa8944f6.png)

<br>

- ##### get_user_model()

  - 리턴값: User Object
  - **models.py가 아닌 곳에서 User모델을 참조할 때 사용**

![image](https://user-images.githubusercontent.com/93081720/163720181-56076958-a01d-455f-95ba-9fe06dbf75da.png)

<br>

----



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

<br>

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

<br>

##### 2. urls.py 작성

![image](https://user-images.githubusercontent.com/93081720/163827918-90ccd2ca-056b-45ff-971c-bb5edbad6c0e.png)

<br>

##### 3. views.py 작성

헷갈리지 않도록 me와 you로 작성함

![image](https://user-images.githubusercontent.com/93081720/163828090-270e174f-c17b-4afc-9f52-e32e73472d7a.png)

<br>

##### 4. profile 페이지에 follow 표시

![image](https://user-images.githubusercontent.com/93081720/163828339-dd7f1169-965c-453a-8e76-40d0d80adba8.png)

※ `{% with %} {% endwith %}` 태그

반복적으로 사용하는 긴 DTL 변수에 대해 해당 태그 내에서만 새롭게 변수명을 재정의하여 사용 가능함

(구버전은 `{% with as %} {% endwith %}`였으나 최신버전은 `=`으로 재정의)

<br>
