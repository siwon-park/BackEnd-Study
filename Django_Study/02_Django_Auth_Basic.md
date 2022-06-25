## Django Authentication System

Django 인증 시스템은 `django.contrib.auth`와 `django.contrib.contenttpyes`를 통해 이루어지며, 이미 INSATLLED_APPS에 기본적인 django-app으로 설치, 등록되어있음

- `django.contrib.auth` : 인증 프레임워크의 핵심과 기본 모델 제공
- `django.contrib.contenttpyes` : 사용자가 생성한 모델과 권한 연결

django의 인증 시스템은 인증(authentication)과 권한(authorization) 부여를 함께 제공하며 인증 시스템이라고 통합해서 부른다.

- **Authentication(인증)** : 신원 확인, 사용자가 자신이 누구인지 확인하는 것
- **Authorization(권한)** : 권한 부여, 인증된 사용자가 수행할 수 있는 작업을 결정

----

### 01_accounts 앱 생성 및 등록

앱 이름이 반드시 `accounts`일 필요는 없지만, auth와 관련해서 django에서는 accounts로 쓰는 것을 권장

----

### 02_쿠키와 세션(Cookie and Session)

#### 01. HTTP

정의

- html 문서와 같은 리소스들을 가져올 수 있도록 해주는 프로토콜(규칙, 규약)

- 웹에서 이뤄지는 모든 데이터 교환의 기초

- ※https의 s는 보안의 의미를 가진 secure의 약자임

특징
- **비연결지향(connectionless)** : 요청에 따른 응답 결과를 줄 뿐이지 서로 연결된 구조가 아님
  - 서버 요청에 대한 응답을 보낸 후 연결을 끊음
- **무상태(stateless)** :
  - 연결을 끊는 순간 클라이언트와 서버 간의 통신이 끝나며 상태 정보가 유지되지 않음 → 하지만 로그인을 한번 하고 나면 브라우저 팝업을 닫기 전까지는 유지되는데 어떻게 가능한 것인가? => 쿠키와 세션
  - 클라이언트와 서버의 지속적인 관계를 유지하기 위해 쿠키와 세션이 존재

=> **클라이언트와 서버의 지속적인 관계를 유지하기 위해 쿠키와 세션이 존재함**



#### 02. 쿠키(Cookie)

서버가 사용자의 웹 브라우저에 전송하는 작은 데이터 조각

사용자가 웹사이트를 방문할 경우 해당 웹사이트의 서버를 통해 사용자의 컴퓨터에 설치, 배치(place-on)되는 작은 기록 정보 파일

- 클라이언트(브라우저)는 쿠기를 로컬에 Key와 Value의 쌍 데이터 형식으로 저장함
- 이렇게 쿠키를 저장해 놓았다가, 동일한 서버에 재요청 시 저장된 쿠키를 함께 전송함

※ 소프트웨어가 아니기 때문에 프로그램처럼 실행될 순 없지만, 악의적으로 훔쳐서 해당 사용자의 계정 접근 권한을 획득할 수도 있음

http 쿠키는 상태가 있는 세션(Session)을 만들어 줌

쿠키는 요청이 동일한 브라우저에서 들어왔는지 아닌지를 판단할 때 주로 사용

- 이를 이용해 사용자의 로그인 상태를 유지할 수 있음
- 상태가 없는(stateless) http 프로토콜에서 상태 정보를 기억시켜줌

=> **웹 페이지에 접속하면 요청한 웹 페이지를 받으며 쿠키를 저장하고, 클라이언트가 같은 서버에 재요청 시 요청과 함께 쿠키도 함께 전송**

![image](https://user-images.githubusercontent.com/93081720/162725626-9bbdd49e-3cce-451b-87b3-8bb09e0096e2.png)

쿠키의 사용 목적
- 세션 관리(Session management)
  - 로그인, 아이디 자동 완성, 공지 하루 안 보기, 팝업 체크, 장바구니 등의 정보 관리
  - 쿠키가 없다면 장바구니에 물품을 담았는데도 불구하고 장바구니가 비어있는 결과가 반환됨
    - 장바구니에서 삭제 => 쿠키(해당 장바구니 정보)를 삭제
- 개인화(Personalization)
  - 사용자 선호, 테마 등의 설정
- 트랙킹(Tracking)
  - 사용자 행동을 기록, 분석



#### 03. 세션(Session)

사이트와 특정 브라우저 사이의 (상호작용하는) 상태(state)를 유지시키는 것

클라이언트가 서버에 접속하면 서버가 특정 session id를 발급하고, 클라이언트는 발급받은 session id를 쿠키에 저장함

- 클라이언트가 다시 서버에 접속하면 요청과 함께 세션 아이디가 저장된 쿠키를 서버에 전달
- 쿠키는 요청 때마다 서버에 함께 전송되므로, 서버에서 세션 아이디를 확인해 알맞은 로직을 처리

ID는 세션을 구별하기 위해 필요하며, 쿠키에는 ID만 저장함

예) 로그아웃 => 세션 삭제



