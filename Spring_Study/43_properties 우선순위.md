# 43_Spring Properties 우선순위

> Spring Framework와 Spring Boot

### 1) 핵심

Framework와 Boot 둘 다 "**설정값 조회는 항상 리스트 앞에서 부터**"라는 규칙은 동일하다.

오버라이드(Override)라고 해서 나중에 읽은 값이 먼저 읽은 값에 덮어써지는 개념이 아니다. Spring은 여러 설정 소스를 순서가 있는 리스트 형태로 쌓아두고, "**설정값(프로필)이 리스트에서 몇 번째 자리에 위치해 있는가**"에 따라서 **항상 먼저 발견한 값을 그대로 사용**한다. (처음 값 발견 이후 탐색 중지)

- addFirst, addLast 메서드를 통해 설정을 리스트에 쌓는다. (오버라이드 != 덮어쓰기, 여러 층을 쌓아두고 조회한다)

<br>

## 1. Spring Framework

### 1) 설정 유입 경로

Spring Framework의 경우 같은 어플리케이션 안에서 프로퍼티가 쌓이는 경로는 크게 2가지이다.

| 경로              | 등록방식                                               | 예시                                                         |
| ----------------- | ------------------------------------------------------ | ------------------------------------------------------------ |
| 선언적 등록       | `@PropertySource("classpath:...")`                     | 클래스패스 (webapp 내부 `META-INF/*.properties`)에서 읽어서 등록 |
| 수동 지정 (local) | `PropertySourcePlaceholderConfigurer.setLocation(...)` | 소스 코드를 통해 특정 위치를 지정 (사실상 외부 주입)         |

- 로컬(local) 소스
  - `PropertySourcesPlaceholderConfigurer` 빈에 개발자가 직접 주입한 프로퍼티
- 환경 소스
  - configurer를 통해 지정하기 전에 이미 Spring의 Environment에 쌓여 있는 프로퍼티

### 2) 실행 순서 (빈 등록 순서)

`PropertySourcesPlaceholderConfigurer`는 `BeanFactoryPostProcessor`의 구현체입니다. 이 인터페이스를 구현한 빈은 스프링이 **다른 일반 빈을 생성하기 전에** 먼저 실행된다.

```
AbstractApplicationContext.refresh() 내부 순서
prepareBeanFactory(beanFactory)
invokeBeanFactoryPostProcessors(beanFactory)   ← 여기서 실행됨
registerBeanPostProcessors(beanFactory)
finishBeanFactoryInitialization(beanFactory)   ← 일반 빈들은 여기서 생성
```

즉, `@PropertySource`로 Spring Environment에 값이 쌓이는 것은 빈 정의 로딩 단계에서 이미 끝나 있고, postProcessBeanFactory 호출이 그 다음으로 일어나 설정값의 최종 병합을 확정 짓는다.

### 3) 설정 우선 순위 결정

`postProcessBeanFactory` 내부를 간략화하면 다음과 같다.

- 요약
  - setLocation으로 등록한 로컬 소스가 있을 경우: 1. 로컬 소스 → 2. 환경 소스
  - setLocation으로 등록한 로컬 소스가 없을 경우: 1. 환경 소스 → 2. 로컬 소스

```java
public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    MutablePropertySources propertySources = new MutablePropertySources();

    // 1) Environment에 이미 쌓인 소스들 (@PropertySource 등)
    propertySources.addLast(
        new PropertySourcesPropertySource("environmentProperties", environment.getPropertySources())
    );

    // 2) setLocation()으로 지정한 local 프로퍼티
    PropertySource<?> localPropertySource =
        new PropertiesPropertySource("localProperties", mergeProperties());

    // 3) localOverride 값에 따라 넣는 위치가 달라짐
    if (localOverride) {
        propertySources.addFirst(localPropertySource);   // 맨 앞 = 최우선
    } else {
        propertySources.addLast(localPropertySource);     // 맨 뒤 = 최후순위
    }

    processProperties(beanFactory, new PropertySourcesPropertyResolver(propertySources));
}
```

#### (1) Environment (환경 소스)

@PropertySource를 통해 Environment에 이미 쌓인 소스의 경우 `addLast`를 통해 리스트의 맨 마지막에 넣는 것을 볼 수 있다.

