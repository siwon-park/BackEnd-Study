# 22_Gradlew

## 1. gradlew build vs. bootJar

Java를 gradle로 통해서 build할 경우, build 결과물로 `실행가능한 jar파일(executable jar)`과 `실행 불가능한 jar 파일(plain jar)`가 빌드되어 나온다.

`plain jar`는 `executable jar`와 달리 어플리케이션 실행을 위한 의존성을 갖고 있지 않고, 소스 코드의 클래스 파일과 리소스만 가지고 있어 실행이 불가능하기 때문에 프로젝트를 정상적으로 배포시키려면 executable jar를 통해 실행해야만 한다.

다음과 같이 `build.gradle`에서 설정을 통해서 plain.jar를 생성하지 않도록 설정할 수도 있지만, 다른 방법을 통해서도 할 수 있다.

```groovy
jar {
    enabled = false
}
```

<br>

### gradlew build

`build`를 수행할 경우 `executable jar`와 `plain jar`가 생성됨을 확인할 수 있다.

`build` Task는 `bootJar`와 달리 내부 동작이 더 길다. bootJar 수행을 포함하고 있으며, test 코드가 있다면 test도 수행한다.

즉, `build`는 모든 총체적인 빌드 작업을 전부 다 수행하는 동작이다.

![image](https://user-images.githubusercontent.com/93081720/227152688-19dbbab2-b89c-4e3c-8f69-14f7475ff129.png)

<br>

### gradlew bootJar

`bootJar`를 실행시키면 다음과 같이 `executable jar`만 생성됨을 확인할 수 있다.

따라서 `bootJar`는 단순히 프로젝트의 실행 가능한 jar 파일만 만드는데에 목적이 있음을 추측할 수 있다. 그리고 그만큼 `build`에 비해 수행하는 동작도 적기 때문에 속도도 빠르다.

![image](https://user-images.githubusercontent.com/93081720/227152899-ffc647d1-0c2a-484b-89ee-95d1bda9ea19.png)

<br>