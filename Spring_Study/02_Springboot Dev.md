# 02_스프링부트 프로젝트

## 프로젝트 개발 순서

[출처](https://github.com/HomoEfficio/dev-tips/blob/master/SpringBoot%20%EA%B0%9C%EB%B0%9C%20%EC%88%9C%EC%84%9C.md)

### 1. Springboot 프로젝트 생성

직접 의존성 설정을 해서 프로젝트를 생성하거나 `Spring Initializer`를 통해서 생성

- Spring Initializer
  - Web, JPA, H2, Actuator
- localhost:8080 으로 테스트

### 2. build.gradle 추가 설정

(maven 프로젝트라면, application.yml에 local, dev, production 프로파일 작성)

(1번에서 직접 의존성을 설정해줬다면 이미 build.gradle을 설정한 것이므로 pass)

### 3. domain 객체(Entity) 작성

도메인 객체, 엔티티 작성

- 도메인 로직 테스트 케이스 작성
- 도메인 객체 작성

### 4. Repository(레포지토리) 작성

repository 작성

- repository 인터페이스 작성
- Optional로 작성
- repository 테스트 작성
  - 기본 JpaRepository 메서드 외의 메서드만
  - @DataJpaTest
  - @SpringBootTest를 붙이지 않으면 Repository외의 다른 Bean은 @Autowired 불가

### 5. Service(서비스) 작성

서비스 작성

- 서비스 인터페이스 작성
- 서비스 테스트 작성
  - 테스트 클래스에 @Transactional 붙여줘야 메서드 별로 저장 후 rollback 적용됨
- 서비스 작성
  - @Transactional
  - 조회 메서드에도 @Transactional(readOnly = true)붙여줘야 JPA proxy 관련 오류 안 생김

### 6. Controller(컨트롤러) 작성

컨트롤러 작성

- 테스트는 service를 mocking 하는 방식 대신 통합테스트와 유사하게 작성

  ```
  @RunWith(SpringRunner.class)
  @SpringBootTest
  ```

- https://github.com/HomoEfficio/spring-web-api-test-stubber 활용 가능

### 7. dto 작성

- View에 맞는 dto 작성
- View에 맞는 validation 애노테이션 포함

### 8. converter 작성

- converter 테스트 작성
- http://modelmapper.org/ 활용 가능

### 9. 컨트롤러에 dto 적용

- 컨트롤러 메서드에 사용되던 도메인 객체를 dto로 변경

### 10. 서비스에 dto-엔티티 매핑 적용

- 서비스 메서드의 파라미터로 사용되던 엔티티 객체를 dto로 변환
- dto <-> 엔티티 변환 후 repository 호출/결과 반환

### 11. 통합 테스트 실행   - Controller 테스트

### 12. Security 적용

- WebSecuityConfig 작성
- 컨트롤러에 권한 체크 추가
- 통합 테스트
  - Security에 대한 테스트는 로그인이 포함된 모듈에서만 수행
  - 나머지 모듈은 Security를 disable 하고 수행

### 13. Swagger 적용

- http://springboot.tistory.com/24

  참고

  - SwaggerConfig Bean 추가
  - Swagger UI 추가: app_root/swagger-ui.html 에서 확인 가능  

### 14. Logging 적용

- Lombok과 @Slf4J 활용   

### 15. Biz: 도메인 작성 ~ 통합 테스트 실행 까지를 계속 iteration

### 16. 공통/아키텍트

- JPA Auditing 설정
- DateTimeFormatter 공통화
- Collection의 validator 공통화
- BaseEntity로 JPA auditing 적용
- XSS 필터 적용
- JSON response XSS 처리
- Generic을 활용한 DataConverter 일반화
- Swagger 설정 및 적용