#### (2) localOverride (로컬 소스)

`PropertySourcesPlaceholderConfigurer.setLocation(...)`을 통해 지정한 로컬 프로퍼티가 있을 경우, localOverride가 true이기 때문에 `addFirst`를 통해 로컬 소스를 리스트에 맨 앞에 위치 시킨다.

##### ※ ★중요★

로컬 소스 properties가 여러 개일 경우, 내부적으로는 properties를 머지(merge)하는 과정이 있다.

```java
pspc.setLocations( // setLocations로 여러 properties를 읽어올 수 있다. (setLocation과 구분)
    new FileSystemResource("/opt/app/config/common.properties"),
    new FileSystemResource("/opt/app/config/go.properties")
);
pspc.setLocalOverride(true);
```

이후 내부적으로는 mergeProperties()를 통해 설정을 병합하는 과정이 존재한다.

```java
protected Properties mergeProperties() {
    Properties result = new Properties();
    // locations에 지정된 파일들을 순서대로 하나씩 이 result 안에 다 부어넣는다
    for (Resource location : this.locations) {
        Properties props = loadFrom(location);
        result.putAll(props);   // 같은 키면 그냥 덮어씀
    }
    return result;
}
```

여기서 정말 중요한 것은 **Map 자료 구조에 put으로 넣기 때문에 만약에 똑같은 설정키일 경우에는 마지막에 처리된 값으로 덮어써지게 된다.**

그래서 엄밀히 말해서 Spring Framework가 설정을 읽는 우선 순위는 먼저 읽는 것이 항상 우선한다라는 설명은 100% 맞는 설명은 아니게 된다.

### 4) 환경 소스가 여러 개일 경우

`@Profile` 없이 `@PropertySource`가 여러 개 동시에 활성화 되면, 내부적으로는 @PropertySource를 처리할 때마다 다음과 같이 `addLast()` 한 줄이 실행된다.

```java
environment.getPropertySources().addLast(새로_등록되는_소스);
```

따라서 A.properties, B.properties 이렇게 두 순서로 처리된다고 하면, 리스트에 쌓이는 순서는 다음과 같다.

```
A 처리 → addLast(A) → 리스트: [A]
B 처리 → addLast(B) → 리스트: [A, B]
```

이후 리스트의 맨 앞에서 설정을 읽기 때문에 Spring Framework의 경우 큐(FIFO) 방식처럼 동작한다.

### 5) 키 단위 병합

다만, 이렇게 설정값을 리스트 형태로 계층식으로 구성하는 것은 파일 전체를 교체하는 것이 아니라, 리스트 상에서 파일을 순서대로 읽되 키 단위로 병합하는 형식으로 동작한다. 우선순위가 높은 소스에 있는 키만 그 값으로 채택되고, 그곳에 없는 키는 다음 소스(우선순위가 낮은 소스)에서 찾는다.

즉, **먼저 찾은 키가 항상 등록되는 형태**이다.

<br>

### 6) 바인딩 방식

Spring Framework의 경우 `@Value("${...}")`와 같이 사용하여 값을 직접 한 줄씩 바인딩해줘야 한다. (키 이름은 반드시 설정 파일에 작성한 그대로 일치해야만 한다.)

```java
@Value("${db.url}")
private String dbUrl;

@Value("${db.max-pool-size:10}")
private int maxPoolSize;
```

- `@Value("${db.max-pool-size:10}")`의 경우 끝에 10은 키 값이 없을 때 폴백할 기본값을 지정한 것이다.

<br>

## 2. Spring Boot

설정값을 읽어오는 부분에 있어서도 Boot가 Framework에 비해 자동화, 편리성을 가지고 있는지 알 수 있다.

Boot 역시 Spring이기 때문에 매커니즘(원리) 자체는 Framework와 동일하다.

### 1) 우선순위 표준 순서

다만, Boot의 경우 우선순위 표가 존재하여 어플리케이션 시작 시 어떤 소스가 어떤 순서로 리스트에 들어갈지 프레임워크 차원에서 이미 정의해놓았다.

가장 큰 원칙은 "**커맨드라인/환경변수가 파일보다 우선, jar 밖이 jar 안보다 우선, applicaton-{profile}.설정이 applicaton.설정보다 우선한다.**"

