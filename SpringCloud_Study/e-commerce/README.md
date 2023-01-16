# Spring Cloud로 개발하는 MSA

## MSA

### discovery-service



### apigateway-service



### user-service

#### Features

- 신규 회원 등록
- 회원 로그인
- 상세 정보 확인
- 회원 정보 수정/삭제
- 상품 주문
- 주문 내역 확인



#### 유의사항

h2는 1.3대 버전으로 사용함(1.3.176)

h2 DB는 1.4.198버전 이후에는 보안 문제로 자동으로 데이터베이스를 생성하지 않는다. 따라서 웹으로 h2에 접속하여 이전에는 기본적으로 생성되었던 testdb에 접근하려고 하면 testdb가 없다고 나온다.