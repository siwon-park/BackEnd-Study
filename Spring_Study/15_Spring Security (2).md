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

