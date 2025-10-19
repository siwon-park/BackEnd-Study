# 23_Filter & Interceptor

> 필터(Filter)와 인터셉터(Interceptor)

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/3dbcf01b-86f4-47c3-9028-5869aa639810)

## 1. 필터(Filter)

`J2EE 표준 스펙 기능`으로 `디스패처 서블릿(Dispatcher Servlet)에 요청(Request)이 전달되기 전/후`에 url 패턴에 맞는 모든 요청에 대해 부가 작업을 처리할 수 있는 기능을 말한다.

디스패처 서블릿은 스프링의 가장 앞단에 위치하는 컨트롤러이기 때문에, 필터는 스프링의 범위 밖에서 동작하는 것이다.

즉 필터는 스프링 컨테이너가 아닌 톰캣과 같은 웹 컨테이너에 의해 관리되는 것이다.

그래서 필터는 `서블릿 필터(Servlet Filter)`라고 부르며, 필터를 구현하기 위해서는 `javax.servlet`의 필터 인터페이스를 구현해야 한다.

단, 스프링 빈으로 등록됨을 유의.

### 1) 필터의 동작 흐름

#### (1) 올바른 흐름

- 단일 필터
  - HTTP 요청 → WAS → 필터 → 디스패처 서블릿 → 컨트롤러
- 다중 필터
  - HTTP 요청 → WAS → 필터 1 → 필터 2 → 필터 3 → ... → 디스패처 서블릿 → 컨트롤러 

#### (2) 제한된 흐름

유효하지 않은 요청일 경우 디스패처 서블릿을 거치지 않고 필터 내에서 바로 응답을 반환한다.

- HTTP 요청 → WAS → 필터 → 유효하지 않은 요청에 대한 응답 반환

<br>

### 2) 필터 인터페이스

필터는  추가히기 위해서는 `javax.servlet`의 필터 인터페이스를 통해 구현해야 한다.

- init()과 destory()는 디폴트 메서드이기 때문에 굳이 반드시 구현할 필요는 없다. doFilter()는 반드시 구현해야 함.

```java
public interface Filter {
    public default void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException;

    public default void destroy() {}
}
```

#### (1) init

필터를 초기화하고 설정하는 메서드로, 웹 컨테이너가 실행될 때 한 번만 호출된다. 따라서 스프링 어플리케이션이 실행되면 최초에 한 번만 실행된다고 보면 된다.

필터 객체가 init 메서드를 통해 초기화되고 나면 이후의 요청들에 대한 처리는 `doFilter`에서 처리한다.

#### (2) ⭐doFilter

실제로 필터가 수행해야 하는 로직을 담은 메서드이다. url 패턴에 맞는 http 요청이 들어오면 해당 요청 및 응답을 가로채어 로직을 수행한 후, `FilterChain`의 doFilter를 통해 다음 대상(필터 혹은 서블릿)으로 요청을 전달한다.

- 필터의 doFilter 메서드의 파라미터에 FilterChain이 있다.
- chain.doFilter() 전 / 후에 필요한 처리 과정을 넣어주는 것이다.

```java
@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    // 요청 처리 로직
    
    // chain.doFilter를 하지 않으면 필터에 걸리고 나서 프로그램이 종료됨
    chain.doFilter(request, response);
    
    // 응답을 가로채는 로직
}
```

#### (3) destroy

필터 객체를 서비스에서 제거하고 사용하던 자원을 반납하기 위한 메서드이다. 웹 컨테이너에 의해 1번만 호출되며 일반적으로 스프링 어플리케이션이 종료될 때 한 번만 호출된다.

<br>

### 3) 필터의 등록

Filter는 스프링 빈으로서 등록되어 사용이 가능하며, 필터의 적용 순서를 config 클래스를 통해 조정할 수 있다.

※ Filter도 @Component로 스프링 빈으로 등록이 가능하지만 보통 다음과 같이 @Configuration을 통해 빈 메서드로 등록을 한다.

#### (1) FilterConfig

```java
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Myfilter1> filter1() {
        FilterRegistrationBean<Myfilter1> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MyFilter1());
        registration.addUrlPatterns("/*"); // 모든 요청에 대해 적용
        registration.setOrder(0); // 우선순위 적용 (낮은 숫자가 더 높은 우선순위)
        return registration;
    }

    @Bean
    public FilterRegistrationBean<Myfilter2> filter2() {
        FilterRegistrationBean<Myfilter2> registration = new FilterRegistrationBean<>();
        registration.setFilter(new Myfilter2());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return bean;
    }
}
```

#### (2) MyFilter1, 2

