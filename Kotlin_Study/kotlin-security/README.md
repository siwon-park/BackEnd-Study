# Kotlin으로 구현한 JWT, SpringSecurity

## 설명

kotlin + Springboot로 JWT와 SpringSecurity를 구현하였다.

코틀린에 대해 어느정도 기초는 알고 있다고 생각했으나 많은 부분 자바 코드를 코틀린으로 리팩토링하는 과정에서 에러를 만났다.

해당 코드는 accessToken과 refreshToken을 받아오는 것과 SpringSecurity로 간단한 권한 접근까지만 구현하였다.

**테스트 결과1**

![image](https://user-images.githubusercontent.com/93081720/205836275-6ee9da22-ea45-4a33-b8e8-ffb48d5d5360.png)

**테스트 결과 2**

![화면 캡처 2022-12-06 145706](https://user-images.githubusercontent.com/93081720/205836157-9cacf742-0fd6-4faa-ab87-206af6fcbe47.png)

<br>

## 트러블 슈팅 기록

### companion object

#### 상황

코틀린 인터페이스에 자바 인터페이스처럼 필드를 선언하려니 에러가 발생하였음

#### 원인

인터페이스에 필드를 선언하는 방법은 권장하지 않는 방법으로 인터페이스를 구현한 곳에서도 동일한 네이밍으로 사용 가능하기 때문에 의도치 않은 에러를 방지하기 위함임

#### 해결

권장하는 방법은 아니지만, companion object를 사용하여 해결하였음

![image](https://user-images.githubusercontent.com/93081720/205838930-4b63445d-e2c1-47d8-9a33-6efa4ef48ac3.png)

<br>

### JwtFilter 등록 관련

#### 상황

JwtFilter와 관련해서 다음과 같은 에러가 발생하였음

![jwtfilter error](https://user-images.githubusercontent.com/93081720/205832948-29416549-b73a-4cb1-b3cf-ec61366e5bf6.png)

![화면 캡처 2022-12-06 121727](https://user-images.githubusercontent.com/93081720/205833629-5998b411-9017-4ec8-86d7-3f2a8019e818.png)

#### 원인

`@Component`가 원인이었다.

#### 해결

`open` class로 등록하니 해당 에러가 완전히 사라지고 원하는 대로 동작하였다.

![image](https://user-images.githubusercontent.com/93081720/205833148-54f2114d-317e-4b33-837d-c40eb50c0fe2.png)

커스텀 필터는 `@Component`를 붙여서 스프링 Bean으로 등록해줘야하는 것으로 알고 있는데 어떤 이유로 해당 에러가 발생했는지 정확한 원인을 구글링해도 찾지는 못하였다. 필터가 중복 적용되는 것도 아니었는데 다음에 기회가 되면 원인을 찾을 수 있으면 좋겠다.

<br>

### Entity Builder 패턴 사용 및 @Id

#### 상황

다음과 같이 유저 인스턴스를 생성하려고 하는데 Id값이 누락되었다고 에러 메세지가 나오고 있었다.

![image](https://user-images.githubusercontent.com/93081720/205835467-9285856a-38fa-4856-92ff-91dcd407ac03.png)

#### 원인

id필드를 초기화하지 않았기 때문

#### 해결

id 필드를 0L로 초기화시켜주니 해결되었다. 인스턴스를 생성했을 때, 1번부터 생성되며, 그 이후부터 차례대로 생성됨을 확인할 수 있었다.

![image](https://user-images.githubusercontent.com/93081720/205835883-42397540-882b-4fdc-9cd0-8786bba419fe.png)

자바는 롬복의 Builder 메서드를 호출해서 Builder 패턴으로 객체를 생성할 수 있지만, 코틀린은 굳이 그럴 필요 없이 위와 같이 사용하면 된다. 대신 입력으로 주지 않는 값에 대해서는 default 값이 이미 정해져 있어야 하거나, 해당 매개변수만 받는 생성자가 있어야 한다.

<br>

### Unable to read JSON Value

#### 상황

SpringSecurity 접근 권한 테스트 도중 계속해서 403 Forbidden이 발생하고 있었다.

![Unable to read Json value](https://user-images.githubusercontent.com/93081720/205836612-11aa5d13-60c9-458b-a71d-cda5581f3d36.png)

#### 원인

찾아보니 JWT토큰을 검증하는 로직에서 올바른 토큰임에도 false값을 반환하고 있었다. 그래서 try구문 밖에서 로그를 찍어보니 `MalformedJwtException`이 발생하고 있었다.

#### 해결

`Bearer `를 없앤 순수한 토큰 값만 전달해야 했다. 확인해보니 token을 사용하기 위한 최상단에서 토큰을 가져왔을 때 `Bearer `를 접두사로 붙인 채로 계속 token을 전달하고 있었다.

![image](https://user-images.githubusercontent.com/93081720/205837074-d6ba267c-f803-419f-8fac-995ab66c6a31.png)

<br>

### 코틀린의 주/부 생성자 개념

#### 상황

AuthUserDetails에 주 생성자로 아래와 같이 user, accountNonExpired, accountNonLocked, credentialNonExpired, enabled, roles를 필드로 뒀었었는데, 

```kotlin
@Getter
class AuthUserDetails(
    var user: User,
    var accountNonExpired: Boolea,
    var accountNonLocked: Boolean,
    var credentialNonExpired: Boolean,
    var enabled: Boolean,
    var roles: List<GrantedAuthority>,
) : UserDetails {

}
```

다음과 같이 user하나만 넣고자 할 때, 에러가 발생하였음

![image](https://user-images.githubusercontent.com/93081720/205837726-48a8e6c9-092d-4a3b-b28c-ccc9d8e298f9.png)

왜 다른 필드 값은 넣지 않느냐는 것이었음

#### 원인

해당 이유는 선언한 모든 필드가 주생성자로 설정되어 있었기 때문임

#### 해결

그래서 부 생성자의 개념을 활용해서 생성자를 하나 더 만들려고 하였으나, 원인 모를 에러가 발생하여서 포기하였음

주 생성자는 user만 받게 하고, 나머지 필드들은 클래스 단으로 내리고 값을 초기화해주니 문제가 해결되었음

![image](https://user-images.githubusercontent.com/93081720/205837344-3e3b931a-d3bd-497b-874c-044d112ba425.png)