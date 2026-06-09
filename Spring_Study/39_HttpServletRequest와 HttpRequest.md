# 39_HttpServletRequest와 HttpRequest

> Spring에서의 HTTP 요청 처리: 수신과 송신의 구분 (들어오는 요청과 나가는 요청)

## 1. HttpServletRequest

서블릿 컨테이너 환경(Tomcat, Jetty 등)에서 HTTP 요청을 다루는 표준 인터페이스.

### 1) 특징

- 서버측에서 **들어온 요청(수신 요청)**을 처리하기 위한 것
  - 내가(서버가) 요청을 처리하기 위해서 파라미터로 받아서 사용
- `getParameter()`, `getSession()`, `getCookies()` 등의 서블릿 환경에 특화된 메서드 제공
- Spring MVC의 컨트롤러(Contoller)의 메서드 파라미터로 주로 사용됨
- 블로킹 I/O 기반

<br>

## 2. HttpRequest

`java.net.http.HttpRequest`와 `org.springframework.http.HttpRequest`

두 클래스는 이름은 같지만 서로 다른 패키지의 별개 타입이다.

### 1) java.net.http.HttpRequest

JDK 11+ 표준 라이브러리로 HTTP Client API의 클래스이다.

클라이언트가 요청을 보낼 때 사용하는 **송신 전용** 객체이다.

- 내가 요청을 보내고자 할 때, 송신 용도의 요청을 만들 때 사용하는 객체이다.

불변(immutable), Builder 패턴, 동기/비동기를 모두 지원한다.

```java
// 요청 생성(빌더 패턴)
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/data"))
    .header("Content-Type", "application/json")
    .GET()
    .build();

// 요청 보내기
HttpResponse<String> response = HttpClient.newHttpClient()
    .send(request, BodyHandlers.ofString());
```

<br>

### 2) org.springframework.http.HttpRequest

SpringFramework가 정의한 추상 인터페이스이다.

HTTP 메서드와 URI, 헤더 등 요청의 공통 속성만 정의되어 있다.

**송/수신 모두에 사용**되며, 이는 하위 타입의 인터페이스에 따라 갈라진다.

- `ClientHttpRequest` → 내가 보내는 요청 (송신, RestTemplate, RestClient/WebClient에서 사용)
- `ServerHttpRequest` → 내가 받는 요청 (수신, 주로 WebFlux에서 사용)

블로킹 또는 리액티브 I/O를 지원한다.

<br>

## 3. Interceptor에서

어떤 인터셉터에서는 파라미터로 HttpServletRequest를 받고, 다른 어떤 인터셉터에서는 HttpRequest를 받는데, 그 차이도 들어오는 요청을 가로채느냐(HandlerInterceptor), 나가는 요청을 가로채느냐(ClientHttpRequestInterceptor)의 차이다.

|               | HandlerInterceptor        | ClientHttpRequestInterceptor   |
| ------------- | ------------------------- | ------------------------------ |
| 방향          | 들어오는 요청 (서버 수신) | 나가는 요청 (클라이언트 송신)  |
| 가로채는 대상 | 사용자 → 내 서버          | 내 서버(클라이언트) → 외부 API |
| 요청 객체     | HttpServletRequest        | HttpRequest (spring)           |
| 사용처        | Spring MVC                | RestTemplate                   |

ClientHttpRequestInterceptor가 HttpServletRequest가 아니라 HttpRequest를 받는 이유는, 여기서 다루는 요청이 Servlet 컨테이너가 받은 요청이 아니라 외부로 보내려고 하는 요청이기 때문이다.

외부로 보내려는 요청이다보니 서블릿이 제공하는 기능이 필요 없는 더 가벼운 추상화인 HttpRequest를 사용하는 것이다.

### 1) 사용 예시

#### (1) ClientHttpRequestInterceptor

- 인터셉터 구현

```java
public class AuthHeaderInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, 
                                         byte[] body,
                                         ClientHttpRequestExecution execution) 
                                         throws IOException {
        request.getHeaders().add("Authorization", "Bearer " + getToken());
        request.getHeaders().add("X-Request-ID", UUID.randomUUID().toString());
        
        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        log.info("{} {} -> {} ({}ms)", 
            request.getMethod(), request.getURI(), 
            response.getStatusCode(), System.currentTimeMillis() - start);
        
        return response;
    }
}
```

- 빈 등록

```java
@Bean
public RestTemplate restTemplate() {
    RestTemplate rt = new RestTemplate();
    rt.getInterceptors().add(new AuthHeaderInterceptor());
    return rt;
}
```

<br>

## 4. 정리/요약

- Spring Boot + Tomcat 환경에서 들어온 요청을 다룬다 → HttpServletRequest
- Spring WebFlux(리액티브) 환경의 요청을 다룬다 → ServerHttpRequest (Spring의 HttpRequest 계열)
- 외부 API를 호출하고 싶다 → java.net.http.HttpRequest 또는 Spring의 RestClient/WebClient