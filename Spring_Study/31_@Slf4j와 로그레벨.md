# 31_@Slf4j와 로그 레벨

## 1. @Slf4j

롬복(Lombok)의 어노테이션으로 해당 어노테이션을 클래스에 붙이면 컴파일 시점에 `static final Logger`필드를 자동으로 생성하여 로그를 찍는데 편의성을 가져다 준다.

- IDE 상

```java
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyClass {
    public void doSomething() {
        log.info("Hello, world!");
    }
}
```

- 실제 컴파일된 코드

```java
public class MyClass {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyClass.class);

    public void doSomething() {
        log.info("Hello, world!");
    }
}
```

@Slf4j를 붙이지 않고 로깅을 하려고 한다면 매번 모든 코드에 `private static final LoggerFactory.getLogger(클래스명.class)`를 선언해줘야만 한다. (개발자가 직접 Logger를 일일이 선언할 필요가 없다.)

## 2. 로그 레벨

로그는 레벨이 있고, 레벨마다 중요성이 다르다.

### 1. 로그 레벨을 구분하는 이유

중요한 로그와 그렇지 않은 로그를 구분하기 위함이다. 로그 레벨의 구분이 없다면 중요한 로그를 운영팀에서 빠르게 파악하지 못해 대응이 늦어지거나 적절한 조치를 취하지 못할 수도 있다.

또한 모든 로그를 남긴다면, 어떤 면에서 보면 낭비일 수도 있다. 에러 로그는 따로 구분하여 쌓는 방식을 택한다던가, 에러 로그는 슬랙, 문자, 메일 등으로 경고 알람을 보낸다던가, 경고 로그는 대시보드에 모니터링 알람을 보낸다던가, 인포 로그만 저장하는 식으로 로그 레벨별로 대응 전략을 다르게 취할 수 있다.

팀 내에서 어떤 상황에 어떤 레벨의 로그를 사용할지 기준을 정해두면 운영 효율을 증가시키는 효과를 가져온다. (로그 레벨 별 보관 기간, 보관 방식 등)

###  2. 로그 레벨

- `info`
  - 정상 처리 흐름 내에서 참고용 정보를 남기기 위한 목적
- `warn`
  - 정상 처리 흐름이나, 잠재적 문제 혹은 향후 장애로 이어질 수 있으니 미리 체크하기 위한 용도 혹은 단순 경고 목적
  - continue, break 등의 루프를 빠져나오거나 무시하는 코드 블럭에 남기기도 함
  - 예) 예상치 못한 입력, 성능 저하, 재시도 등
- `error`
  - 정상적인 코드 흐름이 아니고, 즉시 확인 및 조치가 필요한 상황 혹은 예외 상황에 대한 내용을 기록하기 위한 목적
  - 예외 메세지, 스택트레이스 등의 장애 원인 조치, 예외 발생 원인 등 원인 파악에 필요한 정보를 포함해야 함

