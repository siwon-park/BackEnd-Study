# 220724_TIL

## JWT

JWT(JSON Web Token)는 비밀키를 이용하여 서명된 JSON 형태의 데이터가 저장된 토큰이며 다음과 같이 세 부분으로 구성된다(base64로 인코딩한 값을 콤마('.')를 사이에 두고 이어붙인 형태로 생성됨)

![image](https://user-images.githubusercontent.com/93081720/179353488-f57f0d49-1e2b-4da6-adba-7ebf08835883.png)

- 헤더(header)
  - 토큰의 타입과 해시 암호화 알고리즘으로 구성
- 페이로드(payload)
  - 토큰에 담을 정보
  - payload에 담는 정보의 한 ‘조각’을 클레임(claim)이라고 부르고, 이는 name / value 의 쌍으로 이루어져 있음
  - 토큰에는 여러 개의 클레임들을 넣을 수 있음
  - 토큰에 담긴 주체(Subject), 만료일(exp), 생성자(iss) 등을 담을 수 있음
- 서명(signature)
  - 일련의 문자열, 시그니처를 통해 토큰이 변조되었는지 여부를 확인
  - 'secret key를 포함'하여 암호화되어 있음

<br>

## JWT 토큰 이용 인증 흐름

1. 사용자 로그인
2. 서버에서는 계정 정보 읽어서 사용자 확인 후, 고유한 ID값 부여, 기타 정보와 함께 payload에 넣음
3. JWT 토큰의 유효기간(exp)을 설정
4. 암호화할 secret key를 이용해 accessToken 발급
5. 사용자는 accessToken을 받아 저장한 후, 인증이 필요한 요청마다 토큰을 헤더(header)에 실어서 전송
6. 서버에서는 해당 토큰의 Verify Signature를 Secret key를 이용해 복호화한 후 조작 여부, 유효기간 확인
7. 검증이 완료되면, payload를 디코딩하여 사용자의 ID에 맞는 데이터를 가져옴

**→ 세션/쿠키는 세션 저장소에 유저의 정보를 넣는 반면, JWT는 토큰 안에 유저의 정보를 넣음**

**클라이언트 입장에서는 HTTP헤더에 세션ID나 토큰을 실어서 보낸다는 점에서는 동일하나, 서버 측에서는 인증을 위해 암호화를 하느냐, 별도의 저장소를 이용하느냐의 차이점 발생**

<br>

## JWT 장/단점

### 장점

- 간편하다
    - 세션/쿠키는 별도의 저장소 필요, JWT는 발급한 후 인증과정만 거치면 되기 때문에 별도의 저장소 필요X
- 확장성이 뛰어나다
    - 토큰 기반으로 하는 다른 인증 시스템에 접근 가능
    - ex) SNS 로그인

### 단점

- 이미 발급된 JWT에 대해서는 돌이킬 수 없다
    - 세션/쿠키의 경우 탈취당하면 해당 세션 삭제하면 됨
    - JWT는 만료되기 전까지 계속 탈취 가능
      - 해결책 → AccessToken의 유효기간 짧게하고, RefreshToken 발급하여 이용함

- payload 정보가 제한적이다
    - payload는 따로 암호화되지 않기 때문에 유저의 중요한 정보를 넣지 않는 것을 권장하고 있음
      - 따라서 들어가는 정보가 제한적일 수밖에 없음
- JWT의 길이(자원)
    - JWT의 길이는 세션/쿠키 방식에 비해 길다. 따라서 인증이 필요한 요청이 많아질 수록 서버 자원 낭비가 발생할 가능성이 있다

<br>

## Access Token과 Refresh Token

### Access Token

