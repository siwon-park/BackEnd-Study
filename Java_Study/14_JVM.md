# 14_JVM (Java Virtual Machine)

> 자바 가상 머신

JVM은 JRE에 포함되어 Java 프로그램을 실행시키기 위한 환경 중 하나이다. 따라서 JRE가 설치되어 있으면 JVM도 설치되어 있다.

JVM은 이름 그대로, 자바를 실행시키기 위한 가상 머신이라고 생각하면 된다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/c2739645-19fa-44e3-8e9a-0360e78063e9)

**※ 컴파일(Compile)**

- 컴파일은 소스 코드를 컴퓨터가 이해하고 실행할 수 있는 형태로 변환하는 과정을 의미한다.
- 따라서, 후술하겠지만 Java를 예를 들면 .java 소스 코드가 컴파일러에 의해 클래스 파일(.class)로 변환되는 것도 컴파일 과정이고 JVM이 바이트 코드를 바이너리 코드로 변환시키는 것도 컴파일이다.

<br>

## 1. JVM의 역할과 필요성

> JVM은 왜 필요할까?

### 1) JVM의 역할

클래스 파일(.class)의 바이트 코드를 컴퓨터가 이해 가능한 바이너리 코드로 변환시켜주는 역할을 한다.

#### (1) Java 소스 코드 실행 흐름

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/12037a36-020f-444e-a341-34bb20598c09)

<br>

### 2) JVM의 필요성

그런데 JVM이 왜 필요할까? 단순히 바이트 코드를 바이너리 코드로 바꿔준다고 해서 필요한 것은 아닐 것이다.

#### (1) 바이너리 코드

바이너리 코드란 기계어의 한 종류로, "기계가 읽을 수 있는 이진 코드"를 의미한다.

일반적으로는 개발자가 소스 코드를 작성하면, 기계에 맞춰진 컴파일러가 각 기계가 이해할 수 있는 기계어로 변환시켜주는 역할을 한다.

그런데 이는 특정 OS나 CPU에 맞춰 컴파일되기 때문에 다양한 다른 환경에서는 이 기계어를 이해할 수 없을 수도 있다.

즉, OS 종속적이기 때문에 실행이 보장되지 않을 수도 있다.

#### (2) OS 비종속적

그러나 Java로 작성한 소스 코드는 직접 운영체제로 가서 실행되는 것이 아니라, JVM을 거쳐서 운영체제와 상호작용 하는 과정을 거치게 된다.

이 때, JVM이 각 하드웨어/OS의 환경에 맞게 바이트 코드로 변환시켜주어 OS와 상관없이 독립적으로 프로그램을 실행할 수 있도록 해준다.

##### ※ 주의점

단, 여기서 착각하지 말아야 할 점이 하나 있다.

`JVM은 운영체제(OS)에 종속적이다. 따라서 각 운영체제에 맞는 JVM을 설치해줘야 한다.`

"어, 그럼 왜 OS에 종속적이지 않다고 말하나요?"라는 생각이 들 수도 있다.

OS에 종속적이지 않다는 말은 C언어를 예를 들면, 윈도우용 C 코드와 리눅스용 C 코드를 각 각 따로 작성해야 한다. 즉, OS에 맞춰서 코드를 작성/유지보수 해야 하는 것이다.

그러나 Java를 보면 윈도우든, 리눅스든 코드를 작성하고 컴파일해서 실행시키기만 하면 된다.

**즉, Java 코드를 윈도우에서 실행시키든, 리눅스에서 실행시키든 윈도우 JVM이 윈도우에 맞게 바이트 코드로 변환시켜주는 것이고, 리눅스 JVM이 리눅스에 맞게 바이트 코드로 변환시켜주는 것이다.**

<br>

### 3) JVM과 Java

JVM은 Java에만 국한되지 않는다. Java가 아닌 다른 언어도 클래스 파일만 있다면 JVM을 사용할 수 있다. 실제로 코틀린, 그루비도 JVM을 사용하고 있다.

JVM 덕분에 Java 프로그램이 OS에 종속적이지 않게 실행될 수 있다는 장점이 생겼지만, 반면에 단점도 생겼다. JVM이라는 한 단계를 더 거쳐야 하기 때문에 결국은 실행 속도면에서 느리다는 단점을 갖게 되었다.

