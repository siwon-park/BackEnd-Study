## 26_@Value

> @Value 어노테이션

## 1. Springframework의 @Value와 Lombok의 @Value

둘 다 어노테이션으로 표기는 동일하지만 기능적으로 차이가 있다.

### 1) Springframework의 @Value

스프링프레임워크에서 제공하는 @Value 어노테이션의 경우 외부 설정 값(properties)을 불러와서 스프링 Bean으로서 주입하기 위해 사용한다.

즉, @Value 어노테이션을 활용하면 properties나 yml 파일에 지정한 값을 불러올 수 있다.

이 때, default value를 넣어서 사용할 수도 있고, 없이 사용할 수도 있다.

#### (1) 기본값 없이 사용할 경우

`@Value("${변수명}")`으로 사용하며, 반드시 properties 파일에 해당 변수의 값이 있어야 한다. 없을 경우 해당 값을 호출하는 코드를 Bean으로서 등록하는데 실패하여 어플리케이션이 실행되지 않는다.

![image](https://github.com/user-attachments/assets/c70596a6-a17e-470c-9207-80168c0a69c3)

![image](https://github.com/user-attachments/assets/110e4f2e-6d3e-4e74-8202-b750a433ece3)

위와 같이 properties에 값이 있어야 하며, 지정된 값으로 읽어온다.

<br>

#### (2) 기본값을 지정하여 사용할 경우

기본값을 지정하여 사용할 경우, `@Value("${변수명:기본값}")`형태로 사용한다.

이 때, properties 파일에 해당 변수명으로 지정된 값이 없어도 컴파일이나 어플리케이션 실행에 문제가 발생하지 않는다. 해당 키 값을 못 찾았지만 지정한 기본값이 있기 때문에 기본값을 불러와서 어플리케이션을 실행하게 된다.

![image](https://github.com/user-attachments/assets/5290425d-66a2-4061-a089-1c4bdce69d41)

위와 같이 작성한다면, properties에 해당 변수명으로 정의된 값이 있다면 해당 값을 불러오고, 없다면 @Value 어노테이션에 기본값으로 지정한 값을 가져온다.

※ 특이하게도 url의 기본 Root path를 "/"로 기본값으로 지정하고 싶다면 빈 값(Empty String)으로 둬야 한다. `@Value("${context.path:}")`

<br>

#### (3) PropertySourcesPlaceholderConfigurer

@Value 어노테이션이 동작하기 위해서는 PropertySourcesPlaceholderConfigurer가 스프링 빈으로서 등록이 되어 있어야 한다. 그렇지 않을 경우에는 사용 자체는 가능해도 값을 읽어오지 못해서 항상 null 값이 된다.

<br>

### 2) Lombok의 @Value

반면 롬복의 @Value 어노테이션은 불변 클래스를 생성하기 위해 사용되며, 해당 어노테이션을 붙이게 되면

- 클래스의 모든 필드를 final로 선언
- 모든 필드를 초기화하는 생성자 선언
- getter 메서드 생성
- toString(), equals(), hashCode() 메서드

를 자동으로 생성해준다. 보일러 플레이트 코드들을 자동적으로 생성해주는 역할을 한다.