#### 04. 쿠키 lifetime(수명)

쿠키의 수명은 Session Cookies와 Persistent Cookies로 정의할 수 있음

- Session Cookies
  - 현재 세션이 종료되면 삭제됨
  - 브라우저가 현재 세션이 종료되는 시기를 정의
    - 일부 브라우저는 다시 시작할 때 세션 복원을 사용해 세션 쿠키가 오래 지속될 수 있도록 함
- Persistent cookies(Permanent cookies)
  - Expire속성에 지정된 날짜 혹은 max-age 속성에 지정된 기간이 종료되면 삭제



#### 05. Session in django

장고의 세션은 미들웨어를 통해 구현되며, database-backed sessions 저장 방식을 기본 값으로 사용

세션 정보는 DB의 django_session테이블에 저장됨

모든 것을 세션으로 사용하려고 하면 사용자가 많을 때 서버에 부하가 걸릴 수 있음

※ 미들웨어(Middleware) : HTTP 요청과 응답 처리 중간에서 작동하는 시스템으로 주로 데이터 관리, 앱 서비스, 메시징, 인증 및 API관리 등을 담당

----

### 03_로그인 기능 구현

**로그인은 session을 create하는 과정**임

※ django는 built-in forms를 제공하여 복잡한 session 메커니즘을 대신 해결해줌

#### 01. AuthenticationForm

- 사용자 로그인을 위한 form(forms.Form을 상속받음)
- request를 첫번째 인자로 취함



#### 02. login 함수

- `login(request, user, backend=None)`
  - 현재 세션에 연결하려는 인증된 사용자가 있는 경우 login()함수를 통해 해결함
  - 사용자를 로그인하면 views함수에서 사용함

