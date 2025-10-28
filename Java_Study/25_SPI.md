# 25_SPI (Service Provider Interface)

> 프레임워크가 외부 구현을 런타임에 발견/사용하기 위한 표준 방식

## 1. 개념

Java의 플러그인 아키텍처 매커니즘으로, 인터페이스와 구현체를 분리해서 프레임워크가 구현체(플러그인, 라이브러리, 드라이버 등)을 런타임에 분리해서 교체가 가능하도록 하는 설계 기법이다.

인터페이스와 구현을 분리하는 것이 핵심이며, 이렇게 함으로써 기존 코드의 변경 없이 구현체만 추가/교체하면 추가적인 기능 제공이 가능해진다. (OOP의 다형성)

### 1) 예시

- 이미지 처리 라이브러리를 만든다고 가정.
  - 다양한 이미지 포맷(JPG, PNG, GIF 등)을 지원해야 함.
  - 기존에 웰노은 포맷까지는 하드 코딩(?)한다고 쳐도 이후에 추가되는 새로운 포맷은 어떻게 처리해야 할까?
  - 코드를 수정하는 작업은 번거롭다. 하지만 만약에 이를 인터페이스의 구현체로 받는다면?
- DB 드라이버
  - DB 드라이버 역시 마찬가지로 DBMS 종류가 다양한데, 스프링 프레임워크에서는 jar 파일을 import해오면 사용할 수 있다.
  - 이 때 내부적으로는 스프링, java와 호환되는 인터페이스를 구현한 구현체를 DBMS쪽으로부터 제공받는 것이다.
    - 예를 들면 DataBaseDriver라는 인터페이스가 있고, connect(), execute(), disconnect(), getDriverName() 등을 구현한 구현체를 활용하는 것이다.

<br>

### 2) API vs. SPI

두 개념은 방향성이 완전히 다른 개념이다.

#### (1) API

- 라이브러리에서 클라이언트에게 제공하는 인터페이스 (라이브러리 → 사용자)
- 라이브러리에서 제공해주는 것을 사용자가 어떻게 사용할지 정의해서 사용
  - 라이브러리: "이 메서드, API를 호출하면 우리는 이런 형태의 결과를 줄거예요."
  - 사용자: "API 결과가 저런 형태이니 이렇게 사용하면 되겠군."


#### (2) SPI

- 라이브러리가 구현체에게 요구하는 인터페이스 (라이브러리 ← 인터페이스 구현체)
- 외부 제 3자의 시스템이 우리 시스템과 어떻게 통합될지 정의. 라이브러리 측에서 정의한 인터페이스를 구현한 구현체를 받아서 통합.
  - 라이브러리: "외부 서비스 제공자 여러분, 저희가 정의한 이 인터페이스를 구현하시면 저희 시스템의 어떤 부분과 호환될 수 있어요."
  - 외부 서비스 제공자: "해당 시스템을 사용하기 위해서는 이 인터페이스를 구현해야 하는군."


 <br>

## 2. 동작

### 1) 핵심 구성 요소

#### (1) Service

변하지 않는 핵심 기능. 사용자가 아무런 값을 넣지 않아도 기본적으로 실행되는 기능. 외부 리소스(jar)에 의해서 바뀌지 않는 기능.

#### (2) SPI; Service Provider Interface

핵심 기능을 확장하기 위한 최소한의 인터페이스 명세서.

만약 기능을 확장하고 싶다면 해당 인터페이스를 구현한 구현체를 만들어서 제공해야 함.

#### (3) SP; Service Provider

SPI의 구현체. 기존 코드의 변경 없이 확장된 기능을 제공하는 리소스

#### (4) 그 외

- 서비스 로더: `java.util.ServiceLoader`클래스; 구현체를 찾아주는 역할
- 설정 파일: `META-INF/services/` 디렉토리에 위치한  설정 파일

<br>

### 2) 과정

#### (1) SPI 정의

서비스가 제공할 기능을 정의한 인터페이스를 개발

```java
public interface MessageService {
    void sendMessage();
}
```

#### (2) SPI 구현체 개발

SPI를 구현한 구현체를 개발

```java
public class AlertMessageService implements MessageService {
    @Override
    public void sendMessage() {
        // .. 구현 ..
    };
}

public class CommonMessageService implements MessageService {
    @Override
    public void sendMessage() {
        // .. 구현 ..
    }
}
```

#### (3) 설정 파일 생성

`META-INF/services/` 디렉토리 하위에 `인터페이스의 패키지 경로`의 이름으로 설정 파일을 생성하고 구현체의 클래스 패키지를 작성한다.

- ex) META-INF/services/com.playground.MessageService

```properties
# 파일명: com.playground.MessageService
com.playground.AlertMessageService
com.playground.CommonMessageService
```

#### (4) 서비스 로더를 통해 구현체를 로딩

```java
import java.util.ServiceLoader;
import com.playground.MessageService;

ServiceLoader<MessageService> loader = ServiceLoader.load(MessageService.class);
for (MessageService service : loader) {
    service.sendMessage();
}
```