- 컴파일 과정을 총 2번 거치게 되기 때문에 그렇다. 물론 JVM 내부에서 JIT 컴파일러가 캐싱을 사용하여 보다 효율적으로 동작하게 해놓긴 했지만 C언어의 속도를 따라잡진 못했다.

<br>

## 2. JVM의 동작 방식

JVM은 Java Compiler가 컴파일한 Byte Code(.class)를 Class Loader와 Runtime Data Area, Execution Engine의 상호작용으로 동작하며 처리한다.

### 1) JVM 아키텍처

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/19d46efc-5ea9-4361-b754-e6cf6b38e704)

<br>

### 2) 클래스 로더 (Class Loader)

클래스 로더는 Byte Code인 클래스 파일(.class)을 동적으로 로드하고(`Loading`), 링크를 통해 배치하고(`Linking`), 초기화(`Initialization`)하는 역할을 담당한다.

#### (1) 로딩(Loading)

클래스 파일을 가져와서 JVM의 메모리에 로드한다.

#### (2) 링크(Linking)

클래스 파일을 사용하기 위해 검증, 준비, 분석하는 과정이다.

- 검증(Verify): 읽어온 클래스 파일이 JVM 명세에 명시된 대로 구성되어 있는지 검증한다.
- 준비(Prepare): 클래스가 필요로 하는 메모리를 할당한다.
- 분석(Resolve): 런타임 상수 풀 내 모든 심볼릭 레퍼런스들을 참조해 실제 값들을 동적으로 결정하는 과정이다.

#### (3) 초기화(Initialization)

static 변수들을 설정된 값으로 초기화하는 단계이며, static 블록이 활성화되기 시작하는 상태이다

<br>

### 3) 런타임 데이터 영역 (Runtime Data Area)

JVM의 **`메모리 영역으로 Java 어플리케이션을 실행할 때 사용되는 데이터들을 적재하는 공간`**이다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/247cc46d-17e4-4a5e-89ec-501fa2bb79f5)

#### (1) 메서드 영역 (Method Area)

JVM이 시작될 때 생성되는 공간으로 JVM의 모든 스레드가 공유하는 영역이다.

Byte Code를 처음 메모리 공간에 올릴 때, 초기화되는 대상(클래스, static 변수 등)을 저장하기 위한 메모리 공간이다. 간단히 말해 정적 필드(static field)와 클래스 구조(class structures)를 가지고 있는 영역이다.

- Field Info : 멤버 변수의 이름, 데이터 타입, 접근 제어자의 정보
- Method Info : 메서드 이름, return 타입, 함수 매개변수, 접근 제어자의 정보
- Type Info : Class/Interface 여부 저장, Type의 속성/이름, Super Class의 이름 등

#### ※ 런타임 상수 풀 (Runtime Constant Pool) 

메서드 영역에 존재하는 별도의 관리 영역으로, 상수 자료형을 저장하고 참조하여 중복을 막는 역할을 한다.

클래스를 생성할 때 참조해야할 정보들을 런타임 상수 풀이 가지고 있기 때문에 이를 참조하여 사용한다. 

#### (2) 힙 영역 (Heap Area)

`new`연산자로 생성되는 클래스와 인스턴스 변수 등 참조 타입(Reference Type)이 저장되는 곳이다.

기본 타입(Primary Type)일 경우 스택 영역(stack area)에서 직접 값을 가지고 있지만, 참조형 변수인 경우 스택 영역에서는 주소값만 갖고 있고, 해당 값이 필요할 때 주소값을 통해 힙 영역에 저장되어 있는 값을 참조하는 방식으로 동작한다.

힙 영역 역시 JVM의 모든 스레드에서 공유하는 영역이다.

#### (3) 스택 영역 (Stack Area)

int, boolean, long 등과 같은 기본형 자료형(Primary Type)의 값이 직접 저장되는 공간으로, 메서드 호출을 통해 해당 메서드 내에서만 임시적으로 사용되는 변수나 정보들이 저장되는 영역이다.

- 참조형의 경우 스택 영역에서는 주소값만 가지고 있다.

이렇게 메서드 내에서만 존재하는 공간을 스택 프레임이라고 하며, 각 메서드마다 개별적인 스택 프레임 가지고, 메서드 호출이 끝나면 해당 스택 프레임은 삭제된다.

