![image](https://user-images.githubusercontent.com/93081720/200813778-1b5d0e69-83e7-4e6c-9870-4e8aee5d0cac.png)

# 14_Spring Security (1)

## 01_Spring Security란?

> 인증(Authentication)과 인가(Authorization)와 같은 어플리케이션의 보안을 담당하는 스프링의 하위 프레임워크

### 인증과 인가

스프링 시큐리티는 기본적으로 인증 절차를 먼저 거친 후에 인가 절차를 진행한다.

#### 인증(Authentication)

해당 사용자가 본인이 맞는지를 확인하는 절차

#### 인가(Authorization)

인증된 사용자가 요청한 자원에 접근 가능한지를 결정하는 절차



### 사용자와 암호

스프링 시큐리티에서는 인증과 인가를 위해 Principal을 아이디로, Credential을 비밀번호로 하는 Credential 기반의 인증 방식을 사용한다.

#### 접근 주체(Principal)

보호 받는 자원에 접근하는 대상. 사용자

#### 비밀번호(Credential)

보호 받는 자원에 접근하는 대상의 비밀번호



<br>

## 02_Spring Security Modules

![image](https://user-images.githubusercontent.com/93081720/200815247-848784fd-2145-4003-8315-69789fd3218e.png)

### SecurityContextHolder

인증된 사용자의 정보들을 저장하는 공간으로, 보안 주체의 세부 정보를 포함하여 응용 프로그램의 현재 보안 컨텍스트에 대한 세부 정보가 저장된다.



### SecurityContext

현재 인증한 사용자에 대한 정보, Authentication 객체를 보관하고 있는 공간

스프링 시큐리티는 Security Context 안에 Authentication 객체가 있는지 체크하여 인증 여부를 결정한다



### Authentication

현재 접근하는 주체(Principal)의 인증 정보와 권한을 갖고 있는 인터페이스로, 

SecurityContextHolder를 통해 SecurityContext에 접근하고, SecurityContext를 통해 Authentication에 접근할 수 있다.

```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // SecurityContext에서 꺼냄
```



### UsernamePasswordAuthenticationToken

Authentication을 implements한 AbstractAuthenticationToken의 하위 클래스로, User의 ID가 Principal 역할을 하고, Password가 Credential의 역할을 한다.



### AuthenticationProvider

실제 인증에 대한 부분을 처리하며, 인증 전의 Authentication객체를 받아서 인증이 완료된 객체를 반환하는 역할을 한다.



### AuthenticationManager

인증이 성공하면 Authentication객체를 생성하여 Security Context에 저장한다. 그리고 인증 상태를 유지하기 위해 세션에 보관하며, 인증이 실패한 경우에는 AuthenticationException를 발생시킨다.



### UserDetails

UserDetails 인터페이스는 인증 결과와 주체에 대한 필드를 반환하는 메서드를 가지고 있다. 



### UserDetailService

UserDetailsService 인터페이스는 UserDetails 객체를 반환하는 단 하나의 메소드 loadUserByUsername를 가지고 있다.

<br>

## 03_Spring Security 동작 구조

### 간단 요약

```
1. 시큐리티는 "/login" 주소로 요청이 오면 해당 요청을 가로채서 로그인을 진행함
2. 로그인 진행이 완료되면 시큐리티 세션을 만들고 SecurityContextHolder에 저장함
3. 이때, 시큐리티가 갖고 있는 시큐리티 세션에 들어갈 수 있는 Object는 정해져 있는데, 이 Object가 Authentication 객체이다.
4. 그리고 Authentication 객체 안에는 User정보가 있어야 하고, 이 User 객체 타입은 UserDetails 타입 객체이다.
따라서 우리는 UserDetails를 구현하는 User 클래스나 Principal 클래스를 만들어야한다.
```

![화면 캡처 2022-12-06 132135](https://user-images.githubusercontent.com/93081720/209421135-043b25b4-9ffa-4c4a-ba84-c35e8f333686.png)

<br>

### 전체 요약

![image](https://user-images.githubusercontent.com/93081720/200825549-9d2bc221-d459-4283-a8fd-9540904ffa8a.png)

1. 사용자가 아이디 비밀번호로 로그인을 요청함
2. AuthenticationFilter에서 UsernamePasswordAuthenticationToken을 생성하여 AuthenticationManager에게 전달
3. AuthenticationManager는 등록된 AuthenticationProvider(들)을 조회하여 인증을 요구함
4. AuthenticationProvider는 UserDetailsService를 통해 입력받은 아이디에 대한 사용자 정보를 DB에서 조회함
5. 입력받은 비밀번호를 암호화하여 DB의 비밀번호화 매칭되는 경우 인증이 성공된 UsernameAuthenticationToken을 생성하여 AuthenticationManager로 반환함
6. AuthenticationManager는 UsernameAuthenticationToken을 AuthenticationFilter로 전달함
7. AuthenticationFilter는 전달받은 UsernameAuthenticationToken을 LoginSuccessHandler로 전송하고, SecurityContextHolder에 저장함