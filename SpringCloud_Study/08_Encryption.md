# 08_Encryption

## 1. 대칭키(Symmetric Encryption)

암호화, 복호화 할 때 똑같은 키를 사용함

싱글 쿼테이션(') + 중괄호({}) + cipher라는 문자열을 활용하여 암호화된 데이터라는 것을 알려줘야함

- 이를 붙이지 않을 경우 그냥 문자열로 인식됨
- ex) `'{cipher}abcdefts12323123....sdgg'`

### 사용

#### 대칭키 등록

config-service의 bootstrap.yml에 대칭키를 등록

![image](https://user-images.githubusercontent.com/93081720/214608677-7712541c-2ff0-496e-9c94-d4b6c591189b.png)

<br>

#### POSTMAN 테스트

- http://localhost:8888/encrypt에 Text에 원하는 문자열을 작성해서 `POST`요청을 보내면 대칭키로 암호화된 값이 나온다.

![image](https://user-images.githubusercontent.com/93081720/214607579-647e1825-cb16-47b4-b51a-689d841a410f.png)

- 암호화된 해당 값을 http://localhost:8888/decrypt에 Text로 작성하여 `POST`요청을 보내면 대칭키로 복호화한 값을 얻을 수 있다.

![image](https://user-images.githubusercontent.com/93081720/214608255-03137009-2bd5-4adf-8e73-405cb1d0d25f.png)

<br>

#### 적용

예를 들어, 암호화 시킨 값을 넣어야 할 설정 정보가 마이크로 서비스에 있을 경우, 해당 서비스에서 사용하는 설정 정보를 config-service가 관리하는 설정 정보로 .yml파일로 작성하고 해당 마이크로 서비스에서 bootstrap.yml을 통해 값을 불러와서 사용한다.

- 예) users-service에 있는 DB 설정 정보를 config-service가 관리하는 users-service.yml 파일을 만들어 해당 설정 정보를 암호화된 값과 함께 넣는다.
  - 이후, users-service에서는 bootstrap.yml에서 users-service.yml을 호출하여 해당 설정 정보를 사용한다. 이때, users-service에서는 암호화된 값이 아니라 자동으로 복호화된 값을 사용하게 된다.

![image](https://user-images.githubusercontent.com/93081720/214610581-918df300-5da4-42cd-a820-596f2a2537dc.png)

<br>

## 2. 비대칭키(Asymmetric Encryption)



암호화, 복호화 할 때 사용하는 키가 다름

- 암호화, 복호화 할 때 키가 정해진 것은 아님

  - 암호화 할 때 퍼블릭 키(Public Key)를 사용했다면, 복호화 할 때는 프라이빗 키(Private Key)를 사용

  - 암호화 할 때 프라이빗 키(Private Key)를 사용했다면, 복호화 할 때는 퍼블릭 키(Public Key)를 사용

### Java Keytool

Java Keytool을 이용한 RSA 알고리즘을 사용한 RSA public, private keypair 생성

- 자바를 정상적으로 설치했다면 bin 폴더에서 keytool이라는 실행 프로그램을 찾을 수 있다.

![image](https://user-images.githubusercontent.com/93081720/214616295-943b4093-b960-4097-be89-9b8983012a22.png)

#### 키 생성

먼저 keystore를 보관할 디렉토리를 하나 생성한 다음에, bash나 cmd 창을 열고 keytool 명령어를 입력해서 키를 생성

- 혹시나 keytool 명령어가 command not found가 뜬다면, 환경변수 등록을 해줘야함

![image](https://user-images.githubusercontent.com/93081720/214619476-d7ce0e0a-20ed-447a-ae68-78d58291b9f0.png)

<br>

```bash
keytool -genkeypair -alias apiEncryptionKey -keyalg RSA -dname "CN=Siwon Park, OU=API Development, O=SpringCloudTest, L=Seoul, C=KR" -keypass "test1234" -keystore apiEncryptionKey.jks -storepass "test1234"
```

- -genkeypair: 키 페어를 생성한다
- -keyalg: 키 암호화 알고리즘 => RSA
- -dname: 서명 정보
  - CN=Consumer Name
  - OU=Organization Unit
  - O=Organization
  - L=Location
  - C=Country
- -keypass: keystore의 password
- -keystore: 저장될 keystore의 이름
- -storepass: store의 password

<br>

![image](https://user-images.githubusercontent.com/93081720/214620964-63ef5eb2-b555-42fe-bc15-8955c623510a.png)

![image](https://user-images.githubusercontent.com/93081720/214621329-be5455b3-a6c8-4dee-ad28-b6b68bd05353.png)
