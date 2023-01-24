# 06_Spring Cloud Config

## 1. Spring Cloud Config

### configuration-service

마이크로 서비스가 가지고 있어야할 구성정보 파일(application.yml)에 대한 서비스

구성 정보 파일이 바뀔 때마다 어플리케이션이 다시 빌드/배포 되어야 한다면 비효율적임

이를 개선하기 위해 외부에 있는 시스템을 이용해서 구성 파일을 관리하는 방법이 있음

<br>

### Spring Cloud Config

- 분산 시스템에서 서버, 클라이언트 구성에 필요한 설정 정보(application.yml)를 외부 시스템에서 관리
- 하나의 중앙화된 저장소에서 구성요소 관리 가능
- 각 서비스를 다시 빌드하지 않고, 바로 적용 가능
- 어플리케이션 배포 파이프라인을 통해 `DEV` - `UAT` - `PROD` 환경에 맞는 구성 정보 사용
  - 개발 환경에 맞는 설정 정보가 있고, 프로덕션 환경에 맞는 설정 정보가 있어 상황에 따라 달리 적용 가능
  - ex) application-dev.yml, application-prod.yml

![image](https://user-images.githubusercontent.com/93081720/214056138-2c1c61ff-1a11-4d3c-ba3e-66601b397116.png)

<br>

### yml 파일 간 우선 순위

![image](https://user-images.githubusercontent.com/93081720/214059915-792789b2-4171-446f-94a5-1295d697275b.png)

- 작성 예시
  - application.yml
  - application-user-service.yml
  - application-user-service-dev.yml
- 프로파일 및 설정에 따라 원하는 yml 파일을 불러올 수 있음
- 프로파일을 명시하지 않을 경우 default 프로파일 값을 불러옴

<br>

### yml 파일 생성 및 로컬 레포지토리 업로드

다른 폴더에 ecommerce.yml이라는 파일을 만들고 아래의 내용을 작성한다.

![image](https://user-images.githubusercontent.com/93081720/214065759-fcc8bd80-01e4-4dc1-91f8-956f9e625f0c.png)

깃과 연동된 폴더를 하나 만들고 commit까지만 진행함

#### ※ 주의점

기존에 쓰던 git repo를 사용하지 않고 새로운 repo를 만들어서 연결한 다음 사용하는 것을 권장함

또한 해당 repo에 yml과 관련된 파일을 push하지 말 것

왜냐하면, 로컬에 있는 파일을 수정하고 commit까지만 하고나서 config-server를 재가동하면 로컬 repo가 master보다 앞서 있다면서 원격 repo에 있는 파일을 가지고 와서 roll back해버리기 때문임 

![image](https://user-images.githubusercontent.com/93081720/214207393-88736790-790c-4619-bcc8-d507e77b356a.png)

<br>

### 프로젝트 생성 및 세팅

#### 프로젝트 세팅

![image](https://user-images.githubusercontent.com/93081720/214062129-0ddef992-e361-4abb-8ee6-caf9b6b6c888.png)

- springboot 버전은 2.4.1로, dependency는 Config Server만 추가함

![image](https://user-images.githubusercontent.com/93081720/214062291-1ac85ca1-4a39-4d22-898f-11e85496c14a.png)

- Application 파일에는 `@EnableConfigServer`를 추가함

![image](https://user-images.githubusercontent.com/93081720/214062841-4278d5eb-a775-4c83-b99e-25f95fa9d5f7.png)

<br>

#### application.yml 파일 설정

해당 파일은 config-service의 application.yml임

![image](https://user-images.githubusercontent.com/93081720/214064323-5bb0a26e-dc0f-47a3-b215-56c5f86c2885.png)

- uri에는 local의 git repository 주소를 작성한다.
  - git commit 후 push를 하면 remote에 올라가고, commit까지만 하면 local 저장소에 올라간 상태이다.
  - bash에 `pwd`를 입력하면 현재 디렉토리가 나오는데, 이 때 `/c`를 뺀 주소로 작성해준다.(안 그러면 에러남)

![image](https://user-images.githubusercontent.com/93081720/214064679-13bfdc3e-7e58-4fc8-8636-efd9d6dbc029.png)

- 성공 시, localhost:8888로 들어갔을 때, commit했던 .yml파일의 이름 + default를 입력하면 됨
  - yml파일이 로컬 레포지토리에 제대로 안 올라갔다면 아래와 같이 나옴

![image](https://user-images.githubusercontent.com/93081720/214065016-7e4071b9-1ef2-4332-b594-d55ca868f750.png)

- 성공했다면 아래 사진과 같이 나와야함

![image](https://user-images.githubusercontent.com/93081720/214068205-f2b7075e-35a8-4714-92a5-64588e0c8315.png)

<br>

Spring Cloud Config와 연결하기 위해서 해당 마이크로 서비스에

`spring-cloud-starter-config`와 `spring-cloud-starter-bootstrap` dependency를 추가한다.

![image](https://user-images.githubusercontent.com/93081720/214201709-9b6131fe-9b81-4066-9c95-6cc95c08dc55.png)

<br>

#### bootstrap.yml 파일 설정

다음과 같은 설정 내용을 가진 bootstrap.yml 파일을 작성한다.

![image](https://user-images.githubusercontent.com/93081720/214207872-bfe29cf2-eec2-4526-86c6-3f96e2fe5621.png)

<br>

## 2. Springboot Actuator

- application 상태, 모니터링
- Metric 수집을 위한 Http End Point 제공([공식문서](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints) 참조)

### dependency 설정 추가

![image](https://user-images.githubusercontent.com/93081720/214208079-12c742c8-f569-4e03-823e-359b03a75c9a.png)

<br>

### application.yml 파일 설정 내용 추가

actuator를 사용하기 위해 해당하는 마이크로 서비스의 application.yml 파일에 설정을 추가한다.

![image](https://user-images.githubusercontent.com/93081720/214207985-ebf5a6ce-b5af-4459-9903-93f59cfe0e09.png)

<br>

### refresh 요청하기

위의 설정을 마쳤다면, 이제 매번 ecommerce.yml 파일을 commit할 필요 없이

`해당 포트 번호에 해당하는 주소 + /actuator/refresh`로 `POST`요청을 보내면 자동으로 수정 내용을 반영해준다.

- token.secret에 대한 내용만 바꾸고 refresh 요청을 했을 때의 POST맨 화면

![image](https://user-images.githubusercontent.com/93081720/214208442-54b6d3b1-bc61-495b-948a-e541598ef3e7.png)