- HTTP Request의 header 부분에 authorization으로 넣은 토큰
- 일반적으로 JWT 방식을 많이 사용함
- 로그인이 필요한 세션을 위한 보안 자격(security credentials)를 가지고 있으며, 사용자가 시스템에 로그인할 때 생성된다
- 토큰의 분실, 탈취로 인한 보안 위험이 있기 때문에 보통 짧은 유효기간을 가짐(30분 ~ 1시간)
  - 서버 측에서 토큰을 revoke(취소)하는 것이 아예 불가능하지만은 않지만 보통 불가능하다고 알려짐
    - *"액세스 토큰을 무효화 처리하는 것도 당연히 가능합니다. 액세스 토큰이나 리프레쉬 토큰이나 본질적으로는 차이가 없으니까요. 하지만 액세스 토큰을 무효화 하는 것은 리프레쉬 토큰 무효화보다 성능에 더 영향을 줍니다. 1. 리소스 서버와 인증 서버가 분리된 경우, 매번 인증서버에 무효화 여부를 물어봐야 하고, 인증서버가 보틀넥이 됩니다. 2. 그렇지 않은 경우 역시, 토큰 무효화는 DB와 같은 서버 스토리지가 필요하고 따라서 latency에 영향을 줍니다. 따라서 타협의 결과물로 일반적으로 액세스 토큰은 짧은 유효기간을 가지고 별도의 무효화는 지원하지 않습니다. 대신 리프레쉬 토큰은 긴 유효기간과 무효화를 지원합니다."*

<br>

### Refresh Token

- 새로운 Access Token을 얻을 수 있는 장기 토큰(Long-Lived Token)
- Access Token의 유효기간이 만료되었을 때 새롭게 발급해주는 역할을 함
- Refresh Token도 유효기간이 있어 만료가 되었으면 재발급받아야 한다
- 보통은 특정 리소스를 위한 엑세스 토큰을 얻는다
- Access Token을 통한 인증 방식에서 Access Token이 탈취당할 경우 보안에 취약해지는데 이를 보완하기 위한 개념으로 나온 것이 Refresh Token
- Refresh Token도 JWT형식을 취한다
- Refresh Token은 서버 DB에 저장한다

<br>

Oauth 2.0에서는 Access Token + Refresh Token 방식을 사용한다

<br>

## 원리

유효기간이 짧은 토큰의 경우, 사용자는 로그인을 더 자주해서 토큰을 발급 받아야 하므로 불편하고 유효기간을 늘리면 토큰을 탈취당했을 때 보안에 더 취약해진다

**“유효기간을 짧게 하면서 좋은 방법이 있지 않을까?” —> “Refresh Token”**

처음에 로그인을 완료했을 때 Access Token과 Refresh Token이 동시에 발급된다. 여기서 Refresh Token의 유효기간은 길어서 Access Token이 만료되었을 때 새로 발급해주는 열쇠가 된다

- 예)

  - Refresh Token의 유효기간 2주, Access Token의 유효기간 1시간

  - 사용자는 서비스를 이용하다가 1시간 후에 가지고 있는 AccessToken 만료됨

  - 이 때, RefreshToken의 유효기간 전까지는 AccessToken을 새로 발급받을 수 있음

<br>

### 의문점?

#### Q1) Refresh Token 자체에도 인증 정보가 있을텐데 왜 굳이 서버 DB에 저장을 해야하는가?

A) DB에 따로 저장하지 않으면, 서버는 해당 Refresh Token을 발급했는지 알 수가 없음

만약 DB에 저장하지 않고 Refresh Token의 payload만으로 Access Token의 발급 여부를 판단한다면, 해커가 Refresh Token을 탈취하여 payload를 임의로 조작해서 다른 유저의 정보까지도 접근가능해지므로 이를 막기위해 DB에도 저장하는 것이다

<br>

#### Q2) 만약 Refresh Token이 탈취당한다면 어떻게 할 것인가? 아무리 Access Token의 만료 기한을 짧게 잡더라도 만료 기한이 더 긴 Refresh Token이 탈취당하면 의미가 있을까? 그냥 Access Token의 만료기간을 늘리면 안 될까?

##### Okky 커뮤니티의 개발자들 답변

JWT의 특성인 stateless 때문에 정상적으로 발행된 토큰의 경우 해당 토큰의 만료 시간이 지나기 전까지는 없앨 수 없다!

만약 Access Token의 기간을 길게 잡아 이것만 사용하게 되면 **Access Token이 탈취 당했을 때 서버에서 아무런 방어적인 행동을 할 수 없다. 심지어 서버는 해당 토큰이 탈취 되었다는 사실 조차 모를 수 있다.**

그래서 클라이언트에게 2개의 토큰을 발급하는 것이고, 데이터에 접근 및 요청할 때는 Access Token만 사용하고 Refresh Token은 Access Token을 발급할 때만 서버에 전송한다

만약 Refresh Token이 탈취되어 해커가 새로운 Access Token을 발급 받을 수 있지만, 이 발급이 진행되는 과정에서 다른 나라의 ip주소가 찍힌다던지 신고된 아이디이던가 등을 검증할 수 있는 작업을 서버에서 할 수 있다.

