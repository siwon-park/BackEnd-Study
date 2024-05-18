# 15_가비지 컬렉션(Garbage Collection; GC)

> Java의 메모리 관리 방법 중 하나로 JVM의 Heap 영역에 동적으로 할당했던 메모리 중 필요 없게 된 메모리 객체 (garbage)들을 모아 주기적으로 제거하는 프로세스

C, C++의 경우 메모리 할당과 해제 등 메모리 관리를 개발자가 직접해줘야 하는 반면, Java의 경우 GC가 있어서 메모리 관리 및 메모리 누수 문제에 대해 비교적 자유로워 `개발에만 집중할 수 있다는 장점`이 있다.

Java 뿐만 아니라, Python, JS, GoLang 에도 GC가 기본적으로 내장되어 있다.

그러나 GC에도 단점이 존재하는데, 자동으로 필요 없는 메모리 객체를 처리해준다 해도 `언제 해제되는지 정확하게 알 수 없어 제어가 힘들며`, `GC가 동작하는 동안에는 다른 동작을 멈추기 때문에 오버헤드가 발생`하는 문제가 있다.

- GC가 동작하는 동안 다른 동작을 멈추는 행동을 `Stop-The-World`라고 한다.
  - Stop-The-World: GC를 수행하기 위해 JVM이 프로그램 실행을 멈추는 현상
  - 이 시간이 길어지면 서비스에 차질이 생기기 때문에 이를 최소화하는 것이 핵심 쟁점이다

이처럼 GC가 너무 자주 실행될 경우 성능 하락의 문제가 있어, `GC 최적화`라는 과제가 남아 있다.

- GC 최적화 작업을 GC 튜닝이라고 한다.

<br>

## 1. 가비지 컬렉션 대상

>Unreachable: 객체가 참조되고 않지 있는 상태 (GC 대상)

GC는 특정 객체가 Garbage인지 아닌지 판단하기 위해 도달성(Reachability)라는 개념을 적용하는데, 객체의 상태는 도달성에 따라 2가지로 나뉜다.

- Reachable: 객체가 참조되고 있는 상태
- Unreachable: 객체가 참조되고 않지 있는 상태 (GC 대상)

객체에 레퍼런스가 있다면 Reachable 상태로 구분되고, 유효한 레퍼런스가 없다면 Unreachable 상태로 구분되어 제거 대상이 된다.

<br>

## 2. 가비지 컬렉션 방법

### 1) Mark & Sweep

GC 대상이 될 객체를 `식별(Mark)`하고, `제거(Sweep)`하는 방식으로 동작한다.

- Mark: Root Space에서부터 그래프 순회를 통해 연결된 객체들을 찾아서 각각 어떤 객체를 참조하고 있는지 찾아서 마킹한다.
- Sweep: 마킹된 객체 즉, 참조하고 있지 않은 객체를 힙 영역에서 제거한다.
- 경우에 따라서 파편화된 메모리 영역을 채워나가는 작업(Compaction)을 수행하기도 한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/6a5f060b-97eb-437d-8094-1d9273427302)

### 2) Reference Counting

`객체를 참조하는 레퍼런스의 수를 추적`하여 저장하고 있다가, `레퍼런스 카운트 값이 0이 되면 메모리를 해제`한다.

- 객체가 처음 생성되면 레퍼런스 카운트는 1이지만, 객체를 복사하게 되면 그 수는 증가한다.
- 변수의 값이 바뀌거나, 스코프를 벗어나면 레퍼런스 카운트는 1씩 감소한다.

단, 레퍼런스 카운팅 방식의 문제점은 `순환 참조`가 있다. 실제 사용하지 않는 객체임에도 불구하고 서로 참조하고 있어 레퍼런스 카운트가 0이 되지 않아 GC의 대상에서 벗어나게 되어 메모리 낭비를 초래한다.

<br>

## 3. 힙 메모리 구조

힙 영역은 처음 설계될 때, 다음과 같은 2가지 전제를 기반으로 설계되었다.

- 대부분의 객체는 금방 접근 불가능한 상태(Unreachable)가 된다.
- 오래된 객체에서 새로운 객체로의 참조는 아주 적게 존재한다.

즉, 결론적으로 객체는 대부분 일회성이며, 메모리에 계속해서 오래 상주하는 경우는 드물다는 것이다.

이러한 전제를 기반으로 힙 영역은 객체 생존 기간에 따라 `Young`과 `Old` 총 2가지 영역으로 설계하였다.

- 초기에는 Permanent 영역도 존재했지만 Java8부터 Native Method Area로 이전되었다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/6249ad61-8aea-4ce4-b248-87de3cc708c9)

### 1) Young Generation (Young 영역)

> 새롭게 생성된 객체가 할당(Allocation)되는 영역

대부분의 객체가 금방 접근 불가능한 상태가 된다고 했으므로, 많은 객체가 Young 영역에 있다가 사라진다.

