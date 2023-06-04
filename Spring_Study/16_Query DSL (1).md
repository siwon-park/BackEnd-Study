![image](https://user-images.githubusercontent.com/93081720/201936889-41b94b97-8921-428b-81e3-e187e87212f2.png)

# 16_Query DSL

## 01_JPQL과 Spring Data JPA

### JPQL의 단점

![image](https://user-images.githubusercontent.com/93081720/201936372-100db7ba-0b99-4014-9586-10f5b945e485.png)

- 가독성이 별로 좋지 않다 => 문자열로 작성하기 때문에 오타 등으로 인해 잘못 썼을 경우 버그를 찾기 어렵다.
- 문법이 일반 SQL과 조금 달라서 복잡한 쿼리를 작성할 때마다 레퍼런스를 참고해야한다.
- 조건이 복잡한 동적쿼리를 작성할 때마다 함수가 계속해서 늘어난다. => 필드가 늘어날 때마다 계속해서 해당 함수도 추가해줘야함
  - `findByOOO`, `findByOOOAndXXX`, `findByOOOAndXXXAnd△△△`, ... (계속)

<br>

### Spring Data JPA의 단점

![image](https://user-images.githubusercontent.com/93081720/201939583-b3c8bc33-9420-42b1-b33a-2341dfc978fc.png)

- 프로덕션 코드 변경에 취약하다 => 도메인 종속적이기 때문에 도메인 코드 변경에 취약하다.

<br>

## 02_Query DSL

### Query DSL의 등장

>  해당 언어의 코드로 쿼리를 작성하게 해 줄 수 있는 프레임워크

JPQL의 단점을 극복하기 위해 Query DSL이란 것이 등장함

SQL 쿼리문을 Java를 사용하여 구성하여 사용 가능함

Spring Data JPA와 Query Dsl을 함께 사용해서 서로를 **보완**해야하는 개념

![image](https://user-images.githubusercontent.com/93081720/201939935-d6e100be-ca0f-4f9c-a651-39590c194290.png)

<br>

### Query DSL의 단점

- 불친절한 공식 문서
  - 공식 문서를 직접 들어가보면 알겠지만 상당히 불친절하다. gradle의 경우 구글링을 통해서 설정하는 방법을 찾는게 더 빠르다.
- 버전에 따른 Qclass 생성 방법 상이
  - 매번 Qclass를 설정하는 방법이 변해왔기 때문에 문서를 잘못 구글링할 경우 설정에서 부터 고생한다.
- 사용을 위한 추가 설정 필요
  - 프로젝트에 Query DSL을 적용하기 위한 추가적인 설정이 필요한데, Query DSL 버전에 따라 설정 방법이 상이하고 프로젝트 버전에 따라 적용시키는 방법이 다르다.
  - 예를 들면, 스프링부트 2.7.X 버전대 설정 방법과 3.X 버전대 설정 방법을 달리 해줘야만 정상적으로 빌드되어 동작한다.
    - 이는 javax와 jakarta 때문인듯...?

<br>

### Query DSL 사용을 위한 사전 설정

build.gradle과 더불어 config 파일을 작성하여 사용을 위한 설정을 사전에 해줘야 한다.

#### build.gradle

기존에 plugin을 사용하여 build.gradle 설정을 많이 하는 편인데, 2018년 이후 관리되고 있지 않기 때문에. 플러그인을 사용하지 않고 직접 설정하는 것을 권장한다.

- Java

```groovy
plugins {
	id 'java'
	id 'org.springframework.boot' version '2.7.12'
	id 'io.spring.dependency-management' version '1.1.0'
}

// 중략

dependencies {
    // 중략
	
	// querydsl 설정
	implementation 'com.querydsl:querydsl-core:5.0.0'
	implementation 'com.querydsl:querydsl-jpa:5.0.0'
	implementation 'com.querydsl:querydsl-apt:5.0.0'

	// querydsl JPAAnnotationProcessor 사용 지정
	annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jpa"
	 // java.lang.NoClassDefFoundError(javax.annotation.Entity) 발생 대응
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
     // java.lang.NoClassDefFoundError (javax.annotation.Generated) 발생 대응
	annotationProcessor("jakarta.annotation:jakarta.annotation-api")

	// Spring boot 3.x이상에서 QueryDsl 패키지를 정의하는 방법
	// implementation 'com.querydsl:querydsl-jpa:5.0.0:jakarta'
	// annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jakarta"
	// annotationProcessor "jakarta.annotation:jakarta.annotation-api"
	// annotationProcessor "jakarta.persistence:jakarta.persistence-api"
}

// 중략

// Q클래스 생성 경로 지정
def querydslDir = "$buildDir/generated/querydsl" //"src/main/generated"

// build 시 사용할 sourceSet 추가
sourceSets {
	main.java.srcDirs += [ querydslDir ]
}

tasks.withType(JavaCompile) {
	options.annotationProcessorGeneratedSourcesDirectory = file(querydslDir)
}

clean.doLast {
	file(querydslDir).deleteDir()
}
```

- Kotlin

```groovy
plugins {
    id 'org.springframework.boot' version '2.6.8'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
    id 'org.jetbrains.kotlin.jvm' version '1.6.21' // 코틀린 플러그인 추가
    id 'org.jetbrains.kotlin.plugin.jpa' version '1.6.21' // 코틀린 JPA 플러그인 추가
    id 'org.jetbrains.kotlin.plugin.spring' version '1.6.21' // 코틀린 스프링 플러그인 추가 => 스프링 빈 클래스를 자동으로 열어줌(open을 붙일 필요 없음)
    id 'org.jetbrains.kotlin.kapt' version '1.6.21' // 코틀린 쿼리 dsl 적용을 위한 플러그인 추가
}

group = 'com.group'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk8' // 코틀린 스탠다드 라이브러리 추가
    // ClassNotFoundException: kotlin.reflect.full.KClasses를 해결하기 위함
    implementation 'org.jetbrains.kotlin:kotlin-reflect:1.6.21'
    // 자바 DTO를 코틀린으로 변환하면서 JSON을 파싱하는 과정에서 인식을 못할 수도 있음 따라서 코틀린 전용 jackson 의존성을 추가해준다.
    implementation 'com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3'
    implementation 'org.junit.jupiter:junit-jupiter:5.8.1'
    implementation 'com.querydsl:querydsl-jpa:5.0.0'
    kapt('com.querydsl:querydsl-apt:5.0.0:jpa')
    kapt('org.springframework.boot:spring-boot-configuration-processor')
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
    useJUnitPlatform()
}

compileKotlin {
    kotlinOptions {
        jvmTarget = "11" // 프로젝트의 자바 버전이 11이므로 코틀린도 11버전에서 동작하게 함
    }
}

compileTestKotlin {
    kotlinOptions {
        jvmTarget = "11" // 프로젝트의 자바 버전이 11이므로 코틀린도 11버전에서 동작하게 함
    }
}
```

<br>

#### config 파일

QueryDSL 적용을 하기위한 설정 config파일

| Java                                                         | Kotlin                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/38c979a0-3f67-42c4-85c3-954d20ca55b8) | ![image](https://user-images.githubusercontent.com/93081720/201942338-9ca5643a-8189-437b-ab4c-28fe28fa735f.png) |

<br>

### Query DSL의 적용

#### 기존 Repository 구조

![image](https://user-images.githubusercontent.com/93081720/201515598-6dfb6eab-c0a1-4075-ba7a-998f5f14cb5b.png)

<br>

#### 첫 번째 방법

CustomRepository 인터페이스와 CustomRepository를 Implementation한 클래스 생성

![image](https://user-images.githubusercontent.com/93081720/201516031-3c36ecd1-0bde-4fc0-b7b3-f21261077590.png)

- 첫 번째 Query DSL 적용 방법의 장점
  - 서비스 단에서 JPARepository와 CustomRepository를 상속받고 구현한 Repository 하나만 호출하여 사용하면 된다.

- 첫 번째 Query DSL 적용 방법의 단점
  - 커스텀 repository 인터페이스와 이를 구현한 클래스를 항상 같이 만들어줘야 한다.

<br>

#### 두 번째 방법

QueryDsl 클래스를 생성하여 사용

| Java                                                         | Kotlin                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/dd287d2f-fa06-47f5-a8e1-1c72da1f07d8) | ![image](https://user-images.githubusercontent.com/93081720/201942909-5d85c75d-3e99-4108-ba3d-d9fadcbf0ac9.png) |

- 두 번째 Query DSL 적용 방법의 장점
  - 하나의 클래스만 만들어서 사용하면 되므로 훨씬 간단하다.

- 두 번째 Query DSL 적용 방법의 단점
  - 필요에 따라서 여러 repository를 불러와야 할 수도 있다.

<br>

#### 어떤 방법이 더 좋은가?

성능적으로는 차이가 없지만, 멀티 모듈을 사용하는 경우 모듈별로만 해당 repository를 쓰는 경우가 많기 때문에 사용이 더 간단한 두 번째 방법을 추천

<br>

### JPQL(@Query)을 쓰지 않은 구문도 Query DSL로 옮겨야할까?

=> **동적 쿼리의 간편함** 덕분에 변환해주는 것을 권장!

- 조건이 복잡한 동적쿼리를 작성할 때마다 함수가 계속해서 늘어난다는 단점을 해결 가능함
  - `findByOOO`, `findByOOOAndXXX`, `findByOOOAndXXXAnd△△△`, ... (계속)
- 예) A필드는 필수적으로 들어오지만, B, C, D, E 필드는 선택적으로 들어온다고 했을 때
  - `findByA` (필수)
  - `findByAAndB`, `findByAAndBAndC`, ... , `findByAAndBAndCAndDAndE`까지 함수를 만들어야할 수도 있음
  - 2^4개의 함수가 생성됨 => 만약 필드가 더 늘어난다면? 2^n개