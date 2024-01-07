# 02_Spring Container



스프링 컨테이너는 

내 어플리케이션의 비즈니스 로직이 담긴 객체, 코드 (POJO)와

구성 메타 데이터(Configuration Metadata)를 조합하여 하나의 동작 가능한 어플리케이션으로 만들어준다.

이를 Bean이라는 것으로 구성하여 서버 어플리케이션 오브젝트로 만들어주는 것



스프링 컨테이너는 한 번 만든 오브젝트를 계속해서 사용한다. 딱 1번만 생성하고, 그 이후에는 다시 생성하지 않고 계속해서 사용함 => 싱글톤(Singleton)

(그러나 실제 싱글톤 패턴은 아니고, 마치 싱글톤처럼 동작하게 끔 보이는 것임)

- 서블릿 컨테이너의 서블릿이 요청을 하면 스프링 컨테이너는 해당 오브젝트를 매번 생성해서 리턴하는 것이 아니라, 이미 만들어져있는 1개의 오브젝트를 계속해서 리턴한다. (재사용)





Dependency Injection (의존성 주입)

- Spring IoC / DI Container

스프링 컨테이너는 어떤 오브젝트 A가 다른 어떤 오브젝트 B의 사용이 필요할 때, A가 필요한 의존 오브젝트 B를 생성하여 의존성을 주입시켜주는 역할까지 한다.

Assembler 역할



예를 들어, HelloController가 있다고 치자.

HelloController가 SimpleHelloService를 호출해서 사용하고 있을 때, HelloController는 SimpleHelloController를 의존하고 있다고 한다. (직접 의존)

만약 여기서 SimpleHelloController가 아니라 ComplexHelloController로 변경이 필요하다고 할 때, HelloController에는 소스 코드 레벨에서 변경이 필요할 것이다. 또한 SimpleHelloController의 메서드가 변경되어도 HelloController는 영향을 받는다.

이를 해결하기 위해 HelloService라는 인터페이스를 만들고 이를 구현한 SimpleHelloController와 ComplexHelloController를 사용한다면 적어도 이제 더 이상 소스 코드 레벨에서는 변경이 필요 없을 것이다. 타입이 HelloService이기만 하면 이를 구현한 어떤 클래스를 불러와도 되기 때문이다.

하지만 소스 코드 레벨에선 변경이 필요없다 해도, HelloController는 HelloSerive를 구현한 어떤 클래스 오브젝트를 호출해서 사용해야 할지에 대해 알 수 없다.

이 작업을 해주는 과정이 Dependency Injection이라 하고, 이러한 DI 작업을 해주는 것이 Assembler이다.

외부에서 생성한 오브젝트를 다른 오브젝트에서 사용할 수 있도록 의존성을 주입해주는 것이다.

- 생성자를 통해 주입(의존성을 사용할 클래스 오브젝트를 생성할 때 파라미터로 받음)
- 팩토리 메서드로 빈을 생성하여 파라미터로 주입
- 의존성을 사용할 클래스 오브젝트의 프로퍼티에 해당 의존성 오브젝트를 정의하고, setter 메서드를 통해 호출하여 주입