```java
public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter1 doFilter before");
        chain.doFilter(request, response); // 다음 필터 또는 서블릿으로 요청 전달 (다른 필터가 있어도 그 필터가 빈으로 등록되어 있지 않으면 호출하지 않음)
        System.out.println("MyFilter1 doFilter after");
    }
}
```

```java
public class MyFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MyFilter2 doFilter before");
        chain.doFilter(request, response);
        System.out.println("MyFilter2 doFilter after");
    }
}
```

#### (3) 서블릿 실행 및 진입 결과

- 서블릿 실행 → 필터1 진입 → 필터1 doFilter 실행 → 필터1 before 프린트 구문 실행 → 필터1 doChain 실행 → 필터2 진입 → 필터2 before 프린트 구문 실행 → 필터2 doChain 실행 → (추가 실행할 필터가 없음) → 필터2 after 프린트 구문 실행 → 필터2 종료 → 필터1 after 프린트 구문 실행 → 필터1 종료

```bash
MyFilter1 doFilter before
MyFilter2 doFilter before
MyFilter2 doFilter after
MyFilter1 doFilter after
```

<br>

## 2. 인터셉터(Interceptor)

스프링이 제공하는 기술로써, 디스패처 서블릿이 컨트롤러를 호출하기 전과 후에 요청 및 응답을 가로채서 부가 작업을 처리하는 기능을 수행한다.

웹 컨테이너 내에서 동작하는 필터와는 달리 인터셉터는 스프링 컨테이너 내에서 동작한다.

### 1) 인터셉터의 동작 흐름

※ /main의 경우 인터셉터가 없을 때의 흐름인 반면, /admin의 경우 인터셉터가 있을 때의 흐름이다.

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/01e39d50-0995-4f6e-b00d-08d84ad47295)

#### (1) 올바른 흐름

- 단일 인터셉터
  - HTTP 요청 → WAS → (필터) → 디스패처 서블릿 → preHandle → 컨트롤러 → 로직 수행 →  postHandle → 응답
- 다중 인터셉터
  - ※ 인터셉터가 여러 개일 때, 앞에 있는 인터셉터의 preHandle 메서드를 통과하고 나면 그 다음 인터셉터로 요청을 넘겨서 해당 인터셉터의 preHandle 메서드를 태우는 형태로 동작한다.
  - HTTP 요청 → WAS → (필터) → 디스패처 서블릿 → preHandle 1 →  preHandle 2 → ... → 컨트롤러 → 로직 수행 →  postHandle 1 → postHandle 2 → ... → 응답
  

#### (2) 제한된 흐름

잘못된 요청일 경우 preHandle에서 발견되어 응답을 반환한다.

- HTTP 요청 → WAS → (필터) → 디스패처 서블릿 → preHandle → 결함 발견 → 응답

<br>

### 2) 인터셉터 인터페이스

인터셉터를 추가히기 위해서는 `org.springframework.web.servlet`의 `HandlerInterceptor`인터페이스를 통해 구현해야 한다.

※ 필터가 웹 컨텍스트 내에서 동작하기 때문에 javax.servlet 패키지에서 인터페이스를 가져왔다면, 인터셉터는 스프링 프레임워크 내에서 동작하기 때문에 org.springframework.servlet에서 인터페이스를 가져왔음을 확인할 수 있다.

- 인터셉터의 경우 모든 메서드가 디폴트 메서드여서 implements를 하더라도 반드시 구현할 필요는 없다.

```java
public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable ModelAndView modelAndView) throws Exception {
    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        @Nullable Exception ex) throws Exception {
    }
}
```

#### (1) preHandle

컨트롤러를 호출하기 전에 실행되는 메서드. 요청 정보의 가공 및 추가 등 컨트롤러 이전에 처리해야 하는 전처리 작업을 수행한다.

- 예) 로그인 여부, 호출 권한 체크

반환 타입은 `boolean`이며, `true`가 반환되는 경우에만 후속 작업(다음 인터셉터 호출 및 컨트롤러 호출)을 이어가며 `false`의 경우에는 작업을 중단한다.

preHandle의 3번째 파라미터인 `handler`는 핸들러 맵핑(Handler Mapping)이 찾아준 컨트롤러 빈(Bean)에 맵핑되는 핸들러 메서드(Handler Method)라는 새로운 타입의 객체로서, `@RequestMapping`이 붙은 메서드 정보를 추상화한 객체이다.

#### (2) postHandle

컨트롤러가 요청 로직을 수행한 뒤에 호출되는 메서드로, 컨트롤러 이후에 처리해야 하는 후처리 작업이 있을 경우에 사용한다.

컨트롤러 하위 레벨에서 예외가 발생했을 경우에는 postHandle은 호출되지 않는다.

postHandle의 4번째 파라미터에는 ModelAndView 타입의 파라미터가 있는데, 최근에는 잘 사용되지 않는다.