※ 스택 프레임(stack frame)

메서드가 호출될 때마다 만들어지는 공간(프레임)이며, 현재 실행 중인 메서드 상태 정보를 저장하는 곳을 말한다.

#### (4) PC 레지스터 (Program Counter Register)

스레드가 시작될 때 생성되며, 현재 수행 중인 JVM 명령어 주소를 저장하고 있는 공간이다.

JVM 명령어 주소는 스레드가 어떤 부분을 무슨 명령어로 실행해야 할 지에 대한 기록을 가지고 있다.

#### (5) 네이티브 메서드 스택 (Native Method Stack)

실제 실행할 수 있는 기계어로 작성된 코드, 프로그램을 실행시키는 영역이다.

JIT 컴파일러에 의해 변환된 Native Code 역시 네이티브 메서드 스택에서 실행된다.

<br>

### 4) 실행 엔진 (Execution Engine)

실행 엔진은 클래스 로더를 통해 런타임 데이터 영역에 배치된 Byte Code를 해석해서 읽고 실행하는 역할을 담당한다. .class 파일의 내용은 가상 머신이 이해할 수 있는 중간 정도 레벨로 컴파일된 상태이기 때문에 실행 엔진이 각 기계(OS)에 맞는 형태로 실행될 수 있도록 기계어를 변환해서 실행하는 역할을 하는 것이다.

실행 엔진은 인터프리터(Interpreter)와 JIT 컴파일러(JIT Compiler)를 혼용하여 이 작업들을 수행한다.

#### (1) 인터프리터 (Interpreter)

Byte Code 명령어를 하나씩 읽어서 해석하고 실행한다. 명령어를 하나씩 읽고 해석해서 실행하기 때문에 상대적으로 수행 속도가 느릴 수밖에 없다.

#### (2) JIT 컴파일러 (Just-In-Time Compiler)

인터프리터의 단점을 보완하기 위해 도입된 방식으로, 반복되는 Byte Code 전체를 컴파일하여 Native Code로 변경하고 `캐싱`해두었다가 해당 메서드의 Byte Code가 호출되었을 때, Native Code로 직접 실행한다.

※ 네이티브 코드(Native Code)란, Java의 부모가 되는 C언어나 C++, 어셈블리어로 구현된 코드를 의미한다.

매번 해석하고 실행하는 것보다 캐싱하여 Native Code를 실행하는 것이기 때문에 인터프리터 방식보다 빠르다는 장점이 있다.

그럼 모든 Byte Code를 Native Code로 변환하는 게 더 낫지 않나라고 생각할 수도 있겠지만, 변환하는 데에도 비용이 들기 때문에 모든 것을 JIT Compiler가 담당하지 않고 인터프리터 방식을 사용하다가 일정 기준을 넘어가면 JIT Compiler를 사용해서 변환, 캐싱하는 방법을 사용한다.

#### (3) 가비지 컬렉터 (Garbage Collector; GC)

힙 영역(Heap Area)에서 더 이상 사용되지 않는/참조되지 않는 오브젝트들을 삭제하여, 낭비되고 있는 메모리를 자동으로 회수해주는 역할을 한다.

C의 경우 개발자가 직접 메모리를 관리해줘야 하지만, Java는 가비지 컬렉터(GC)가 존재하여 실시간으로 메모리를 최적화 시켜준다. (단, 자동으로 실행되는 것은 맞지만 매 순간마다 실행되는 것이 아니다.)

<br>

### 5) 기타

#### (1) 자바 네이티브 인터페이스 (Java Native Interface; JNI)

네이티브 메서드 라이브러리(Native Method Libraries)와 상호 작용하며 코드 실행을 위한 C, C++의 네이티브 라이브러리를 제공하는 인터페이스다.

JVM이 C, C++ 라이브러리들을 호출할 특정 상황이 있을 때, 이를 가능케 해주는 영역이다.

#### (2) 네이티브 메서드 라이브러리 (Native Method Libraries)

실행 엔진의 작동에 필요한 C, C++ 라이브러리들을 모아놓은 집합소



참고 문헌: https://www.linkedin.com/pulse/jvm-architecture-how-internally-work-ali-as-ad