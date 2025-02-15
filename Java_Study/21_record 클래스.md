# 21_record 클래스

> 불변 객체(immutable)를 쉽게 생성할 수 있도록 도와주는 클래스 (jdk 16버전부터 정식 등장)

## 1. record 클래스 이전

이전에는 request, response와 같은 DTO를 만들기 위해서 다음과 같이 사용하였다.

### 1) 직접 코딩

클래스를 선언한 다음 직접 필드를 선언하고, 생성자를 만들고 필요한 메서드를 작성하여 사용하였다.

![image](https://github.com/user-attachments/assets/ff5617a2-a636-482c-8139-6557154e8afe)

<br>

### 2) @Data 어노테이션

롬복에서 제공하는 어노테이션으로 @Getter, @Setter, @ToString, @RequiredArgsConstructor 등의 어노테이션을 모두 합쳐놓은 기능을 하는 어노테이션이다. (단, 만약에 각 어노테이션별로 특정 추가 옵션이 필요할 경우 @Data가 아니라 필요한 모든 어노테이션을 명시해줘야 한다.)

![image](https://github.com/user-attachments/assets/10371d2c-e73b-4154-a2d7-72a2046f7c0e)

<br>

### 3) 단점

#### (1) 보일러플레이트 코드

IDE의 도움을 받을 수도 있지만, 데이터 클래스의 필드를 추가할 때 기존 코드를 수정하고 추가하는 등의 동일하고 지루한 프로세스를 반복해야 한다.

toString, equals, hashCode 등에 대해 오버라이딩 했다면 더욱 그렇다.

#### (2) 모호한 클래스의 목적

해당 클래스가 DTO와 같은 데이터 클래스임을 알려면 패키지명(dto), 클래스명(requestDto, responseDto)으로 명시적으로 선언을 해놓거나 어노테이션 등으로 구분해야 한다.

<br>

## 2. record 클래스 이후

`record`라는 클래스 타입으로 선언하기만 하면 된다.

![image](https://github.com/user-attachments/assets/81e4fa5e-26df-4501-a9b7-ed0a96784ede)

record 클래스를 사용하게 되면 컴파일 타임에 컴파일러에 의해 아래 내용들이 자동으로 구현된다. (IDE를 통해 컴파일된 바이트 코드를 뜯어보면 확인 가능하다.)

- 필드 캡슐화(private final 선언)
- 생성자, getter, equals, hashCode, toString 메서드
  - 단 getter 메서드의 경우 get필드명()과 같은 형태가 아니라 필드명()으로 호출하여 사용한다.
  - setter가 없어서 필드를 선언하면 변경하지 못하기 때문에 불변객체, 데이터 클래스로서의 역할을 확실하게 수행할 수 있다.

단순히 record로 선언함으로써 불변 객체를 쉽게 생성하여 다룰 수 있고, 기존의 단순하고 지루한 보일러플레이트 코드를 작성하는 프로세스가 사라졌다.

또한 record 클래스가 기본적으로 제공해주는 메서드들은 재정의가 가능하다. (직접 선언해서 재정의하여 사용 가능)

### 1) 제약사항

#### (1) record 클래스는 상속 불가

record 클래스는 모든 필드를 final로 선언하기 때문에 상속이 불가하다.

따라서 이 때문에  record 클래스는 JPA의 Entity로 사용이 불가하다.

JPA는 지연 로딩(lazy fetch) 방식을 사용할 때 프록시 객체를 생성하여 이를 통해 작업을 처리하는데, 프록시 객체는 원본 객체를 상속하여 생성된 확장 클래스이다. 하지만 record 클래스는 상속이 불가하기 때문에 프록시 객체를 생성할 수 없다. 그래서 Entity로 사용이 불가하다.