그러면 서버는 요청을 무시하고 해킹된 Refresh Token을 서버에서 지워버려 Access Token을 해커에게 발급하지 않을 수 있는 것이다

**결론** : 한 번 발급되면 정보가 변하지 않는 stateless라는 jwt 특성 때문에 Refresh Token을 사용하여 Access Token을 발급한다. Refresh Token이라는 방어 도구라도 있어야 서버가 해킹된 토큰에 대한 방어 행동을 취할 수 있게 된다

<br>

##### Access Token의 만료기간을 늘리는 것에 대해

- 만료 기한이 짧은 엑세스 토큰만 사용할 때의 단점
  - 사용자가 매번 재로그인 해야하는 번거로움이 있다

- 만료 기한이 긴 엑세스 토큰만 사용할 때의 단점
  - 엑세스 토큰을 포함하는 모든 요청마다 엑세스 토큰 검증을 하고, 엑세스 토큰이 무효화 되었는지 검증하는 과정도 추가되어 부하가 커질 수 있음

<br>

##### Refresh Token의 탈취에 대해

서버 측의 검증 로직을 추가한다(검증 로직 중 하나임)

1. 데이터베이스에 각 사용자에 1대1로 맵핑되는 Access Token, Refresh Token 쌍을 저장한다.
2. 정상적인 사용자는 기존의 Access Token으로 접근하며 서버 측에서는 데이터베이스에 저장된 Access Token과 비교하여 검증한다.
3. 공격자는 탈취한 Refresh Token으로 새로 Access Token을 생성한다. 그리고 서버측에 전송하면 서버는 데이터베이스에 저장된 Access Token과 공격자에게 받은 Access Token이 다른 것을 확인한다.
4. 만약 데이터베이스에 저장된 토큰이 아직 만료되지 않은 경우, 즉 굳이 Access Token을 새로 생성할 이유가 없는 경우 서버는 Refresh Token이 탈취당했다고 가정하고 두 토큰을 모두 만료시킨다.
5. 이 경우 정상적인 사용자는 자신의 토큰도 만료됐으니 다시 로그인해야 한다. 하지만 공격자의 토큰 역시 만료됐기 때문에 공격자는 정상적인 사용자의 리소스에 접근할 수 없다.

<br>

##### Access Token과 Refresh Token 둘 다 탈취 당한다면?

100% 완벽한 보안이라는 것은 존재하지 않는다

<br>

## AccessToken + RefreshToken 인증 과정

![image](https://user-images.githubusercontent.com/93081720/180630441-b255ec7a-be76-4f1d-8961-8667ee4db762.png)

1. 사용자 로그인
2. 서버에서는 회원 DB에서 값을 비교(일반적으로 pw는 암호화해서 들어감)
3. 로그인이 완료되면 Access Token, Refresh Token을 발급. 이 때 일반적으로 회원 DB에 Refresh Token을 저장해둠
4. 사용자는 Refresh Token은 안전한 저장소에 저장 후 Access Token을 헤더에 실어 요청을 보냄
5. Access Token을 검증하여 이에 맞는 데이터 전송
6. 시간이 지나 Access Token 만료
7. 사용자는 이전과 동일하게 Access Token을 헤더에 실어 요청을 보냄
8. 서버는 Access Token이 만료됨을 확인하고 권한 없음으로 신호 보냄

    **사용자(프론트)에서 Access Token의 payload를 통해 유효기간을 알수 있기 때문에 프론트엔드 단에서 API 요청 전에 토큰이 만료됐다면 바로 재발급 요청을 할 수도 있음**

9. 사용자는 Refresh Token과 Access Token을 함께 서버로 보냄
10. 서버는 받은 Access Token이 조작되지 않았는지 확인한 후 , Refresh Token과 사용자의 DB에 저장되어 있던 Refresh Token을 비교. Token이 동일하고 유효기간도 지나지 않았다면 새로운 Access Token발급
11. 서버는 새로운 Access Token을 헤더에 실어 다시 API 요청 진행

<br>

## 결론 및 정리

Access Token + Refresh Token은 보안성, 성능, 사용편이성 등을 적절하게 타협한 결과이다. 순수하게 보안만 추구한다면 더 좋은 방법들이 있긴하다