# 01_MSA란?

> MSA(MicroServiceArchitechture)

## 1. 소프트웨어 아키텍처

### Antifragile

저비용 저에러, 고효율의 IT시스템이 가지는 특성

#### 1. 오토 스케일링(Auto Scaling)

자동 확장성

자동으로 사용량에 따라 인스턴스의 대수를 늘림 => 성수기에는 서버의 대수를 늘리고, 비수기에는 줄임(자동)

#### 2. 마이크로서비스(MicroService)

매우 작은 단위, 많은 양의 서비스들이 독립적으로 운영, 배포됨

#### 3. 카오스 엔지니어링(Chaos Engineering)

시스템이 어떠한 불안정성, 불확실성 하에서도 안정적으로 작동하도록 설계된 것을 말함

#### 4. Continuous Deployment(CD)

지속적인 배포

<br>

### Cloud Native

#### 확장 가능한 아키텍처

- 시스템의 수평적 확장에 유연
- 시스템의 부하 분산, 가용성 보장
- 컨테이너 기반 패키지
- 지속적인 모니터링

####  탄력적 아키텍처

- 서비스 생성 - 통합 - 배포
- 비즈니스 환경 변화에 대응 시간 단축
- 분할된 서비스 구조
- 무상태 통신 프로토콜
- 서비스의 추가와 삭제를 자동으로 감지
- 변경된 서비스 요청에 따라 사용자 요청 처리(동적 처리)

#### 장애 격리(Fault Isolation)

- 특정 서비스에 장애 및 오류가 발생해도 다른 서비스에 영향을 주지 않음

<br>

### Cloud Native Application

**마이크로서비스**로 구현되어 **CI/CD**를 통해 전달되며, **DevOps**를 통해 관리되고, **컨테이너 가상화 기술**을 사용함

![image](https://user-images.githubusercontent.com/93081720/212450708-0270792d-6e4a-48a9-aab8-a53f9e063053.png)

<br>

## 2. Monolithic vs. Microservice

### Monolithic 

어플리케이션을 구성하는 모든 내용들을 하나로 통합해서 개발/운영하는 것

- 모든 비즈니스 로직이 하나의 어플리케이션에 패키지화 되어 서비스 됨
- 어플리케이션을 사용하는 데이터가 한곳에 모여 참조되어 서비스 됨
- 하나의 기능을 유지보수할 경우 나머지 모든 것들도 다시 빌드/배포되어야 함

<br>

### Microservice

함께 작동하는 작은 서비스들

어플리케이션 구성하는 모든 내용들을 각각 분리해서 개발/운영하는 것

- 모놀리식에 비해 유지보수/운영이 유리함 => 추가적인 개발이 필요할 때 해당 서비스만 추가 개발하면 됨

<br>

### Monolith vs. Front & Back vs. MSA

- Front와 Back을 나누는 것은 Monolith과 MSA의 중간 방식임

![image](https://user-images.githubusercontent.com/93081720/212476059-27424f19-4409-415f-adce-7f960514b2c1.png)

<br>

## 3. MSA

- Challenges
- Small Well Chosen Deployable Units
- Bounded Context
- RESTful
- Configuration Management
- Cloud Enabled
- Dynamic Scale Up And Scale Down
- CI/CD
- Visibility



무조건 모든 서비스가 MSA로 개발되어야 하는 것은 아님

- 공수, 비용, 서비스 경계, 변화 정도, 확장성, 외부 의존성, 서비스 간 의존성 등을 고려해야함



### MSA 표준 구조

![image](https://user-images.githubusercontent.com/93081720/212477659-e997bc48-c456-4ec4-9094-690335b578e3.png)

<br>

### MSA 기반 기술들

![image](https://user-images.githubusercontent.com/93081720/212477700-18b7dd24-3a80-4785-ab5a-375a76d4fb68.png)

<br>