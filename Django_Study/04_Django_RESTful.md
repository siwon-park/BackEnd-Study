# Django(5)_RESTful

## 1. HTTP

### 1. HTTP

#### 정의

- 웹 상에서 컨텐츠를 전송하기 위한 약속, 규칙(Protocol)

HTML 문서와 같은 자원(Resource)들을 가져올 수 있도록 하는 프로토콜

웹 상에서 이루어지는 모든 데이터 교환의 기초

- 요청(Request)과 응답(Response)

<br>

#### 특징

- 무상태(Stateless)
- 비연결지향(Connectionless)

=> 쿠키(cookie)와 세션(session)을 통해 서버 상태를 요청과 연결함

<br>

#### HTTP request method

- 자원에 대한 수행하고자 하는 행위, 동작을 정의
  - 자원(resource) : HTTP 요청의 대상
- GET(조회), POST(생성), PUT(수정), DELETE(삭제)

<br>

#### HTTP request status code

- Informational responses(100번대)
- Successful responses(200번대)
- Redirection responses(300번대)
- Client error responses(400번대)
- Server error responses(500번대)

<br>

### 2. URI(Uniform Resource Identifier)

- 통합 자원 식별자
- 인터넷의 자원을 식별하는 유일한 주소(정보의 자원을 표현)
- 인터넷에서 자원을 식별하거나 이름을 지정하는데 사용되는 간단한 문자열
- 하위개념
  - URL, URN

=> URI는 크게 URL과 URN으로 나눌 수 있지만, URN을 사용하는 비중이 매우 작기 때문에 일반적으로 URL은 URI와 같은 의미처럼 통용됨

<br>

#### URL(Uniform Resource Locator)

- 통합 자원 위치

- 네트워크 상에 자원이 어디 있는지 알려주기 위한 약속
- 과거에는 실제 자원 위치를 나타냈지만 현재는 추상화된 의미론적인 구성
- 웹 주소, 링크라도고 불림

<br>

#### URN(Uniform Resource Name)

- 통합 자원 이름
- URL과 달리 자원의 위치에 영향을 받지 않는 유일한 이름 역할을 함
- 예) ISBN(국제표준도서번호)

<br>

#### URI의 구조

##### Scheme(Protocol)

- 브라우저가 사용해야 하는 프로토콜
- http(s), data, file, ftp, mailto 등