| 우선순위 | 소스                                                         | 비고                      |
| -------- | ------------------------------------------------------------ | ------------------------- |
| 1        | 커맨드라인 인자(`--server.port=8088`)                        | 가장 우선                 |
| 2        | `SPRING_APPLICATION_JSON`과 같은 환경변수(`.env`) / 시스템 프로퍼티 |                           |
| 3        | ServletConfig / ServletContext 초기화 파라미터               | war 배포시                |
| 4        | JNDI 속성 (`java:comp/env`)                                  |                           |
| 5        | Java 시스템 프로퍼티 (`System.getProperties()`)              |                           |
| 6        | OS 환경변수                                                  |                           |
| 7        | jar 밖의 `application-{profile}`.설정(yml, properties)       | 활성 프로필 전용          |
| 8        | jar 안의 `application-{profile}`.설정(yml, properties)       |                           |
| 9        | jar 밖의 `application`.설정(yml, properties)                 | 프로필 지정없는 기본값    |
| 10       | jar 안의 `application`.설정(yml, properties)                 | 사실상 가장 낮은 우선순위 |
| 11       | `@PropertySource` / 코드 기본값                              | 가장 낮음                 |

### 2) 여러 프로필 사용 시

예를 들어, `spring.profiles.active=user,pgsql,active`로 띄웠고, `application-user.yml`, `application-pgsql.yml`,  `application-active.yml` 세 파일이 전부 같은 키 `db.url`을 갖고 있다고 가정.

이 경우에는 가장 마지막에 작성한 `active` 프로필에 있는 `db.url`값이 최종 채택된다.

그 이유는 Boot의 경우 여러 프로필을 처리할 때 각 프로필에 대해서 addLast가 아니라 addFirst로 처리하기 때문이다.

```
user 처리   → addFirst(user)   → 리스트: [user]
pgsql 처리  → addFirst(pgsql)  → 리스트: [pgsql, user]
active 처리 → addFirst(active) → 리스트: [active, pgsql, user]
```

**가장 마지막에 읽어서 값이 덮어쓰여졌기 때문에 그 값을 사용했다고 오해하면 안된다. 엄연히 내부적으로는 먼저 읽은 값에서 발견된 키값을 채택한 것이다. 원리는 바뀌지 않았다.**

이후 리스트의 맨 앞에서 설정을 읽지만, addFirst를 통해 마지막에 들어온 설정을 더 먼저 읽기 때문에 Spring Boot의 경우 스택(LIFO) 방식처럼 동작한다.

### 3) 외부 파일 사용 시

Spring Framework에서는 외부 파일을 사용하여 (로컬 소스), 그 값으로 설정값을 사용하고 싶다는 것을 개발자가 직접 코드로 localOverride를 구현해야 했다면, Boot의 경우 이러한 것을 표준 옵션으로 제공하고 있다.

```properties
# jar 밖 특정 경로를 추가로 읽되, 기존 application.properties 우선순위 규칙에 편입
spring.config.additional-location=file:/opt/app/config/

# 특정 파일을 명시적으로 import (Spring Boot v2.4+ Config Data API)
spring.config.import=file:/opt/app/config/override.properties
```

<br>

### 4) 바인딩 방식

Spring Boot의 경우에도 Framework와 마찬가지로 `@Value("${...}")`으로 한 줄씩 바인딩이 가능하지만, `@ConfigurationProperties` 어노테이션을 통해 통째로 하나의 객체에 바인딩하는 것도 가능하다.

```java
@Getter
@Setter
@ConfigurationProperties(prefix = "db")
public class DbProperties {
    private String url;
    private int maxPoolSize;
}
```

```yml
db:
  url: localhost:5432
  max-pool-size: 10
```

`@ConfigurationProperties(prefix = "...")`에서 prefix로 시작하는 키 값들을 이 클래스 객체에 전부 자동으로 맵핑시킬 수 있다.

다만, 이 때 relaxed binding 기능을 통해서 자동으로 맵핑해주기 때문에 표기(케이스)를 준수해줘야 한다. (자기마음대로 이상한 케이스를 사용하면 안된다는 의미)

```
max-pool-size ←→ maxPoolSize ←→ MAX_POOL_SIZE
```

