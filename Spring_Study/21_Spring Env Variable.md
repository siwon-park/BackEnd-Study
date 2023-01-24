# 21_Spring Env Variable

스프링부트에서 환경 변수(구성 정보) 값 읽어오기

`application.yml`파일에 다음 예시와 같이 설정 정보 변수를 정의했을 때, Springboot 프로젝트에서 해당 변수값을 읽어오는 방법이 있다.

- 예시) `greetings.message` 변수를 정의

![image](https://user-images.githubusercontent.com/93081720/214344912-7d6238cf-74ce-4288-9bd7-d876671329fc.png)

<br>

## 1. @Value

### 설정 및 사용

사용할 곳에서 바로 `private 변수`로 선언하고`@Value`로 값을 불러와도 된다.

그런데 아래와 같이 클래스로 분리해서 의존성 주입을 통해 사용하는 것이 재사용성 측면에서 더 좋다.

#### 클래스 생성

`@Component`와 `@Value`어노테이션을 사용한다.

`@Component`는 해당 클래스를 Spring Bean으로 등록하기 위함이고, `@Value("${변수명}")`형태로 작성해서 `application.yml`에 작성한 변수값을 읽어온다.

![image](https://user-images.githubusercontent.com/93081720/214344840-b4e7ce9f-e100-4274-a0b9-602633b1bd76.png)

<br>

#### 의존성 주입

사용할 곳에서 해당 클래스를 의존성 주입한다.

![image](https://user-images.githubusercontent.com/93081720/214345411-9805aed1-1d22-44dd-b706-280ad675f471.png)

<br>

#### 사용

`@Data`를 적용했으므로 getter 메서드를 통해서 해당 값을 불러온다.

![image](https://user-images.githubusercontent.com/93081720/214346056-93c6102d-ccee-4b0d-8d8c-ee37f8c84fa8.png)

<br>

## 2. Environment

### 설정 및 사용

#### import

`org.springframework.core.env.Environment`에서 Environment 인터페이스를 불러온다.

![image](https://user-images.githubusercontent.com/93081720/214346608-3e41b220-3207-4386-94cb-1f23fad0e550.png)

<br>

#### 의존성 주입

사용할 곳에서 Environment를 의존성 주입을 해준다

![image](https://user-images.githubusercontent.com/93081720/214346976-fb13742c-ab61-4292-8bc7-ebcfd91ed1a5.png)

<br>

#### 사용

`.getProperty()`라는 메서드를 사용해서 `application.yml`에 작성한 변수값을 불러온다.

일반적으로 `env.getProperty("변수명")`과 같이 작성하여 사용한다.

![image](https://user-images.githubusercontent.com/93081720/214347276-bb1444c4-c36f-441f-8445-e0e18c0c3633.png)