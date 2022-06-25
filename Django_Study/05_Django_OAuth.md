# Django_OAuth

## 1. OAuth

### 정의

OAuth는 인증을 위한 오픈 스탠더드 프로토콜로, 사용자가 Facebook이나 트위터 같은 인터넷 서비스의 기능을 다른 애플리케이션(데스크톱, 웹, 모바일 등)에서도 사용할 수 있게 한 것

- 사용자와 여러 인터넷 서비스 업체 모두에 이익이 되는 생태계를 구축하는데 기여

OAuth는 단순히 소셜 로그인 서비스가 아님을 기억!

- OAuth에서 'Auth'는 `'Authentication'(인증)`뿐만 아니라 `'Authorization'(허가) `또한 포함

- '방문증'을 가진 사람이 출입할 수 있는 곳과 '사원증'을 가진 사람이 출입할 수 있는 곳은 다름
- 역시 직접 서비스에 로그인한 사용자와 OAuth를 이용해 권한을 인증받은 사용자는 할 수 있는 일은 다르다



### OpenID와 OAuth

OpenID도 인증을 위한 표준 프로토콜이고 HTTP를 사용한다는 점에서는 OAuth와 같음

그러나 OpenID와 OAuth의 목적은 다름

- OpenID의 주요 목적은 인증(Authentication)이지만, OAuth의 주요 목적은 허가(Authorization)
- OpenID는 로그인(인증)이 주목적, OAuth는 권한있는 사용자인지 확인하는 것(인증+권한)



## 2. Django pjt에 OAuth 구현하기

### 1. django-allauth설치

`$ pip install django-allauth`



### 2. settings.py 추가 및 등록

#### AUTHENTICATION_BACKENDS 추가

![image](https://user-images.githubusercontent.com/93081720/168414171-f89ea50d-5a42-45b0-a99e-1b15dbdc63d3.png)

<br>

#### INSTALLED_APPS 등록

빨간 밑줄 - 필수 등록 / 파란 밑줄 - 사용하려는 OAuth API에 따라 유동적으로 등록

![image](https://user-images.githubusercontent.com/93081720/168414261-04047398-402a-40a7-8548-11a3e6683ad7.png)

<br>

![image](https://user-images.githubusercontent.com/93081720/168414290-b295e1c0-4a9f-4aa3-8472-dc6aa618d7a5.png)

<br>

#### 프로젝트의 메인 urls.py에 path 추가

![image](https://user-images.githubusercontent.com/93081720/168414336-43d54cd4-ab1e-4387-b71b-62b7a88c2469.png)

<br>

#### login template 수정

![image](https://user-images.githubusercontent.com/93081720/168414415-872fdc86-f2e4-48dc-9f86-ce0868e65593.png)

<br>

#### 추가 UX 개선(settings.py 등록)

![image](https://user-images.githubusercontent.com/93081720/168414749-ef020c81-6bb5-4843-a161-7bc2ff3b9337.png)



### 3. 전체적인 흐름(Google 예시)

- GCP -> pjt 생성 및 등록 -> API 및 서비스 -> OAuth 동의 화면 -> User Type '외부' 설정 -> 기타 추가적인 설정 -> 사용자 인증 정보 -> 사용자 인증 정보 만들기 -> OAuth 클라이언트 ID -> 승인된 리디렉션 URI -> Django admin page에서 소셜 어플리케이션 추가 및 등록



#### 소셜 어플리케이션 추가

구글의 경우 발급받은 클라이언트 아이디/비밀번호를 넣고, 카카오의 경우 REST API키만 넣으면 됨

![image](https://user-images.githubusercontent.com/93081720/168420112-1c865ea3-9463-4a23-914a-438218850ef6.png)



![image](https://user-images.githubusercontent.com/93081720/170469275-b47670b7-f59a-46bb-873f-0b2999597896.png)



#### Redirect URI 등록

![image](https://user-images.githubusercontent.com/93081720/168420060-053e000d-77b0-4986-b7c5-6f5e8bc8d9c7.png)