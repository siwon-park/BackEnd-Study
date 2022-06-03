# Spring_learned

### Section 1

- 실무에서는 sysout보다는 log를 쓴다 => why? 에러 관리 및 트랙킹 목적
- static에서 .html을 먼저 찾고 나서 template에서 찾는다.
  - src - resources - static에 index.html만들기
- 빌드
  - 실행 파일 만드는 행위, 터미널(cmd)에서 해당 명령어 입력(반드시 서버를 끈 채로 진행할 것)
  - 빌드하기
    - gradlew build
  - 빌드 클리어
    - gradlew clean build



### Section 2

- 단축키
  - Ctrl + P : 정의된 메서드, 함수의 파라미터 정보
  - Ctrl + Shift + Enter : 세미콜론까지 자동완성



### Section 3

- 인터페이스의 장점 => 개발 도중에 바꿔끼울 수 있어 좀 더 효율적인 개발 가능
- 공유되는 변수의 경우 실무단에서는 컨커런트 해시맵을 사용함
- Shift + F6 => 다중 커서 및 이후에 등장한 모든 변수를 선택(변수명 변경에 용이)
- TDD(Test Driven Development) => Test를 할 수 있는 틀을 먼저 만드는 개발 방식
- 테스트 코드는 한글로 적어도 됨
- 서비스 => 비즈니스 로직과 가까운 용어를 사용하고, 비즈니스 로직에 의존적으로 설계(비즈니스 로직이 실제 동작하는 곳이기 때문)