![image](https://user-images.githubusercontent.com/93081720/162750733-c3e4877a-c053-4a86-bd6f-f8b8baafb3ee.png)

![image](https://user-images.githubusercontent.com/93081720/162750323-ce35cb8b-3d8c-4d0a-bd08-248a0140ece5.png)



#### 03. get_user()

AuthenticationForm의 인스턴스 메서드 =>  get_user()는 AuthenticationForm의 인스턴스만 적용 가능

user_cache는 인스턴스 생성 시 None이며, 유효성 검사를 통과했을 경우에 로그인한 사용자 객체로 할당됨 => 인스턴스의 유효성을 먼저 확인하고, 해당 인스턴스가 유효할 때만 user를 제공하려는 구조

![image](https://user-images.githubusercontent.com/93081720/162751613-b2e588c2-0a0b-45ce-88b7-0d5ff96277d4.png)



#### 04. Users

현재 로그인한 사용자를 나타내는`auth.USER`인스턴스와 로그인하지 않은 Anonymous User 인스턴스는 템플릿 변수 `{{ user }}`에 저장됨

![image](https://user-images.githubusercontent.com/93081720/162754891-3a6f05a7-260b-429e-ab13-68013044ec13.png)

---

### 04_로그 아웃 기능 구현

**로그 아웃은 session을 delete하는 것**과 같음



#### 01. logout 함수

`logout(request)`

- HttpRequest객체를 인자로 받고 반환값이 없으며, 사용자가 로그인하지 않은 경우 오류를 발생시키지 않음
- session data를 DB에서 완전히 삭제시키고, 쿠키에서도 sessionid가 삭제됨
- 쿠키까지 삭제하는 이유는 다른 사람이 동일한 웹 브라우저에서 로그인하여, 이전 사용자의 세션 데이터에 엑세스 하는 것을 방지하기 위함임

![image](https://user-images.githubusercontent.com/93081720/162753606-bb33f2bd-4d72-4556-a97d-285daae03269.png)

![image](https://user-images.githubusercontent.com/93081720/162753481-f26aeeb9-0c29-4d10-a6b9-d44b92a17d07.png)



---

### 05_로그인 사용자에 대한 접근 제한

로그인 사용자에 대한 엑세스 제한 2가지 방법

#### 01. is_authenticated 속성(attribute)

- User_model의 속성(attribute) 중 하나
- 모든 User 인스턴스에 대해 항상 True(단, AnonymousUser에 대해서는 항상 False)
- 단순히 사용자가 인증되었는지 여부를 알 수 있는 방법
- 권한(permission)과 관련이 없으며, 유효한 세션이거나 활성화 상태인지도  확인하는 것은 아님

=> **로그인과 비로그인 상태에서 출력되는 링크를 달리하거나, 이미 인증된 사용자(로그인한 상태)라면 로그인을 수행할 수 없도록 처리하고, 인증된 사용자(로그인한 상태)만 로그아웃을 할 수 있도록 변경**

![image](https://user-images.githubusercontent.com/93081720/162754610-47189f85-782d-4807-818f-148c2698cf62.png)

![image](https://user-images.githubusercontent.com/93081720/162755054-f7dcfa2f-74fe-4594-8387-ca4fd0a6fb05.png)



#### 02. login_required 데코레이터(decorator)

- 사용자가 로그인되어 있지 않으면, `settings.LOGIN_URL`에 설정된 문자열 기반 절대 경로로 redirect함
  - LOGIN_URL의 기본값은 `/accounts/login/`임 => 그래서 django에서 로그인 기능을 하는 앱의 이름을 accounts로 명명하는 것을 권장하는 것임
  - 사용자가 로그인되어 있으면 정상적으로 view함수를 실행함

![image](https://user-images.githubusercontent.com/93081720/162756405-abb63da8-78a4-4599-88d9-c30960df24e9.png)



- `@required_POST`와 `@login_required`를 함께 사용하는 경우 에러가 발생함
  - 비로그인 상태로 무언가 요청했을 때, next 매개변수를 따라 해당함수로 다시 redirect되는데, redirect 과정에서 POST데이터가 손실되어 POST 방식이 불가능하기 때문에 GET 방식으로 요청됨
  - `@login_required`는 GET 요청 방식을 처리할 수 있는 view함수에서만 사용해야함
    - 따라서 `@login_required`를 지우고 view함수에서 `is_authenticated`속성으로 사용자 인증을 판별함



#### 03. next query string parameter

로그인이 정상적으로 진행되면 기존에 요청했던 주소로 redirect하기 위해 주소를 keep해주는 개념 (단, 별도로 처리해주지 않으면 views함수에 설정한 redirect 경로로 이동하게 됨)

※ @login_required 데코레이터가 있어야 next query string parameter가 있음

- redirect 수정
  - request.GET.get('next')는 next query string parameter가 없을 경우 None을 반환한다


![image](https://user-images.githubusercontent.com/93081720/162756870-df98828e-4b16-45d7-a69c-0e43008f36cf.png)

- login.html 수정 => next parameter가 있는 url로 요청을 보내기 위해 action 값 비움

![image](https://user-images.githubusercontent.com/93081720/162757154-2aa0e34a-224c-4122-b9c5-bc419886a126.png)



---

### 06_회원가입

#### UserCreationForm

- 주어진 username과 password로 권한이 없는 새 user를 생성하는 ModelForm
- 3개의 필드를 가짐(username, password1, password2)

---

### 02_회원탈퇴

회원탈퇴는 DB에서 사용자를 삭제하는 것

탈퇴하면서 해당 유저의 세션 데이터도 함께 지우고자 할 경우, 반드시 탈퇴(삭제) 이후에 로그아웃을 진행해야함 => 로그아웃을 먼저 진행할 경우 세션 데이터가 먼저 사라지기 때문에 유저를 탈퇴(삭제) 시킬 수가 없다

![image](https://user-images.githubusercontent.com/93081720/167319789-55f87c2a-3c46-44f1-b864-4c4a65da90a9.png)

---

### 03_회원수정

#### UserChangeForm

- 사용자의 정보 및 권한을 변경하기 위해 admin에서 사용하는 ModelForm
- 그냥 UserChangeForm을 사용할 경우에는 일반 사용자가 접근해서는 안 될 정보 필드까지 모두 수정이 가능해지는 문제가 발생함 => 따라서 UserChangeForm을 상속받아 새로운 서브 클래스 폼을 만들어 접근 가능 필드를 조정해야하는 작업이 필요함



#### get_user_model()

- 현재 프로젝트에서 활성화된 사용자 모델(active user model), User 클래스를 반환함
- django는 `django.contrib.auth.get_user_model()`을 사용해서 참조해야함을 강조

![image](https://user-images.githubusercontent.com/93081720/162763475-8319322f-b7f2-4616-8123-0275f2c44742.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/167319926-d38251f2-5cdd-45a1-a2bc-ff5e79fd0bf1.png)

---

### 04_비밀번호 변경

#### PasswordChangeForm

- 사용자가 비밀번호를 변경할 수 있도록 하는 forms.Form
- 이전 비밀번호를 입력하여 비밀번호를 변경할 수 있도록 함
  - PasswordChangeForm의 부모 클래스인 SetPasswordForm은 이전 비밀번호를 입력받지 않고 비밀번호를 설정할 수 있음



#### update_session_auth_hash

`update_session_auth_hash(request, user)`

- 비밀번호가 변경되면 기존 세션과 사용자 인증 정보가 일치하지 않게되어 로그인 상태를 더 이상 유지할 수 없음 => 따라서 redirect했을 때 로그아웃된 상태로 redirect됨
- update_session_auth_hash는 현재 요청과 새 세션 hash로부터 파생된 업데이트된 사용자 객체를 가져와서 세션 hash를 업데이트 => 암호가 변경되어도 로그아웃되지 않도록 새로운 password hash로 세션을 업데이트함

![image](https://user-images.githubusercontent.com/93081720/162766203-dcc0a86a-4b45-4108-a57a-ec8e5e2ccea2.png)