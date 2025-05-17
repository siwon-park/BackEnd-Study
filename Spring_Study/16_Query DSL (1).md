![image](https://user-images.githubusercontent.com/93081720/201936889-41b94b97-8921-428b-81e3-e187e87212f2.png)

# 16_Query DSL - (1)

## 1. JPQL과 Spring Data JPA

### 1) JPQL

직접 문자열 텍스트로 쿼리를 작성하는 방식. @Query 어노테이션 안에 문자열로 직접 쿼리를 작성한다.

![image](https://user-images.githubusercontent.com/93081720/201936372-100db7ba-0b99-4014-9586-10f5b945e485.png)

#### (1) 장점

- 쿼리를 직접 작성하는 개념이다 보니 쿼리 튜닝이 가능하다.

#### (2) 단점

- 문자열로 작성하기 때문에 오타를 냈을 경우 컴파일 단계에서 잡히지 않아 위험하다.
- 쿼리가 길어질 경우 가독성이 좋지 않다.
- 문법이 일반 SQL과 조금 달라서 복잡한 쿼리를 작성할 때마다 레퍼런스를 참고해야한다.

<br>

### 2) Spring Data JPA

개발자가 직접 쿼리를 작성하지 않고 Spring Data JPA가 제공하는 메서드를 통해서 DB에서 데이터를 가져오는 방식

![image](https://user-images.githubusercontent.com/93081720/201939583-b3c8bc33-9420-42b1-b33a-2341dfc978fc.png)

#### (1) 장점

- 간단하게 JPA에서 제공하는 메서드만 사용해도 쿼리 수행이 가능하다. 

#### (2) 단점

- 조건이 복잡한 동적쿼리를 작성할 때마다 함수가 계속해서 늘어난다. => 필드가 늘어날 때마다 계속해서 해당 함수도 추가해줘야함
  - `findByOOO`, `findByOOOAndXXX`, `findByOOOAndXXXAnd△△△`, ... (계속)
- 동적 쿼리 생성에는 한계가 있음

- 도메인 종속적이기 때문에 도메인 코드 변경에 취약하다.
  - = 프로덕션 코드 변경에 취약하다.
  - 엔티티 필드, 연관관계 맵핑이 테이블 구조와 1:1로 강하게 연결되어 있기 때문에 도메인 코드 변경이 곧 쿼리의 변경을 의미하며, 이는 DB 스키마의 변경도 동반할 수 있기 때문에 매우 위험하다. (마이그레이션 및 배포 리스크가 있음)

<br>

## 2. Query DSL

### 1) Query DSL의 등장

>  Querydsl is a framework which enables the construction of type-safe SQL-like queries for multiple backends including JPA, MongoDB and SQL in Java.

Java로 타입 세이프한 쿼리를 작성할 수 있게 만들어주는 프레임워크.

복잡한 동적 쿼리 작성에 있어 JPQL, JPA의 단점을 극복하기 위해 Query DSL이란 것이 등장함.\

- 예시

![image](https://user-images.githubusercontent.com/93081720/201939935-d6e100be-ca0f-4f9c-a651-39590c194290.png)

#### (1) 오해?

Query Dsl을 사용하면 Spring Data JPA는 필요 없다?

- Spring Data JPA와 Query Dsl을 함께 사용해서 서로를 **보완**해야하는 개념.
- 기본적으로 Spring Data JPA를 통해 제공 가능한 수준의 쿼리는 JPA의 메서드를 활용하고, 더 복잡한 동적 쿼리가 필요할 경우 Query Dsl을 사용.

#### (2) 장점

- 복잡한 동적 쿼리를 Java 코드를 작성하듯이 사용하여 짤 수 있다.
- 타입 세이프(type-safe)하다. Java로 쿼리를 구성하다보니 잘못된 사용이 있을 경우 JPQL과 달리 컴파일 시점에 체크하여 잠재적인 문제를 사전에 차단할 수 있다.

#### (3) 단점

- 불친절한 공식 문서
  - 공식 문서가 상당히 불친절한 편이다. 스프링에서 query dsl을 사용하려면 설정이 필수적인데, 공식 문서에서 찾기가 불가능하고 구글링을 통해서 설정하는 방법을 찾는게 더 빠르다.
  
- 사용을 위한 추가 설정 필요

  - 버전에 따른 Qclass 생성 방법 상이
    - 매번 Qclass를 설정하는 방법이 변해왔기 때문에 잘못 구글링할 경우 설정부터 고생한다.


  - 프로젝트에 Query DSL을 적용하기 위한 추가적인 설정이 필요한데, 버전에 따라 설정 방법이 다르다. 예를 들면, 스프링부트 2.7.X 버전대 설정 방법과 3.X 버전대 설정 방법을 달리 해줘야만 정상적으로 빌드되어 동작한다.
    - Java EE의 아티팩트가 javax에서 jakarta로 변경되었기 때문.

<br>

## 3. Query DSL 사용을 위한 사전 설정

build.gradle과 더불어 config 파일을 작성하여 사용을 위한 설정을 사전에 해줘야 한다.

### 1) 설정 방법

프로젝트 버전에 따라 설정 방법이 상이하므로, 가장 좋은 방법은 본인이 사용할 버전과 가장 가까운 버전으로 설정하는 방법을 찾아서 따라서 적용하면 편하다.

Java EE의 아티팩트 아이디가 javax에서 jakarta로 변경되었기 때문에, query dsl 라이브러리를 dependency에 적용할 때 반드시 classifier를 명확하게 명시해주자.

#### (1) build.gradle

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

#### (2) config 파일

QueryDSL 적용을 하기위한 설정 config파일을 생성하고 JPAQueryFactory를 생성할 수 있는 메서드를 @Bean으로 등록한다. (빈 메서드 등록)

| Java                                                         | Kotlin                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/38c979a0-3f67-42c4-85c3-954d20ca55b8) | ![image](https://user-images.githubusercontent.com/93081720/201942338-9ca5643a-8189-437b-ab4c-28fe28fa735f.png) |

<br>

## 4. Query DSL의 적용

두 가지 방법이 있지만, 실질적으로는 둘 다 JPAQueryFactory를 호출해서 쿼리를 작성하기 때문에 유사하다고 볼 수 있다.

차이는 **스프링 데이터 JPA의 CustomRepository 확장 기능을 활용하느냐**, 아니면 **QueryDSL 쿼리만 완전히 별도로 관리하느냐**의 차이. 두 방법 간 성능적인 offset은 없다고 보는게 맞다.

### 1) 기존 JpaRepository 구조

기존에는 Spring Data JPA만 사용했기 때문에 Repository가 JpaRepository를 상속받는(extends) 형태였다.

![image](https://user-images.githubusercontent.com/93081720/201515598-6dfb6eab-c0a1-4075-ba7a-998f5f14cb5b.png)

<br>

### 2) CustomRepositoryImpl 클래스

첫 번째 방법. CustomRepository 인터페이스를 선언하고, 이를 구현한 클래스인 CustomRepositoryImpl를 생성한다.

그 후 기존 Repository는 JpaRepository와 CustomRepository를 상속(확장, extends)한다.

![image](https://user-images.githubusercontent.com/93081720/201516031-3c36ecd1-0bde-4fc0-b7b3-f21261077590.png)

#### (1) 장점

- 서비스 단에서는 Repository 하나만 호출하여 사용하면 된다.
- Spring Data JPA에서 제공하는 쿼리 메서드와 Query Dsl을 통해 직접 작성한 동적 쿼리 메서드를 사용할 수 있다.
  - save, findBy, delete 등과 같이 JPA쪽에서 기본적으로 제공하는 강력한 표준 메서드를 같이 사용할 수 있다.

#### (2) 단점

- 복잡한 구조와 코드 복잡도 증가 (클래스 파일 수 증가)
  - 커스텀 repository 인터페이스와 이를 구현한 클래스를 항상 같이 만들어줘야 하며, 이를 기존의 respository가 확장해야만 사용이 가능하다.
- 관심사가 분리되지 않음
  - JPA 표준 메서드와 QueryDsl 커스텀 메서드가 한 repository 안에 있음.

<br>

### 2) QueryDslRepository

두 번째 방법. 별도의 QueryDsl 커스텀 쿼리만을 위한 클래스를 생성하여 사용하는 방법.

| Java                                                         | Kotlin                                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://github.com/siwon-park/Problem_Solving/assets/93081720/dd287d2f-fa06-47f5-a8e1-1c72da1f07d8) | ![image](https://user-images.githubusercontent.com/93081720/201942909-5d85c75d-3e99-4108-ba3d-d9fadcbf0ac9.png) |

#### (1) 장점

- 하나의 클래스만 만들어서 사용하면 되므로 훨씬 간단하다.
- QueryDsl 커스텀 쿼리만을 위한 클래스로 별도 구분이 가능하다. (관심사의 분리)

#### (2) 단점

- JPA에서 기본적으로 제공하는 표준 메서드 사용은 불가하다.
- 필요에 따라서 여러 repository를 불러와야 할 수도 있다.
  - 예를 들어 첫 번째 방법에서는 하나의 repository에서 JPA와 QueryDsl 쿼리를 제공하므로 불러올 repository가 하나이지만, 두 번째 방법을 사용하게 되면 아무래도 역할이 분리되어 있다보니 불러올 repository가 늘어나게 된다.

<br>

### 3) 어떤 방법이 더 좋은가?

두 방법 간 성능적으로는 차이가 없다. 따라서 팀, 코드 스타일에 따라서 하나의 방법을 선택해서 적용하면 된다.

멀티 모듈을 사용하는 경우 모듈별로 특정 repository를 쓰는 경우가 많기 때문에 상대적으로 사용이 조금 더  간단한 두 번째 방법을 추천.

<br>

## 5. 기타

### 1) JPQL을 쓰지 않은 구문도 Query DSL로 옮겨야할까?

결론적으로 **동적 쿼리 작성의 간편함** 덕분에 변환해주는 것을 권장!

- 조건이 복잡한 동적쿼리를 작성할 때마다 함수가 계속해서 늘어난다는 단점을 해결 가능함
  - `findByOOO`, `findByOOOAndXXX`, `findByOOOAndXXXAnd△△△`, ... (계속)
- 예) A필드는 필수적으로 들어오지만, B, C, D, E 필드는 선택적으로 들어온다고 했을 때
  - `findByA` (필수)
  - `findByAAndB`, `findByAAndBAndC`, ... , `findByAAndBAndCAndDAndE`까지 함수를 만들어야할 수도 있음
  - 2^4개의 함수가 생성됨 => 만약 필드가 더 늘어난다면? 2^n개