Young 영역의 크기는 Old 영역에 비해 적은데, 그 이유는 대부분의 객체들이 짧은 시간에 생겼다가 사라지기 때문에 큰 공간을 필요로 하지 않기 때문이다.

Young 영역은 3가지 영역으로 세분화되는데 Eden, Survival 0, Survival 1이다.

#### (1) Eden

`new`를 통해 새로 생성된 객체가 위치하는 곳으로, 정기적인 가비지 수집 후 살아남은 객체들은 Survivor

#### (2) Survivor 0 / 1

최소 1번 이상으로 GC로부터 살아남은 객체들이 존재하는 영역

- survivor 영역에는 특별한 규칙이 있는데, 0과 1 둘 중 한 영역은 반드시 비어 있어야 한다.

Young 영역에 대한 GC를 **Minor GC**라고 부른다.

### 2) Old Generation (Old 영역)

> Young 영역에서 Reachable 상태를 유지하여 살아남은 객체가 복사되어 남아 있는 영역

객체가 계속해서 참조되어 살아남았다는 것은 그만큼 중요한 객체라는 의미이므로 복사하여 Old 영역으로 이전된다. Old 영역은 Young 영역보다 메모리가 크게 할당되어 있으며, 많이 참조되고 중요한 객체들이 옮겨간 만큼 가비지는 적다.

Old 영역에 대한 GC를 **Major GC**라고 부른다.

<br>

## 4. 가비지 컬렉션 동작 방식

| 구분      | Minor GC                            | Major GC              |
| --------- | ----------------------------------- | --------------------- |
| 대상      | Young 영역                          | Old 영역              |
| 실행 시점 | Young 영역 내 Eden 영역이 꽉찬 경우 | Old 영역이 꽉 찬 경우 |
| 실행 속도 | 빠르다                              | 느리다                |

### 1) Minor GC

> Young 영역에 대한 GC

Minor GC는 Young 영역에 대해 발생하는 GC를 말하며, Eden 영역이 가득 찼을 때 실행된다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/e4a76d08-7b80-4c04-9818-cb7f24e2ba5f)

#### (1) Minor GC 과정

##### [1단계] 

객체가 생성되어 Eden 영역에 위치한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/aa3c04ee-220b-4fdf-a152-9c2468b91650)

##### [2단계]

객체가 계속해서 생성되어 Eden 영역에 위치하다가, Eden 영역이 가득차면 Minor GC가 발생한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/14df28d5-6b66-48f5-a43a-d5819ae6f0b2)

##### [3단계]

참조되지 않는 객체들을 Mark한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/61856e74-7a3c-4789-9b6a-5288b872c900)

##### [4단계]

Mark한 객체들을 제거(Sweep)하여 메모리 해제 후, 살아남은 객체들을 Eden 영역에서 Survivor 0 영역으로 옮긴 다.

- 살아남은 객체들의 `age`값은 1씩 증가한다
  - `age`값은 객체가 살아남은 횟수를 의미하며, 임계치에 다다르면 Old 영역으로 이관 여부를 결정해 이관한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/837cd661-6a8c-40ee-b07f-de1acfe9c878)

##### [5단계]

다시 객체가 계속해서 Eden 영역에 새롭게 생성되다가, Eden 영역이 가득차서 Minor GC가 발생한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/648f5d95-2afe-4413-bf03-9854f99c658b)

##### [6단계]

Young 영역 전체에 걸쳐 참조되지 않는 객체들을 Mark한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/803cefe2-bb76-4089-80db-aa9c236162da)

##### [7단계]

Mark한 객체들을 제거(Sweep)하여 메모리 해제 후, 살아남은 객체들을 비어있는 Survivor 영역으로 이관한다.

- 이관 후 살아남은 객체들의 age 값을 1씩 증가시킨다.
- survivor 0과 1 중 하나는 반드시 비어있으므로 Minor GC가 발생할 때마다 한 쪽에서 다른 한 쪽으로 계속해서 이동이 발생하게 된다.
  - 즉, 아래 예시 그림에서 Minor GC가 한 번 더 발생하면 survivor 1 영역에서 살아남은 객체들은 Eden 영역에서 살아남은 객체들과 함께 survivor 0으로 이동한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/78e3e13b-2088-4f22-b6b9-69e5c4019f84)

이러한 과정이 계속해서 반복되는 것이 Minor GC 과정이다.

<br>

### 2) Major GC

> Old 영역에 대한 GC

Major GC는 Young 영역에서 계속해서 살아남은 객체들이 age값이 가득차서 Promotion 되어 Old 영역으로 이관되는데, 이관된 객체가 Old 영역을 가득 채워 메모리가 부족해졌을 때 발생한다.

Major GC의 경우 `Full GC`라도고 부른다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/e8efde16-aec1-4c36-9f65-ac96eceb6913)

#### (1) Major GC 과정

##### [1단계]