![image](https://user-images.githubusercontent.com/93081720/164249442-02191997-ebd6-473c-b6e3-b571e828cdd6.png)

<br>

##### Host(Domain name)

- 요청을 받는 웹 서버의 이름
- IP주소로 직접 사용할 수도 있지만, 실 사용 시 불편하므로 IP주소로는 웹에서 자주 사용되지는 않음

![image](https://user-images.githubusercontent.com/93081720/164249634-bcaf66e0-db7f-40be-9597-0fb31befa1b8.png)

<br>

##### PORT

- 웹 서버 상의 리소스에 접근하는데 사용되는 기술적인 문(gate)
- 일반적으로는 로컬에서 서버를 열고 접속할 때 보임
- HTTP 프로토콜의 표준 포트
  - HTTP 80 => 로컬 8000번대 포트
  - HTTPS 443
- DNS(Domain Name Server)

![image](https://user-images.githubusercontent.com/93081720/164249847-2d4458b7-6c7b-46e7-807c-78a8ddd616b9.png)

<br>

##### PATH

- 웹 서버 상의 리소스 경로
- 초기에는 실제 파일이 위치한 물리적 위치를 나타냈지만, 오늘날에는 물리적 실제 위치가 아닌 추상화 형태 구조로 표현

![image](https://user-images.githubusercontent.com/93081720/164250008-3a341f2e-0a7d-4429-a944-877d817d8bdb.png)

<br>

##### Query(Identifier; 식별자)

- Query String Parameters
  - URL 상 ?이후에 오는 것들
- 웹 서버에 제공되는 추가적인 매개 변수
- &로 구분되는 key와 value 목록

![image](https://user-images.githubusercontent.com/93081720/164250147-5626a20c-141a-4ce5-ba96-844d502432f2.png)

<br>

##### Fragment(Anchor)

- 자원 안에서 북마크 역할을 함
- 브라우저에게 해당 문서(HTML)의 특정 부분을 보여주기 위한 방법
- 브라우저에게 알려주는 요소이기 때문에 fragment identifier(부분 식별자)라고 부르며 '#'뒤의 부분은 요청이 서버에 보내지지 않음

![image](https://user-images.githubusercontent.com/93081720/164250278-e5007ea8-c812-4649-97bd-4c70d5c325e3.png)

<br>

---

<br>

## 2. RESTful API

### 1. API

- Application Programming Interface
- 프로그래밍 언어가 제공하는 기능을 수행할 수 있게 만든 인터페이스
  - **어플리케이션과 프로그래밍으로 소통하는 방법**
  - CLI는 커맨드 라인, GUI는 그래픽 아이콘, API는 프로그래밍을 통해 특정한 기능을 수행함
- Web API
  - 웹 애플리케이션 개발에서 다른 서비스 요청을 보내고 응답을 받기 위해 정의된 명세
  - 현재 웹 개발은 모든 것을 직접 개발하기보다 여러 Open API를 활용하는 추세
- 응답 데이터 타입
  - HTML, XML, JSON 등

<br>

### 2. REST

**RE**presentational **S**tate **T**ransfer

- API 서버를 개발하기 위한 일종의 소프트웨어 **설계 방법론**
- 네트워크 구조(Network Architecture) 원리의 모음
  - 자원을 정의하고 자원에 대한 주소를 지정하는 전반적인 방법
- REST원리를 따르는 시스템을 RESTful이란 용어로 지칭함
  - REST원리에 따라 설계한 API => RESTful API
- 자원을 정의하는 방법에 대한 고민 => 정의된 자원을 어디에 위치시킬 것인가?

=> **서버의 자원을 어떠한 방식으로 접근하게 해야하는지 구체적으로 명시한 것이 REST**

=> **각 자원(resource)에 대하여 자원의 상태(state)에 대한 정보를 주고 받는 개발 방식(아키텍쳐)**

<br>

- REST의 자원과 주소의 지정 방법
  - 자원 => URI
  - 행위 => HTTP method
  - 표현 => JSON
    - 자원과 행위를 통해 궁극적으로 표현되는 추상화된 결과물

<br>

- REST 핵심 규칙
  - 정보는 URI로 표현
  - 자원에 대한 행위는 HTTP method로 표현(GET, POST, PUT, DELETE => READ, CREATE, UPDATE, DELETE)
  - 설계 방법론은 지켰을 때 얻는 것이 훨씬 많기 때문에 지키는 것을 권장함
    - 설계 방법론은 규약이 아니기 때문에 지키지 않아도 실제 동작 여부에 큰 영향을 미치지 않음

<br>

### 3. JSON

JavaScript Object Notation

JavaScript 표기법을 따른 **단순 문자열**

- 특징
  - 사람이 읽어나 쓰기 쉽고 기계가 파싱(해석, 분석)하고 만들어내기 쉬움
  - 키와 값(Key, Value)형태로 구성되어 프로그래밍 언어의 자료구조로 쉽게 변환 가능함

<br>

---

<br>

## 3. Response

※ django_seed

django-seed 라이브러리를 통해 모델 구조에 맞는 데이터를 임의로 생성 가능함

`$ pip install django-seed` (INSTALLED_APPS에 등록 시에는 `django_seed`로 등록)

`$ python manage.py seed articles --number=5` (5개의 랜덤 데이터를 DB 모델에 채움)

<br>

### 1. 직렬화(Serialization)

데이터 구조나 객체 상태를 동일 또는 다른 컴퓨터 환경에 저장하고, 나중에 재구성할 수 있는 포멧으로 변환하는 과정

OOP에 대부분 등장하는 개념

객체가 메모리 상에 저장된 상태를 뽑아서 byte array로 만드는 것(직렬화 한다)

※ 역으로 byte array 포멧을 프로그래밍 언어의 자료로 객체화하는 것을 deserialization이라 함

![image](https://user-images.githubusercontent.com/93081720/164254179-eeb6729e-01a2-4e53-94a2-7e53764a170f.png)

<br>

#### Serializers in Django

- Queryset 및 Model 인스턴스와 같은 복잡한 데이터를 JSON, XML 등의 유형으로 쉽게 변환 할 수 있는 Python 데이터 타입으로 만들어줌

<br>

### 2. DRF(Django REST Framework)

Web API 구축을 위해 강력한 Toolkit을 제공하는 라이브러리

※ django rest framework

`$ pip install djangorestframework==3.13.1`(버전은 굳이 명시 안 해줘도 되지만 RTS로 설치할 것)

(INSTALLED_APPS에 등록 시에는 `rest_framework`로 등록)

<br>

#### Django Serializer

주어진 모델 정보를 활용하기 때문에 직접 필드를 만들 필요가 없음

DRF의 Serializer는 django의 Form 및 ModelFrom 클래스와 매우 유사하게 구성되고 동작함

- Web API
  - 웹 어플리케이션 개발에서 다른 서비스에 요청을 보내고 응답을 받기위해 정의된 명세

=> **★☆모델을 직렬화하는 것은 Form을 만드는 것과 매우 유사함☆★**

![image](https://user-images.githubusercontent.com/93081720/164255572-c7bfe743-38d2-45f5-8deb-6dd36f8ebaac.png)

<br>

----

<br>

## 4. Single Model

### 1. ModelSerializer

모델 필드에 해당하는 필드가 있는 Serializer 클래스를 자동으로 만들 수 있는 shortcut

`from rest_framework import serializers` => `serializers.ModelSerializer`로 호출

- 모델 정보에 맞춰 자동으로 필드 생성
- serializer에 대한 유효성 검사기 자동 생성 => 역직렬화 시
- .create()와 .update()와 같은 간단한 기능 기본 구현이 포함되어 있음

![image](https://user-images.githubusercontent.com/93081720/164260040-7712d4d1-a8c4-41e2-a421-a1b0d443d712.png)

※ 직렬화 시 필드에 있는 값을 읽고(read), 역직렬화 시 필드 속성에 해당하는 외부의 데이터를 값을 씀(write)

<br>

![image](https://user-images.githubusercontent.com/93081720/164261410-b07143c7-7ac3-4a49-8329-ad96db320c01.png)

- #### many 속성

  - 단일 인스턴스가 아니라, QuerySet과 같이 다수의 인스턴스 객체들에 대해 직렬화할 때 many속성을 사용하여 True로 지정해준다(단일 인스턴스일 경우 아예 쓰지 않음)

<br>

- #### api_view 데코레이터

  - view함수가 응답해야하는 HTTP 메서드의 목록을 리스트의 인자로 받음(default는 GET)
  - 허용하지 않은 메서드에 대해 405 Method Not Allowed로 응답
  - DRF에서는 데코레이터는 선택이 아닌 필수적으로 작성해야만 view함수가 정상적으로 작동함

<br>

![image](https://user-images.githubusercontent.com/93081720/164263883-425dc81f-e1e8-4d4d-9c66-9accd6fdb492.png)

- #### status code

  - DRF는 status code를 보다 명확하고 읽기 쉽게 만드는 데 사용할 수 있는 정의된 상수 집합을 제공함
  - rest_framework의 status모듈에 HTTP status에 대한 코드 집합이 모두 포함돼 있음
  - status=200과 같이 숫자 표기도 가능하지만 DRF에서 권장하지 않음

<br>

- #### raise_exception

  - is_valid() 유효성 검사에 오류가 있는 경우 serializers.ValidationError 예외를 발생시키는 인자로 사용
  - DRF에서 제공하는 기본 예외 처리기에 의해 자동 처리되며, 예외 처리 시 HTTP status code 400을 응답
    - 따라서 raise_exception=True일 경우 400응답에 대한 return구문을 작성하지 않아도 됨

<br>

### 2. GET, POST, PUT, DELETE 작성 예시

![image](https://user-images.githubusercontent.com/93081720/164383353-f42df8ff-5457-4c7c-aaba-0d2feea0db31.png)

<br>

----

<br>

## 5. 1:N Relation

### 1. .save()메서드

Serializer에 인스턴스를 넘겨주지 않은 이상 .save()를 통해서만 인스턴스가 생성됨을 유의

인스턴스를 저장하는(생성하는) 시점에 추가 데이터 삽입이 필요한 경우(예-댓글 생성 시 어떤 게시글에 대한 것인지) 아래와 같이 속성 값=인스턴스(데이터)로 설정하여 추가적인 데이터 값을 받을 수 있음

![image](https://user-images.githubusercontent.com/93081720/164382918-8c96c20f-d494-4299-97a0-7b7cefa7f101.png)

<br>

### 2. 읽기 전용 필드(Read Only Field)

예를 들어, 댓글을 작성한다고 가정했을 때 어떤 게시글에 작성하는 댓글인지 게시글에 대한 id정보가 필요함

모델에서 article이라는 필드의 외래키값을 설정했지만, 댓글을 생성할 때 게시글의 id 정보를 form-data로 넘겨주지 않았기 때문에 직렬화하는 과정에서 article 필드에 대해 유효성 통과를 할 수 없는 상황이 발생함

=> Meta 클래스의 fields항목을 전부(all)로 설정했기 때문에 article에 대한 정보도 넘겨줘야하는 상태인 것임

=> `read_only_fields` 항목을 통해 해당 필드에 대해 직렬화하지 않고 읽기 전용 필드로 지정 가능함

![image](https://user-images.githubusercontent.com/93081720/164382231-ee7c2be2-e75d-4f8f-9054-0ffb7fae4143.png)

<br>

#### ※ 새로운 필드를 추가하기

![image](https://user-images.githubusercontent.com/93081720/164374234-a91254d1-798d-4314-8468-260e792085a2.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/164375048-3258ebe6-ce9b-47f1-af03-6729f9c1ee2f.png)

<br>

### 3. 1:N serializer

특정 게시글(1)에 작성된 댓글 목록(N)  출력하기 => 기존의 필드 override

- PrimaryKeyRelatedField => 역참조 대상의 id를 출력함
- Nested relationships => 역참조 대상의 모든 내용을 nested(중첩)하게 표현함

특정 게시글(1)에 작성된 댓글 개수(N)  출력하기 => 새로운 필드 추가

- Nested relationships

<br>

#### 1. PrimaryKeyRelatedField

pk값을 사용하여 관계된 대상을 역참조하는데 사용 가능

필드가 N관계를 나타내는데 사용되는 경우 many=True 속성 지정 필요

_set 필드값은 역참조할 때만 사용하는 필드이므로 form-data로 받지 않으니 read_only=True로 지정 필요

![image](https://user-images.githubusercontent.com/93081720/164371737-8e263b26-760f-4b2c-a5c8-7203d89bd118.png)

※ 단, 모델에서 ForeignKey필드의 related_name을 따로 변경해줬다면 변수명도 변경한 related_name으로 지정해줘야 한다.



<br>

#### 2. Nested relationships

모델 관계상으로 참조된 대상(comment)은 참조하는 대상(article)의 표현에 포합되거나 중첩될 수 있음

serializer를 필드로 사용하여 구현 가능

※ 경우에 따라 클래스 위치를 변경해줘야할 수도 있음

![image](https://user-images.githubusercontent.com/93081720/164373592-57cc7511-8982-48d6-9cad-d370483be594.png)

<br>

#### 3. 특정 게시글(1)에 작성된 댓글 개수(N)  출력하기 => 새로운 필드 추가

- comment_set은 댓글 목록을 불러올 수 있는 필드로 모델 관계에 의해 자동적으로 구성되어 있어, 옵션만 지정해주면 되지만, 댓글의 개수 필드는 자동적으로 구성되는 매니저가 아니기 때문에 아예 새로운 필드를 직접 작성해야한다.

![image](https://user-images.githubusercontent.com/93081720/164375170-379e4151-682f-4977-8adf-715b6ed4c5bc.png)

- ##### source 속성

  - 새로 작성한 필드에 넣을 데이터를 참조할 필드 및 참조 필드의 속성을 지정할 수 있음
  - 위의 예시에서 .count는 속성이 아니고, 쿼리셋 api임(.count())



#### ※ 서로 참조하고 싶은 경우 해결법?

1. 한 클래스의 inner 클래스로 선언함

![image](https://user-images.githubusercontent.com/93081720/164724888-9df429dd-1de0-4579-8ef8-ddc52e9fc401.png)

2. 다른 파일에서 새로 작성하여 import해서 사용함



#### ※ 주의 사항

특정 필드를 override 혹은 새롭게 추가한 경우 read_only_fields에 등록하여 사용하는 것은 불가능함. 반드시 속성 값으로 read_only=True를 지정해줘야한다.

![image](https://user-images.githubusercontent.com/93081720/164379403-a8d03d9b-0ad1-4217-ba16-c03351d9580c.png)

<br>

----

## Fixtures

DB에 채우는 테스트용 초기 데이터를 Fixtures라고 한다.

DB의 serialized된 내용을 포함하는 파일 모음

<br>

- 왜 사용하는가?
  - 앱을 처음 설정할 때, 미리 준비된 데이터로 DB를 채우는 작업이 필요한 상황이 있음

- django가 fixtures 파일을 찾는 경로
  - apps/fixtures/
- 사용법
  - django_seed로 임의 데이터를 DB에 넣음
  - dumpdata를 통해 fixtures파일을 생성함
  - 생성한 fixtures파일을 django의 fixtures파일 저장 경로에 저장
  - DB migrate 또는 DB에 데이터가 있으면 DB 초기화 후, loaddata를 통해 fixtures파일 업로드

<br>

### 1. dumpdata

응용 프로그램과 관련된 DB의 모든 데이터를 표준 출력으로 출력(저장)

※ fixtures는 직접 생성하는 것이 아니라 dumpdata를 통해 생성하는 것이니 직접 작성 X 

- 기본 사용 형태

![image](https://user-images.githubusercontent.com/93081720/164727193-d3df289d-4cda-45e6-95f9-8ab8b0c9d845.png)

<br>

예시) user.json으로 저장

![image](https://user-images.githubusercontent.com/93081720/164727440-5e97b6d1-e826-4872-b3a3-63cd634741cc.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/164727699-cbb2ee8d-4355-4547-9d1a-8ef77c7ab3d8.png)

<br>

### 2. loaddata

fixture의 내용을 검색하여 DB에 업로드

예시) DB 초기화 또는 migrate 이후 loaddata 실행

![image](https://user-images.githubusercontent.com/93081720/164728896-bf13685b-d3d1-4fed-b7c0-748b68523d5f.png)

<br>

---

<br>

# Django Rest Auth

![image](https://user-images.githubusercontent.com/93081720/170827196-60afa29e-959b-4391-93eb-607def6fc911.png)





![image](https://user-images.githubusercontent.com/93081720/168844525-ff0e1673-11a0-47b9-8251-279a5fa6251b.png)

### Server

클라이언트에게 '정보'와 '서비스'를 제공하는 컴퓨터 시스템

- Django REST Framework(DRF) => JSON 반환
- Django 서버 => HTML 반환

DB와 data가 서버의 핵심 => DB와 통신하며 데이터를 CRUD

요청에 따른 정보를 client에게 응답



### Client

정보 요청을 보내는 주체 / 표현을 하는 주체

서버에게 그 서바가 맡는, 서버가 제공하는 서비스를 올바르게 요청하고, 요청 시 서버가 요구하는 필요한 인자를 그 방식에 맞게 전달하며, 서버로부터 반환되는 응답을 사용자에게 적절한 방식으로 표현하는 기능을 가진 시스템

- 올바르게 보내다 => 서버가 요구하는 형식에 맞게 보내는 것

응답받은 정보를 잘 가공하여 화면에 보여줌



data 설계(어떤 데이터가 필요한가?) -> DB ERD 작성 -> DB 모델링 -> API(클라이언트와 어떤 데이터를 주고 받을지 합의, 어떤 url로 보냈을 때, 이러한 데이터를 준다)

<br>

## CORS(Cross Origin Resource Sharing)

### Origin(출처)

두 URL의 프로토콜, 포트, 호스트가 모두 같을 경우에만 동일한 출처로 인정함



### SOP(Same-Origin Policy, 동일 출처 정책)

특정 출처에서 불러온 문서나 스크립트가 다른 출처에서 가져온 리소스와 상호작용하는 것을 제안하는 보안 방법 => 잠재적으로 해로울 수 있는 문서를 브라우저가 차단하여 공격을 받을 경로를 줄임

![image](https://user-images.githubusercontent.com/93081720/168844784-e2f7396c-a7ee-4b85-b0d6-5d22d5fd6709.png)

<br>

### CORS(Cross-Origin Resource Sharing, 교차 출처 자원 공유)

추가 HTTP header를 사용하여, 특정 출처에서 실행 중인 웹 어플리케이션이 다른 출처의 자원에 접근할 수 있는 권한을 부여하도록 브라우저에게 알려주는 체제

리소스가 자신의 출처(Domain, Protocol, Port)와 다를 때 교차 출처 HTTP 요청을 실행함 => 다른 출처의 리소스를 불러오려면 그 출처에서 올바른 CORS header를 포함한 응답을 반환해야한다.

#### 사용하는 이유?

- 브라우저 & 웹 어플리케이션 보호
  - 악의적인 사이트의 데이터를 가져오지 않도록 사전 차단
  - 응답으로 받는 자원에 대한 최소한의 검증
  - 서버는 정상적으로 응답하지만 브라우저에서 차단하는 것임
- 서버의 자원 관리
  - 누가 해당 리소스에 접근할 수 있는지 관리가 가능함



### Access-Control-Allow-Origin 응답 헤더

브라우저 리소스에 접근하는 임의의 출처로부터 요청을 허용한다고 알리는 응답에 포함

![image](https://user-images.githubusercontent.com/93081720/168846560-052c006f-63b8-450b-a003-b1febc16ef3e.png)

![image](https://user-images.githubusercontent.com/93081720/168846703-b5631c35-8840-4a0b-be89-d96ad60c90e5.png)



### 1. django-cors-headers

응답에 CORS header를 추가해주는 라이브러리

다른 출처에서 보내는 Django 어플리케이션에 대한 브라우저 내의 요청을 허용함

Django App이 header 정보에 CORS를 설정한 상태로 응답을 줄 수 있게 도와주며, 이 설정을 통해 브라우저는 다른 출처에서 요청을 보내는 것이 가능해짐

#### 설치

`$pip install django-cors-headers`

#### INSTALLED_APPS 등록

`'corsheaders'`로 등록

#### 미들웨어 추가

`corsheaders.middleware.CorsMiddleware`

- 단, django.middleware.common.CommonMiddleware보다 위에 위치시켜야함

#### CORS_ALLOWED_ORIGINS 리스트 변수 선언

교차 출처 자원 공유를 허용할 도메인을 등록함

- 예) `http://localhost:8080`

![image](https://user-images.githubusercontent.com/93081720/170828014-d1780b25-7104-4151-ba9f-5cb4f68920c5.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/170828083-32ad462e-9117-438f-a0e7-a779c97a1b7c.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/170828118-a5f4e6a4-e5b0-4759-81ec-a4cea882a679.png)



<br>

## Authentication & Authorization

### Authentication

인증

=> 자신이라고 주장하는 유저를 확인하는 과정

일반적으로 인증 이후에 권한이 부여됨

세션, 토큰, 제 3자(소셜 서비스)를 활용하는 등의 다양한 인증 방식이 존재함

<br>

### Authorization

권한 부여, 권한, 허가

유저가 자원에 접근할 수 있는지 확인/권한을 부여하는 것

=> 인증이 되었어도 모든 권한을 부여 받는 것은 아님

- 예) 로그인(인증)을 했다고 하더라도 다른 사람의 글까지 수정/삭제할 수 있진 않음(권한)

<br>

## DRF_Authentication

### 다양한 인증 방식

#### 1. Session Based

#### 2. Token Based

- ##### Basic Token

- ##### JWT(JSON Web Token)

#### 3. OAuth

- ##### google, facebook, kakao, naver 등



### Basic Token Authentication 과정

#### 1. 클라이언트가 서버에 요청

![image](https://user-images.githubusercontent.com/93081720/168848884-87a11ac3-b22f-488a-8d59-545392a96f1f.png)

#### 2. 서버에서 확인

![image](https://user-images.githubusercontent.com/93081720/168849103-861ea847-6ebe-4ab0-9afe-adc48af6ee6e.png)

#### 3. 서버가 토큰 값을 반환

![image](https://user-images.githubusercontent.com/93081720/168849266-5cbf347e-bfe0-4302-9cdf-975d325f622e.png)

#### 4. 클라이언트가 토큰 값을 활용하여 서버에 요청

![image](https://user-images.githubusercontent.com/93081720/168849552-f3a8aefc-7656-495e-9374-6af2f0c8fdb3.png)

#### 5. 서버에서 응답 반환

![image](https://user-images.githubusercontent.com/93081720/168849679-dfb2cbab-e457-46cf-9b38-b74effcf7e52.png)

#### 과정 종합

![image](https://user-images.githubusercontent.com/93081720/168849931-3727778b-efa3-402c-a2d7-ef2172afa152.png)

<br>

### JWT

JSON Web Token

JSON 포맷을 활용한 요소 간 안전하게 정보를 교환하기 위한 표준 포멧

암호화 알고리즘에 의한 디지털 서명이 되어 있기 때문에 JWT 자체로 검증 가능

기본 토큰 인증 체계와 달리 DB를 사용하기 때문에 검증에 필요한 정보를 모두 갖고 있어(self-contained) 토큰 유효성 검사를 할 필요가 없음

단, 토큰 탈취 시 서버 측에서 토큰 무효화가 불가능함 => 블랙 리스팅 테이블을 활용해서 막아야 함

따라서 매우 짧은 유효기간(5min)과 Refresh 토큰을 활용하여 구현

One source Multi Use 상황에 적합

<br>

### dj-rest-auth, django-allauth

#### 라이브러리 설치

![image](https://user-images.githubusercontent.com/93081720/170827196-60afa29e-959b-4391-93eb-607def6fc911.png)

<br>

#### Settings.py 설정

![image](https://user-images.githubusercontent.com/93081720/170828564-2761df7d-cb29-40d2-b085-36e5d049bad4.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/170828266-05f5f25e-4c69-420e-9e3f-a9816795d490.png)

<br>

#### 메인 urls.py

![image](https://user-images.githubusercontent.com/93081720/170828672-aeaf6407-2f72-4ba6-a4f0-6aaf0f1adb66.png)

<br>

#### 각 앱별 views.py 권한 설정

![image](https://user-images.githubusercontent.com/93081720/170828870-b27043fb-5e3f-4d72-9c6f-5e1be642846d.png)

<br>

##### 데코레이터

![image](https://user-images.githubusercontent.com/93081720/170828891-16d7f5bb-03b9-4182-a31b-f27caad4ae73.png)

인증된 사용자의 접근이 가능하거나 인증되지 않은 사용자는 읽기만 가능

<br>

![image](https://user-images.githubusercontent.com/93081720/170828907-2ba3b921-a86b-43e2-b3e2-2eeb936df870.png)

관리자권한의 유저만 접근 가능

<br>

![image](https://user-images.githubusercontent.com/93081720/170828945-147696c2-a506-4b0c-99ce-f66c30a8481a.png)

모든 사용자에 대해 접근 가능

<br>
