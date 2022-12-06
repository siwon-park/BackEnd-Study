![image](https://user-images.githubusercontent.com/93081720/200813778-1b5d0e69-83e7-4e6c-9870-4e8aee5d0cac.png)

# 15_Spring Security (2)

## 01_스프링 시큐리티와 권한 설정

스프링 시큐리티에서는 config 설정을 통해 특정 url로의 접근을 제한할 수 있다.

### authenticated()

`authenticated()`는 특정한 권한과 상관 없이, 인증된 사용자의 경우에 접근할 수 있게 한다.

즉, 로그인한 유저가 ADMIN 권한이 있든 없든, MANAGER 권한이 있든 없든, 로그인하여 인증만 된다면 해당 url로 접근하여 요청을 보낼 수 있다.

<br>

### hasRole()과 hasAuthority()

authenticated()가 인증을 체크했다면, `hasRole()`과 `hasAuthority()`를 통해서 권한을 체크할 수도 있다.

다음과 같이 SecutiryConfig 구성 클래스 안에서 configure를 orverride하는 코드에서와 같이 사용 가능하다.

- 예시1)

![image](https://user-images.githubusercontent.com/93081720/201704004-96959c11-c84b-4137-a54d-37b91b397696.png)

- 예시2)

![image](https://user-images.githubusercontent.com/93081720/201702574-7422a057-81b3-4e2d-bd20-2eb5d042fe3f.png)

<br>

먼저 이 둘을 사용하기 위해서는 스프링 시큐리티의 `UserDetails`와 `UserDetailsService`를 구현한 클래스에서 몇 가지 설정을 해줘야 한다.

#### UserDetails

먼저 User 모델의 인증 정보를 가져오는 `getAuthorites()`이다. 스프링 시큐리티에서 요구하는 유저의 인증 정보는 GrantedAuthority형태이다.

 	![image](https://user-images.githubusercontent.com/93081720/201705346-89d027fc-5727-4034-a508-b66d771e4c9d.png)

하나의 유저가 여러 권한을 가질 수도 있으므로, 기본적으로 스프링 시큐리티에서 구현해야하는 유저 권한은 목록이며, 컬렉션 자료형이다.

따라서 `ArrayList`자료형을 사용하였다. 

※ 지금 이 예시의 경우는 유저 엔티티의 role이 하나로 고정되어 있기 때문에 getter를 통해 가져온 role 하나만 넣어도 되는 형태이다. 그렇지 않고 여러 권한을 문자열로 가지고 있을 경우에는 다음과 같이 코드를 짜면 된다.

![image](https://user-images.githubusercontent.com/93081720/201706116-1cf71e7f-47a7-4f6f-b2a0-10f9669dff44.png)

#### UserDetailsService

UserDetailsService에서는 유저 객체를 가져와서 UserDetails를 구현한 클래스를 통해 Authority 객체화 시켜준다.

![image](https://user-images.githubusercontent.com/93081720/201704681-556ac474-3d8f-465b-b211-79bd7a812de1.png)

그리고 나서, 위의 config에서 접근 권한을 설정하여 접근을 제한할 수 있다.

#### hasRole()

유저에게 해당 권한이 있는지 확인하여 접근을 제한하는 메서드로, 유의할 점은 체크를 할 때 스프링 시큐리티에서 PREFIX로 `ROLE_`를 붙이므로, 실제 DB에 저장된 권한 데이터(문자열)의 형태는 `ROLE_권한`이어야한다.

![image](https://user-images.githubusercontent.com/93081720/201708072-1c363999-d192-404e-9508-72a026e7ae5f.png)

따라서  hasRole(`"ROLE_권한"`) 형태가 아니라, hasRole(`"권한"`) 형태로 작성해줘야 한다.

그렇지 않으면, 컴파일하여 실행 시 에러가 발생한다.

#### hasAuthority()

hasRole과 마찬가지로 유저에게 해당 권한이 있는지 확인하여 접근을 제한하는 메서드이다.

그러나 hasRole과는 달리 스프링 시큐리티에서 따로 자동으로 붙여주는 PREFIX가 존재하지 않기 때문에, 그냥 DB에 있는 권한으로 지정한 문자열 데이터 그대로 사용 가능하다.

<br>

### antMatchers()

특정 리소스(url)에 대해서 권한을 설정함

```java
antMatchers("/login**", "/api/**")
```

<br>

### anyRequest()

모든 리소스를 의미함

- 모든 리소스에 대해 인증 필요: `anyRequest().authenticated()`
- 모든 리소스에 대해서 인증 없이 접근 가능: `anyRequest().permitAll()`

<br>

### permitAll()

설정한 리소스에 대한 접근을 인증 절차 없이 허용한다는 의미

```java
antMatchers("/login**", "/api/**").permitAll()
```

<br>

## 필터 관련 설정

커스텀 필터 예시)

![image](https://user-images.githubusercontent.com/93081720/205935501-08b64c8d-6a4c-4afa-9e6c-e51eb72c5ff6.png)

### addFilterBefore()

지정된 필터 앞에 커스텀 필터를 추가 등록한다. 즉, 추가 등록한 필터가 해당 필터보다 먼저 동작한다.

![image](https://user-images.githubusercontent.com/93081720/205935885-a05ceeaf-9c2b-4bb4-8187-3652d4feb0e5.png)

위 사진에서는 MyFilter1이 UsernamePasswordAuthenticationFilter보다 **먼저** 동작하게 한다.

<br>

### addFilterAfter()

지정된 필터 뒤에 커스텀 필터를 추가 등록한다. 즉, 추가 등록한 필터가 해당 필터보다 나중에 동작한다.

![image](https://user-images.githubusercontent.com/93081720/205936291-6a55215e-0170-43f6-95d0-35c7d5321b02.png)

위 사진에서는 MyFilter1이 UsernamePasswordAuthenticationFilter보다 **나중에** 동작하게 한다.

<br>

### addFilterAt()

지정된 필터의 순서에 커스텀 필터가 추가된다.

사실 상 크게 쓰일 일은 없을 듯한 설정 옵션. addFilterBefore나 addFilterAfter를 쓰면 되고, 서블릿의 addFilter를 사용하면서 filter간 우선 순위를 설정해서 사용하면 되기 때문

