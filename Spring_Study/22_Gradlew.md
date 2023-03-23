# 22_Gradlew

## 1. gradlew build vs. bootJar

Java를 gradle로 통해서 build할 경우, build 결과물로 `실행가능한 jar파일(executable jar)`과 `실행 불가능한 jar 파일(plain jar)`가 빌드되어 나온다.

plain jar는 말 그대로 실행이 불가능하기 때문에 프로젝트를 정상적으로 배포시키려면 executable jar를 통해 실행해야만 한다.

`build.gradle`에서 설정을 통해서 plain.jar를 생성하지 않도록 설정할 수도 있지만, 다른 방법을 통해서도 할 수 있다.

### gradlew build

`build`를 수행할 경우 `executable jar`와 `plain jar`가 생성됨을 확인할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/227152688-19dbbab2-b89c-4e3c-8f69-14f7475ff129.png)

<br>

### gradlew bootJar

`bootJar`를 실행시키면 다음과 같이 `executable jar`만 생성됨을 확인할 수 있다.

![image](https://user-images.githubusercontent.com/93081720/227152899-ffc647d1-0c2a-484b-89ee-95d1bda9ea19.png)

<br>