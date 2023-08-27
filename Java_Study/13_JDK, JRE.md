# 13_JDK, JRE

> 자바를 다루면서 한 번씩 들어봤던 용어인 JDK, JRE에 대한 간단한 정리

Java로 프로그램을 직접 개발하려면 JDK가 필요하고, Java 프로그램을 컴파일하여 실행하려면 JRE가 필요하다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/a2faf4ea-936d-4097-a220-0464f55c83c4)

<br>

## 1. JDK (Java Development Kit)

> 자바 개발 키트

JDK는 개발자들이 자바 개발을 하는데 사용되는 `SDK(Software Development Kit)`을 말한다.

- SDK: 소프트웨어 개발 키트로, 하드웨어 플랫폼, 운영체제, 프로그래밍 언어 개발사가 제공하는 툴을 총칭한다. 대표적인 SDK로는 안드로이드 스튜디오를 예로 들 수 있다.

JDK는 JRE와 Java Development Tools 등을 포함하고 있다.

JDK의 버전으로는 SE, EE, ME, FX 등이 있는데, 의미는 다음과 같다.

- SE (Standard Edition) : 표준 에디션
- EE (Enterprise Edition) : 대규모 기업용 에디션
- ME (Micro Edition) : 임베디드용 개발을 위한 에디션 (현재는 잘 사용되지 않음)
- FX: 사용자 GUI를 제공하는 에디션

Java 소스코드 자체는 오픈 소스이기 때문에 누구에게나 열려있지만, JDK는 라이센스가 있기 때문에 무료/유료로 나뉜다. 가장 유명한 유료 라이센스 JDK는 Oracle JDK가 있고, 무료 JDK는 우리가 잘 알고 있는 Open JDK 외에도 Azul Zulu, Amazon Corretto 등 한 번쯤은 들어본 JDK들이 있다.

<br>

## 2. JRE (Java Runtime Environment)

> 자바 실행 환경

JRE는 JVM과 자바 프로그램을 실행시킬 때 필요한 라이브러리 등을 함께 묶어서 배포되는 패키지, 실행 환경을 말한다.

JRE는 기본적으로 JDK에 포함되어 있기 때문에 JDK를 설치하면 함께 설치된다. (기존에는 개별적으로도 제공되었으나 JDK11부터는 따로 제공되지 않는다고 함)