Young 영역에서 살아남은 객체들의 age가 임계치에 다다른다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/64faaa4a-25d3-4b04-bc27-a01cdd0a89d5)

##### [2단계]

임계치에 다다른 객체에 대해 Promotion 여부를 결정하여 Old 영역으로 이동시킨다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/831f6147-bc08-492e-a5e7-1d3d73554d49)

##### [3단계]

위 과정이 계속해서 반복되어 Old 영역이 가득차 메모리가 부족하게 되면 Major GC가 발생한다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/95370d6d-5250-4606-a96a-339dc792cc8c)

Young 영역의 경우 Old 영역에 비해 크기가 작기 때문에 Minor GC는 보통 0.5초 ~ 1초 사이에 끝나지만, 위 그림의 예시에서 보듯이 Old 영역이 더 크기 때문에 Major GC의 경우 Minor GC에 비해 10배의 시간을 사용한다.

여기서 바로 Stop-The-World 문제가 발생한다. 10배의 시간이면 최소 5초 ~ 10초인데, 컴퓨터 상으로는 엄청나게 큰 시간이며, 이 시간 동안 GC가 동작하여 스레드가 멈추고 CPU에 많은 부하를 주기 때문에 Major GC에 대한 최적화가 필요하다.

<br>

## 5. 가비지 컬렉션 알고리즘

JVM이 GC를 통해 자동적으로 메모리를 관리해주는 점은 큰 장점이지만, GC가 발생함에 따라 어플리케이션이 잠깐 중지되는 현상이 발생할 수도 있어 이를 최적화하기 위해 다양한 GC 알고리즘이 개발되었다.

![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/488cf4af-c551-4a4d-ab7e-704088dba860)

GC 알고리즘 종류로는 Serial, ConcMarkSweep, Parallel, G1, ZGC, Shenandoah, Epsilon 등이 있으며, 상황에 따라 필요한 GC 방식을 설정해서 사용할 수 있다.

자바 프로그램을 실행할 때, GC 옵션을 지정하는 방식으로 선택한 GC 알고리즘으로 힙 영역의 메모리를 관리할 수 있다.

- 예시) Serial GC 실행 명령

```bash
java --XX:+UserSerialGC -jar [실행할 java 파일명].java
```

<br>

### 1) Serial GC

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/4187fdcb-54b5-43ef-9f12-18312e6f8240)![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/4f405c06-b3d7-4db7-b499-c780eb957e3a)

- 싱글 스레드로 GC 작업이 수행되어 느리다.
- Mark-Sweep-Compact 알고리즘을 사용한다.
- 이제는 거의 쓰이지 않는 GC 알고리즘이다.

<br>

### 2) Parallel GC

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/83e8426d-7de5-4878-9e82-e097dadf1752)

- JDK 8의 기본 GC 알고리즘으로 멀티 스레드 방식으로 GC가 수행된다.
- 멀티 스레딩 방식으로 GC가 수행되다보니 Serial GC에 비해 빠르다.
  - Young 영역만 멀티 스레드로 수행되고 Old 영역은 싱글 스레드라는 한계.

<br>

### 3) CMS GC

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/cf581cdb-1930-49a1-944b-4e62a7740971)

- Stop The World 현상 때문에 Java 어플리케이션이 멈추는 현상을 줄이는데 초점을 둔 GC 알고리즘
- JDK9 이후 사용이 줄었고, JDK14 버전대부터는 지원 종료
- 마킹을 여러 단계에 걸쳐 끊어서 적용하고 Sweeping 하는 방식으로 이루어진다.
- Compact 과정은 별도 존재하지 않는다. Compact 과정이 있으면 결국 시간적으로 이점을 보기위해 적용한 부분들이 상쇄되기 때문이다.

<br>

### 4) G1 GC

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/bfa2e1e7-0166-446f-86ef-9b394eaf7c0a)![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/319b9acf-f4f0-4a1c-b146-e5577b429a40)

- JDK9의 기본 GC로 메모리와 CPU를 많이 사용하는 CMS GC를 개선하기 위해 나온 방식이다.
- 기존의 GC에서 구분한 Heap 영역과 달리 `Region(지역)`이라는 개념을 사용한다.
- 각 region은 Eden, Survivor, Old 영역 등 다양하게 동적으로 역할을 부여 받아 구성되어 있다.
  - 기존 GC와 달리 Humonogous라는 객체의 크기가 region의 50%를 넘는 큰 객체들을 저장하기 위한 영역이 별도 존재한다.

<br>

### 5) ZGC

![image](https://github.com/siwon-park/BackEnd-Study/assets/93081720/4e1c8a8b-3ac7-471d-bd60-ffe0e9a85f34)

- JDK11부터 실험적으로 도입된 GC로 region이 아니라 z page라는 영역으로 나눠서 힙 메모리를 관리한다.
- z page는 2의 제곱 크기로 다양한 사이즈로 할당된다.
- Stop The World 현상이 짧은 것이 가장 큰 장점이다.
