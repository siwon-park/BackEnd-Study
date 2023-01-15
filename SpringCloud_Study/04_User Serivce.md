# 04_User Serivce

## 1. user-serivce

### 프로젝트 설정

![image](https://user-images.githubusercontent.com/93081720/212520387-2a0a3e00-abd6-47a9-83d5-43233d68f4e1.png)

springboot 버전은 2.4.1, Spring Boot DevTools, Lombok, Eureka Discovery Client, Spring Web을 종속성으로 추가해준다.

![image](https://user-images.githubusercontent.com/93081720/212520399-5cbbac49-db69-4a0c-b6e1-6bb25310e459.png)

<br>

### @EnableDiscoveryClient

`@EnableEurekaClient`로 등록해줘도 된다. 하지만 원조는 `@EnableDiscoveryClient`이다.

![image](https://user-images.githubusercontent.com/93081720/212520516-bccd8d4c-ecd5-4f26-a282-b3ff980229b9.png)

<br>

### application.yml

![image](https://user-images.githubusercontent.com/93081720/212521036-0d6c4770-64b3-4d82-a811-abf2e07591fe.png)

- service-url
  - defaultZone: `eureka 서버의 포트번호 + 주소`
    - 해당 서비스의 프로젝트를 eureka 서버 주소에 등록함

<br>

### 프로젝트 실행

Eureka Dashboard에서 확인해보면 user-service가 `up`상태로 정상 작동함을 확인할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/212521089-9d9acfcb-c322-49fc-9ee6-0cf248b636f7.png)

<br>

#### 동일한 프로젝트를 여러 개 실행하기

동일한 어플리케이션을 여러 개 실행하는 방법은 여러 가지가 있지만, 여기서는 IntelliJ를 사용해서 간단하게 실행할 수 있는 방법을 기록함

`-Dserver.port={포트번호}`

- 구성 편집 

![image](https://user-images.githubusercontent.com/93081720/212523576-e8107972-19d4-4dd0-8e71-5c667d0186d7.png)

- 복사할 어플리케이션 선택 후 구성 복사

![image](https://user-images.githubusercontent.com/93081720/212523614-5675d008-9dd4-4947-976c-da5b1fcea796.png)

- VM옵션 추가(옵션이 보이지 않을 경우 실행 옵션에 추가시켜줘야 함)

![image](https://user-images.githubusercontent.com/93081720/212523696-7742e878-14f6-4669-935b-2a552ef793c5.png)

![image](https://user-images.githubusercontent.com/93081720/212523835-520d4975-58f8-4b3d-9ccc-f4cc48fa8f29.png)

- 실행

![image](https://user-images.githubusercontent.com/93081720/212523800-a9ae02bf-b7e8-47b8-8a4d-9399beceb189.png)

![image](https://user-images.githubusercontent.com/93081720/212523813-d7631d30-b2c6-4893-9e95-e55a01929cb8.png)

<br>

#### 랜덤 포트를 사용

위와 같이 매번 포트 포워딩을 수작업으로 해주는 것은 비효율적일 수밖에 없다.

application.yml 파일에 server.port값을 `0`으로 설정하면 랜덤 포트를 사용하겠다는 의미이다.

- 5000번대 이상의 랜덤 포트로 포트 포워딩된다.

단, 랜덤 포트 사용 시, 기존에 수동으로 적용시켜줬던 포트를 해제해줘야한다.

![image](https://user-images.githubusercontent.com/93081720/212523863-29177a9b-ef3c-4fd7-ac6f-2364a7fb7d40.png)

그러나 포트 번호를 0번으로 바꾼다고 해서 인스턴스가 다중으로 실행되는 것은 아니다. 실행시켜보면 5만번 대 이상의 포트를 가진 인스턴스가 하나 실행되어 있음을 확인할 수 있다.

다음과 같이 `eureka.instance`옵션에 `instance-id`에 랜덤으로 값을 지정시켜줘서 인스턴스를 구분시켜줘야 다중으로 실행시킬 수 있다.

![image](https://user-images.githubusercontent.com/93081720/212524017-b86a275f-caf4-4150-be33-4eb1fed740ce.png)

![image](https://user-images.githubusercontent.com/93081720/212524117-85a3a3ad-f0d3-4e43-8bdb-b2ee93244bb9.png)

생성된 인스턴스 주소에 마우스를 호버하면 웹 브라우저 왼쪽 하단에 몇 번 포트로 연결되었는지 확인할 수 있다.

