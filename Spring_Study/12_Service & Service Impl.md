# 12_Service & Service Impl
## 서비스 인터페이스를 만드는 이유?
대부분의 프로젝트를 보면 서비스 인터페이스를 만들고 해당 인터페이스를 구현한 서비스를 만든다.

해당 서비스 인터페이스와 서비스가 1:1로 대응되는데, 굳이 번거롭게 서비스 인터페이스를 만들어야할 이유가 무엇일지 궁금해졌다.

이유도 모른 채 무의식적으로 그냥 다른 사람들을 따라하는 것을 지양하고 싶었다.

결론부터 말하자면 꼭 서비스 인터페이스를 통해 서비스를 구현할 필요는 없다.

왜 사용하는지?, 어떻게 사용하는 것이 바람직한 것인지? 와 같이 인터페이스와 구현체를 분리한 설계를 통한 이점들과 이유와 근거에 대해서 설계를 한 개발자 당사자는 명확히 이해해야 한다.

### 1. OOP 인터페이스와 Loose Coupling
인터페이스를 사용하므로써 OOP의 다형성을 실현할 수 있다. 개발 코드를 수정하지 않고, 사용하는 객체를 변경할 수 있도록 해준다.

하나의 인터페이스를 구현하는 여러 구현체가 있고 기능에 따라 적절한 구현체가 들어가서 다형성을 줄 수 있다.

예시) 당근 마켓이라는 서비스에서는 일반 사용자도 존재하지만, 지역 게시판에 자신의 가게를 홍보할 수 있는 사장님으로 등록된 사용자도 존재
- MemberService : 회원 서비스 인터페이스
  - GeneralMemberServiceImpl : 일반 회원 서비스
  - OwnerMemberServiceImpl : 사장님 회원 서비스
  - ... : OOO 회원 서비스

=> 사용자 서비스는 일반 사용자들에 대한 서비스와 사장님으로 등록된 사용자들에 대한 서비스 등으로 구현을 달리 할 수 있으며, 서비스가 확장됨에 따라서 사용자의 유형도 얼마든지 확장될 가능성이 존재

비즈니스 로직의 확장 가능성 등을 염두해본다면 서비스 인터페이스를 만들고 서비스를 구현하는 것이 적절하다.

또한 클래스 간의 의존 관계를 줄여서 Loose Coupling을 실현할 수도 있다.

### 2. AOP
AOP와 트랜잭션은 서비스 인터페이스에서 처리한다.

Spring에서 JDK Dynamic Proxy 를 사용하여 AOP Proxy 를 만드는 만들기 위해 JDK Dynamic Proxy는 인터페이스 기반으로 프록시 객체를 만들게 되어 있다.

스프링에서 AOP를 구현할 때 JDK의 기본 프록시를 사용하는데, 이 프록시는 인터페이스 기반으로 동작하기 때문에 Service인터페이스를 만들어 사용한다.

예를 들어, 인터페이스가 있어야지 @Transactional 어노테이션이 동작하게 된다.