- `@RestController` 혹은 `Controller + @ResponseBody` 조합의 REST 컨트롤러를 주로 사용하게 되면서, 이들이 개발자가 정의한 DTO, Entity 등으로 명확하게 응답을 반환하게 됨으로써 컨트롤러가 로직을 통해 반환한 명확한 응답을 인터셉터가 임의로 수정하는 것 자체가 좋은 접근이 아니라고 판단하여 이 때에는 인터셉터가 요청 결과를 후처리할 수 없다.
- Model과 View를 반환하던 시절에는 자주 사용되었겠지만 현재의 `REST API`형태의 어플리케이션 서비스들에서는 postHandle 로직의 사용은 제한적일 수밖에 없다.

#### (3) afterCompletion

응답 결과를 보낸 이후 해당 요청에 대한 작업들을 종료할 때 실행되는 메서드로, 주로 사용하던 자원의 반납이나 로그 기록 등의 목적으로 사용된다.

postHandle과 달리 컨트롤러 하위 레벨에서 예외가 발생하더라도 afterCompletion은 반드시 호출된다.

<br>

### 3) 인터셉터 등록

인터셉터를 등록하기 위해서는 @Component로 인터페이스 구현체를 스프링 빈으로 등록한 다음, `WebMvcConfigurer` 구현체를 통해서 인터셉터를 등록해줘야 한다.

#### (1) 인터셉터 구현 및 빈 등록

```java
@Component
public class MyInterceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor1 동작 preHandle() 메서드 호출");
        return HandlerInterceptor.super.preHandle(request, response, handler); // true면 컨트롤러로 진행, false면 요청 차단
    }

    /*
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    */
}
```

```java
@Component
public class MyInterceptor2 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor2 동작 preHandle() 메서드 호출");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

```

#### (2) WebMvcConfigurer 구현 및 인터페이스 등록

```java
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final MyInterceptor1 myInterceptor1;
    private final MyInterceptor2 myInterceptor2;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터 등록(addInterceptor 메서드를 호출한 순서대로 인터셉터 등록됨)
        registry.addInterceptor(myInterceptor1)
                .addPathPatterns("/api/**"); // 인터셉터를 적용할 URL 패턴 지정, /** : 모든 하위 경로 포함
        registry.addInterceptor(myInterceptor2)
                .addPathPatterns("/api/**");
    }
}
```

#### (3) 컨트롤러 호출 결과

인터셉터의 경우 `.addPathPatterns`로 인터셉터를 적용할 특정 URL 패턴을 적용하지 않는다면 모든 요청에 대해 인터셉터를 적용하게 된다.

```bash
// 필터를 먼저 호출하고 (sevlet 레벨), 인터셉터를 호출한다(spring context)
MyFilter1 doFilter before
MyFilter2 doFilter before
MyInterceptor1 동작 preHandle() 메서드 호출
MyInterceptor2 동작 preHandle() 메서드 호출
MyFilter2 doFilter after
MyFilter1 doFilter after
```

<br>

## 3. 요약

필터와 인터셉터는 관리되는 영역이 다르지만, 둘 다 스프링 빈으로 등록되어 관리는 가능하다.

- 필터

  - 웹 컨테이너(서블릿 컨테이너)에 의해 관리

  - 스프링 컨텍스트가 아니기 때문에 스프링의 예외 처리 X

  - Request/Response 객체 조작 가능 (조작 가능; 전혀 다른 객체로 변환)

    - ```java
      public class MyFilter implements Filter {
          public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
              // 개발자가 다른 request와 response를 넣어줄 수 있음
              chain.doFilter(new MockHttpServletRequest(), new MockHttpServletResponse());       
          }
      }
      ```

  - 용도:

    - 어플리케이션/서비스 전체를 통틀어 공통된 보안 사항 및 인증/인가 관련 작업
    - 모든 요청에 대한 로깅
    - 이미지/데이터 압축 및 문자열 인코딩
    - 스프링 어플리케이션과 분리되어야 하는 기능들

- 인터셉터

  - 스프링 컨테이너에 의해 관리

  - 스프링 컨텍스트에 포함되기 때문에 스프링의 예외 처리 가능

  - Request/Response 객체 조작 불가능 (조작 불가능; 전혀 다른 객체로 변환 불가)

    - ```java
      public class MyInterceptor implements HandlerInterceptor {
          default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
              // Request/Response를 교체할 수 없고 boolean 값만 반환할 수 있다.
              return true;
          }
      }
      ```

  - 용도:

    - 세부적인 보안 및 인증/인가 공통 작업
    - API 호출에 대한 로깅
    - 컨트롤러로 넘겨주는 정보의 가공