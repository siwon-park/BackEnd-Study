![image](https://user-images.githubusercontent.com/93081720/211854738-60911ea9-80cd-486e-87f5-ba681e796d20.png)

# 10_스프링과 스프링부트의 차이점

## 1. Spring

> 자바 기반 어플리케이션 개발을 위한 오픈소스 경량 프레임워크

![image](https://user-images.githubusercontent.com/93081720/211850356-c717870d-fe7f-4b65-ae8d-8e989713732e.png)

### 특징

- 자바 객체와 라이브러리들을 관리해주며, 톰캣과 같은 WAS(Web Application Server) 가 내장되어 있어 자바 웹 어플리케이션을 구동할 수 있음
  - WAS(Web Application Server): 웹 서버와 웹 컨테이너가 합쳐진 개념으로, 웹 서버 단독으로는 처리할 수 없는 데이터베이스 조회나 다양한 로직 처리가 필요한 동적 컨텐츠를 제공함. 서블릿 컨테이너라고도 함.
- 경량 컨테이너로 자바 객체를 Spring에서 직접 관리함. 객체의 생성 및 소멸과 같은 생명 주기(Life cycle)을 관리하며, Spring 컨테이너에서 필요한 객체를 가져와 사용합니다.
- 제어의 역전(IoC), 의존성 주입(DI), 관점지향프로그래밍(AOP)

<br>

## 2. Springboot

> Spring 사용을 위한 설정을 자동화하여 더 편리하게 Spring을 사용할 수 있게 해주는 프레임워크

Spring의 많은 부분을 자동화하여 사용에 편리성을 더한 Spring 프레임워크

### 특징

- Springboot는 내부에 Tomcat이 포함되어 있어 Embed Tomcat을 사용하기 때문에 따로 Tomcat을 설치하거나 버전 관리를 해야 하는 번거로움을 덜어준다.
- `spring-boot-starter-%`를 통해 의존성(dependency) 관리 및 자동화를 시켜준다.
  - springboot 사용에 필요한 라이브러리를 손쉽게 추가 가능하다.
  - JPA가 필요할 경우 `build.gradle`에 `spring-boot-starter-jpa`만 추가해줘도 적용이 가능하다.
- Spring에서는 설정해줘야 했던 XML 설정을 하지 않아도 된다.
- `.jar`파일을 이용해 손쉽게 어플리케이션 배포가 가능하다.