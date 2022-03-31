# 자바 스터디

자바의 정석, 점프 투 자바를 학습하고 필요한 내용들을 기록

### < 자바의 특징 >

1. **간단하다 (Simple)**

   - 자바는 C++에 가깝지만 훨씬 간단하다.

2. **객체 지향적이다 (Object-oriented)**

   - 자바는 숫자(int, float, long 등)나 논리값(true, flase)을 제외한 거의 모든 것이 객체로 구성되어 있으며 실제 자바는 Object 클래스에서 모든 클래스를 파생한다. 객체의 변수 값이 독립적으로 유지된다.

3. **인터프리터 언어이다 (Interpreted)**

   - 자바는 컴파일 언어인 동시에 인터프리터 언어이다. 먼저 텍스트 소스를 컴파일 하여 2진 파일(클래스 파일)로 만든 다음 자바 런타임이 클래스 파일을 인터프리트 하면서 실행한다.

   ![설명1](http://wikidocs.net/images/page/256/compile.png)

   - 따라서 컴파일 언어에 가까운 속도와 시스템 독립성을 동시에 얻었다.

4. **강력하다 (Robust)**

   - 자바는 포인터 연산을 지원하지 않는다. 따라서 잘못된 주소를 가르킬 가능성을 사전에 없앴다.
   - 자바는 모든 메모리 접근을 자바 시스템이 관리하고 제한하며, 예외 핸들링을 하여 시스템 붕괴 우려가 없다.

5. **안전하다 (Secured)**

   - 자바는 포인터 개념이 없고, 유형 정의가 강고하다.
   - 프로그램 작성 시 자료형 타입에 굉장히 민감하다(=항상 타입체크를 진행한다)
   - 덕분에 코드가 매우 명확해지며, 일단 컴파일 되었다면 코드에 결정적인 문제는 일차적으로 없는 셈이다.

6. **플랫폼 독립적이다 (Platform independent)**

   - 자바의 실행 파일은 이진 코드(클래스) 파일이다. 따라서 자바 런타임이 설치된 시스템에서는 어디서나 자바 프로그램을 실행할 수 있다.
   - Java Virtual Machine 덕분에 한번 작성된 프로그램은 os에 상관없이 어디서든 돌려 볼 수 있다.

7. **멀티 쓰레딩을 지원한다 (Multithreaded)**

   - 멀티 쓰레드를 지원할 경우 하나의 프로그램 단위가 동일한 쓰레드를 동시에 수행할 수 있다.
   - 따라서 멀티 CPU 시스템에서 보다 높은 효율을 낼 수 있다.

8. **동적이다 (Dynamic)**

   - 자바 인터페이스(Interface)를 사용하면 하나의 모듈을 갱신할 때, 다른 모듈을 모두 갱신할 필요가 없다. 인터페이스가 모든 인스턴스 변수와 도구의 실행문을 배제한 채 객체 간 상호 작용을 정의하기 때문이다.

------

### <자바 소스코드의 기본 구조>

파일명: 클래스명.java

```java
/* 클래스 블록 */
public class 클래스명 {
    /* 메소드 블록1 */
    [public|private|protected] [static] (리턴자료형|void) 메소드명1(입력자료형 입력변수, ...) {
        명령문(statement);
        ...
    }
    /* 메소드 블록2 */
    [public|private|protected] [static] (리턴자료형|void) 메소드명2(입력자료형 입력변수, ...) {
        명령문(statement);
        ...
    }
    ...
}

//Sample.java Hellow World 소스코드 예시
public class Sample {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

- 소스 파일(*.java)과 public class명은 일치해야한다.
- 클래스 파일(*.class)은 클래스마다 하나씩 만들어지므로 소스파일을 컴파일하면 소스 파일에 있는
  클래스만큼 클래스 파일이 생성된다.



##### ※ main 메서드(`public static void main(~~~)`)

- 자바 Application에 필수적으로 있어야 하는 특수한 메서드
- 자바 Application이 실행될 때 자동으로 실행됨

<일반적인 Flow>

1. main 메서드 내에서 다른 클래스 '객체' 생성
2. 해당 객체의 메서드 호출 및 객체의 변수 조작
3. 자바 Application으로부터 원하는 결과 획득

------

### <클래스(Class) 명>

##### 클래스명 명명 규칙(general)

- **명사**로 명명 한다
- 두 개 이상의 단어가 섞이는 경우 각 단어의 첫번째 문자는 대문자로 한다.
  - 예) class **C**amel**C**ase {}



### < 메서드 명>

##### 메서드명 명명 규칙(general)

- 동사로 명명한다.

- 두 개 이상의 단어가 섞이는 경우  각 단어의 첫번째 문자는 대문자로 하되, 메서드 명의 첫 글자는 소문자로 한다.
  - 예) **r**un**F**ast(); ,**g**et**B**ackground();



### <변수명>

##### 변수명 명명 규칙

- 변수명은 숫자로 시작할 수 없다.
- 특수문자는 _와 $ 외에 사용할 수 없다.
- int, class, return과 같은 자바 키워드는 변수명으로 사용할 수 없다. 
  - 단, 단독으로만 사용할 수 없지, 다른 단어와 섞어서는 사용 가능하다 예) String this_apple = ...


```
자바 키워드
abstract  continue  for         new        switch
assert    default   goto        package    synchronized
boolean   do        if          private    this
break     double    implements  protected  throw
byte      else      import      public     throws
case      enum      instanceof  return     transient
catch     extends   int         short      try
char      final     interface   static     void
class     finally   long        strictfp   volatile
const     float     native      super      while
```

----

### <자료형>

#### 변수, 상수, 리터럴

- **변수(variable)**: 하나의 값을 저장하기 위한 공간
- **상수(constant)**: 값을 한번만 저장할 수 있는 공간. 보통 이름을 모두 대문자로 하며, final 키워드를 앞에 붙임
- **리터럴(literal)**: 값 그 자체를 의미

```java
// year는 변수, NAME은 상수, 2022와 "SIWON PARK"은 리터럴임
int year = 2022;
final String NAME = "SIWON PARK";
```



#### 원시(primitive) 자료형

- int, long, double, float, boolean, char 등은 원시(primitive) 자료형이라고 부른다. 이러한 자료형들은 <span style="color:Red">`new`</span> 키워드로 생성할 수 없다. (= 객체로서 만들 수 없다)
  - <span style="color:Red">`new`</span>키워드로 생성한다는 것은 새로운 객체를 만든다는 것을 의미한다.
  - 원시 자료형은 <span style="color:Red">리터럴(literal)</span>로만 값을 세팅할 수 있다.
    - `리터럴 표기`란 객체 생성 없이 고정된 값을 그대로 대입하는 방법을 의미한다.
      - 예) int x = 12;

| 원시 자료형 | Wrapper 클래스 |
| ----------- | -------------- |
| int         | Integer        |
| long        | Long           |
| double      | Double         |
| float       | Float          |
| boolean     | Boolean        |
| char        | Char           |

※ ArrayList, HashMap, HashSet 등은 데이터 저장시 원시 자료형에 상응하는 Wrapper 클래스들을 사용해야한다. 원시 자료형 대신에 Wrapper 클래스를 사용하면 값 대신 객체를 주고 받는 것이므로 코드를 객체 중심적으로 작성할 수 있다.

> 주의!

`String`은 리터럴 표기가 가능하지만, 원시 자료형은 아니다. 리터럴 표기가 가능하도록 자바에서 특별 대우 해주는 예외 케이스이다.

```java
// 예시
String a = "happy java" // 리터럴 표기(사용 가능)
    
String a = new String("happy java") // String 객체 생성
```

-------------------

### <숫자형>

#### 정수형(int, long)

int : -2147483648 ~ 2147483647(약 -21.4억 ~ 21.4억의 범위)

long: -9223372036854775808 ~ 9223372036854775807(약 -900경 ~ 900경의 범위)

> 주의!

long 변수에 값을 대입할 때는 대입하는 숫자 값이 int 자료형의 범위를 벗어나는 경우, 해당 숫자값 뒤에 `L`을 붙여줘야 한다.

```java
long countOfStar = 8764827384923849L; // 대문자 L 또는 소문자 l을 붙여줘야한다
```

#### 실수(double, float)

double: `-1.7*10^308 ~ 1.7*10^308` ※ 자바에서 실수형은 디폴트가 double이다.

float: `-3.4*10^38 ~ 3.4*10^38`

```java
float pi = 3.14F;
double morePi = 3.14159265358979323846;
```



> **증감 연산자(++, --)**

- `i++` : i가 참조된 후 증가 → 해당 코드가 실행된 후 증가하는 개념
- `++i` : i가 참조되기 전 증가 → 증가되면서 해당 코드를 실행

```java
int count = 1; // 1 → 정수형 count변수에 1을 할당함
int total = ++count; // 2 → 먼저 count+1 한 후, 2가 정수형 total변수에 할당
int total = count++; // 3 → total에 먼저 2가 할당된 후에 +1하여 3이 됨
```



----------

### <문자(char)>

> 주의!

- 문자(char)과 문자열(String)은 다른 개념임
  - char은 한 개의 문자 값이고, String은 말 그대로 문자들의 집합, 문자열임
  - char를 리터럴로 선언할 때 반드시 일반 따옴표(`' '`)를 쓴다. 쌍따옴표는 X! 
- 문자 리터럴을 생성하고자 할 때는 반드시 따옴표 안에 공백이 있어야 한다.
  - char ch = " "; (O) / char ch = ""; (X)


```java
char a1 = 'a';  // 문자
char a2 = 97;   // 아스키 코드
char a3 = '\u0061';   // 유니코드
// 세 개의 출력 값은 모두 같음
```

------------------

### <문자열(String)>

String 자료형은 값을 한번 생성하고나면 해당 값을 변경할 수 없다(Immutable)

- 문자열을 생성하고자 할 때는 다음 두 가지의 방법 모두 가능하다.

```java
String a = "Happy SIWON"; // 리터럴 표기로 문자열 값 세팅
String b = new String("Amazing SIWON"); // 새로운 String 객체 생성
```

##### 문자열 내장 메서드

- `객체.length()` : 문자열 객체의 길이를 반환

```java
String a = "Happy Siwon";
System.out.println(a.length()); // 11
```



- `객체.equals(객체)` : 두 개의 문자열 객체가 동일한지 비교하여 결과값을 반환함

```java
String a = "Happy SIWON";
String b = new String("Amazing SIWON");
System.out.println(a.equals(b)); // true 동일 문자열 '값'인지 비교 
System.out.println(a == b); // false 동일 문자열 '객체'인지 비교
```

> 주의!
>
> 값을 비교할 때는 `.equals`를 사용해야한다. `==` 은 동일한 객체인지 판별할 때 사용함



- `객체.indexOf("문자열1")` : 문자열 객체에서 "문자열1"이라는 문자열이 ''시작하는 위치''를 반환함

```java
String a = "Amazing SIWON";
System.out.println(a.indexOf("SIWON")); // 8
```



- `객체.contains("문자열1")` : 문자열 객체에 "문자열1"이 포함되어 있는지 여부를 반환함

```java
String a = "Happy SIWON";
System.out.println(a.contains("sad")); // false
```



- `객체.charAt(인덱스)` : 문자열 객체에서 숫자번째 인덱스에 위치하는 문자를 반환함

```java
String a = "Amazing SIWON";
System.out.println(a.charAt(1)); // m
```



- `객체.replaceAll("대상 문자열", "대체 문자열")` : 문자열 객체에서 대상 문자열을 대체 문자열로 변환

```java
String a = "Amazing SIWON";
System.out.println(a.replaceAll("SIWON", "Spider-Man")) // "Amazing Spider-Man"
```



- `객체.substring(시작 인덱스, 끝 인덱스)` : 문자열 객체에서 시작 인덱스 ~ 끝 인덱스 직전까지의 문자열 반환

```java
String a = "Happy SIWON";
System.out.println(a.substring(6, 11)); // "SIWON"
```



- `객체.toUpperCase()` : 문자열 객체를 대문자로 변환 
- `객체.toLowerCase()` : 문자열 객체를 소문자로 변환

```java
String a = "Amazing SIWON";
System.out.println(a.toUpperCase()); // "AMAZING SIWON"
System.out.println(a.toLowerCase()); // "amazing siwon"
```



- `객체.split("구분자")` : 문자열 객체를 ''구분자로 분리한 결과''를 ''문자열 배열''로 반환

```java
String a = "Amazing-SIWON";
String[] result = a.split("-"); // result는 배열 {"Amazing", "SIWON"}
```



##### 문자열 포메팅

- 문자열 포멧 코드(`String.format("문자열", "문자1", 숫자, ...)`)

| 코드 | 설명            | 예시                                                         |
| ---- | --------------- | ------------------------------------------------------------ |
| %s   | 문자열(String)  | System.out.println(String.format("I love %s", "you")); # "I love you" |
| %c   | 문자 1개(char)  | System.out.println(String.format("%c love you", "I")); # "I love you" |
| %d   | 정수(Integer)   | System.out.println(String.format("I love you %d", 300); # I love you 300 |
| %f   | 부동소수(float) | System.out.println(String.format("%.4f", 3.141529)); # 3.1415 |
| %%   | %문자 자체      | System.out.println(String.format("Error is %d%%.", 98)); # Error is 98% |
| %o   | 8진수           |                                                              |
| %x   | 16진수          |                                                              |

- `System.out.printf()` : Sting.format을 출력한 것과 동일한 기능을 함

```java
System.out.printf("I love you %d",3000); // "I love you 3000"
```

--------

### < StringBuffer >

StringBuffer는 문자열을 추가하거나 변경 할 때 주로 사용하는 자료형이다.  StringBuffer는 mutable자료형인 반면, String 자료형은 값을 생성하고 나면 변경 불가능하다. -> String 메서드의 toUpperCase와 같이 대문자를 반환하는 것은 값을 변경한 것이 아니라 새로운 객체를 하나 더 생성한 것이다.

##### StringBuffer 메서드

- `객체.append("문자열")` : 객체에 문자열을 추가
- `객체.toString()` : StringBuffer객체를 String 자료형으로 변환

```java
StringBuffer sb = new StringBuffer(); //StringBuffer 객체 sb를 생성함
sb.append("Happy");
sb.append(" ");
sb.append("SIWON");
String result = sb.toString(); // String 객체를 생성하고 sb객체 값을 할당
System.out.println(result); // "HAPPY SIWON"

// String 자료형과 비교
String result = "";
result += "Happy";
result += " ";
result += "SIWON";
System.out.println(result); // "Happy SIWON"

// StringBuffer는 객체를 한 번 생성했지만, String 객체는 총 4번 생성되었다.
// 그럼 무조건 StringBuffer를 쓰는게 좋을까? -> 상황에 따라 다름
// StringBuffer 자료형은 String 자료형보다 무거운 편에 속한다. 메모리 사용량도 많고 속도도 느리다. 따라서 문자열 추가나 변경 작업이 많다면 StringBuffer를, 그게 아니라면 String을 쓰는게 유리
```



- `객체.insert(인덱스, "문자열")` : 객체의 원하는 위치에 문자열을 삽입함

```java
StringBuffer sb = new StringBuffer();
sb.append("SIWON");
sb.insert(0, "Amazing-");
System.out.println(sb.toString()); // "Amazing-SIWON"
```



- `객체.substring(시작 인덱스, 끝 인덱스)` : 객체에서 시작 인덱스~끝 인덱스-1까지의 문자열을 반환함

```java
StringBuffer sb = new StringBuffer();
sb.append("Amazing-SIWON");
System.out.println(sb.substring(0, 7)); // "Amazing"
```

------

### <배열(Array)>

같은 자료형의 데이터들의 모임. 배열로 선언된 변수들은 연속된 데이터 공간에 할당됨

#### 배열의 특징

- 길이가 고정되어 있음(생성 시 고정함)
- 배열 생성 시 초기 기본값은 배열에 넣는 자료형 타입에 따라 다름
  - `int` : 0
  - `boolean` : false
  - `char` : '￦u000'
  - 참조형: null

#### 배열의 생성

- 배열의 생성은 보통 자료형 타입(int, String 등) 옆에 `[]`기호를 쓰고 =  `new Wrapper클래스[배열 길이]`형태로 선언한다.

```java
// 배열의 생성 예시
int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
int[] numbers = new int[10]; // 초깃값은 0
String[] weeks = {"월", "화", "수", "목", "금", "토", "일"};
// 배열 상수 {"월", "화", ...}는 선언할 때만 사용 가능함
String[] weeks = new String[7];
```

다음과 같은 방법으로도 생성할 수 있다.

[1차원]

- `<데이터 타입> <배열 변수명>[] = new <데이터 타입>[배열의 크기];`
- `<데이터 타입>[] <배열 변수명> = new <데이터 타입>[배열의 크기];`

[2차원]

- `<데이터 타입>[][] <배열 변수명> = new <데이터 타입>[배열 크기1][배열 크기2];`
- `<데이터 타입> <배열 변수명>[][] = new <데이터 타입>[배열 크기1][배열 크기2]`;
- `<데이터 타입>[] <배열 변수명>[] = new <데이터 타입>[배열 크기1][배열 크기2]`;

```java
String scoreList = new String[3][4]; // 3X4배열 생성

int[][] scoreList = new int[3][]; // 3xn배열 생성
scoreList[0] = new int[2]; // 0번 배열에는 길이 2의 배열
scoreList[1] = new int[3]; // 1번 배열에는 길이 3의 배열
scoreList[2] = new int[4]; // 2번 배열에는 길이 4의 배열
// [[0,1],[0,1,2],[0,1,2,3]] 대충 이런식
```

#### 배열 메서드

- `객체.length` : 배열 객체의 길이를 반환함 



##### 배열 출력

- `Arrays.toString(배열 객체)` : 배열 안의 요소를 `[요소, 요소, 요소, ...]`의 형태로 출력
  1차원의 배열에만 사용 가능

```java
int[] prime = new int [3];
prime[0] = 2;
prime[1] = 3;
prime[2] = 5;
System.out.println(Arrays.toString(prime)); // [2, 3, 5]
for (int i=0; i<prime.length; i++){
    System.out.println(prime[i]); // 한줄 씩 2, 3, 5 출력
}
```

- `Arrays.deepToString(다차원 배열)` : 2차원 배열을 `[[요소1, 요소2], [요소3, 요소4]]`형태로 출력
  다차원 배열에 사용가능. 배열의 모든 요소에 재귀적으로 접근해 문자열을 구성해서 반환하는 방식으로 동작

```java
int[][] arr = {{1, 2}, {3, 4}}
System.out.println(Arrays.deepToString(arr)); // [[1, 2], [3, 4]]
```



##### 배열 비교

- `Arrays.equals(배열1, 배열2)` : 두 일차원의 배열을 비교함
- `Arrays.deepEquals(배열1, 배열2)` : 다차원 배열을 비교함

```java
String[][] strArr1 = new String[][] {{"Amazing", "Siwon"}, {"Hello", "Java"}}
String[][] strArr2 = new String[][] {{"Amazing", "Siwon"}, {"Hello", "Java"}}
System.out.println(Arrays.equals(strArr1, strArr2)); // false
System.out.println(Arrays.deepEquals(strArr1, strArr2)); // true
```



##### 배열 정렬

- `Arrays.sort(배열)` : 배열을 정렬함

```java
int[] arr = new int[] {0, 9, 7, 8, 5, 4, 6, 2, 1, 3};
Arrays.sort(arr);
System.out.println(Arrays.toString(arr)); // [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```



##### 배열 복사

- `Arrays.copyOf(배열, 배열의 길이)`: 배열 전체 또는 (처음에서)지정한 길이만큼을 복사
- `Arrays.copyOfRange(배열, 시작, 끝)`: 배열의 특정 부분을 복사함

```java
int[] arr = new int[] {1, 2, 3, 4};
int[] copiedArr1 = Arrays.copyOf(arr, arr.length); // arr전체를 복사
int[] partArr1 = Arrays.copyOf(arr, 3); // arr의 처음부터 3만큼 복사함 // [1, 2, 3]
int[] copiedArr2 = Arrays.copyOfRange(arr, 1, 3); // arr를 1부터 3미만까지 복사함 // [1, 2]
```



- `System.arraycopy(src, srcPos, dest, destPos, length)`
  - `src: 원본 배열`, `srcPos: 원본 배열의 복사 시작 위치(0부터 시작)`, `length`: 복사할 크기
  - `dest: 복사할 배열`, `destPos: 복사 받을 시작 위치`

```java
String[] orgArr = {"봄", "여름", "가을"}; // 원본 배열, 배열 크기: 3
String[] destArr = new String[orgArr.length+1]; // 복사할 배열, 배열 크기: 원본 배열 + 1
System.arraycopy(orgArr, 0, destArr, 0, orgArr.length);
// 원본 배열의 0부터, 복사할 배열 0부터, 길이 3까지 복사함
destArr[3] = "겨울"; // 복사한 배열의 3번째 인덱스에 "겨울" 추가
System.out.println(Arrays.toString(destArr)); // [봄, 여름, 가을, 겨울]
```



> 예시) for each 구문을 활용한 배열의 최댓값, 최솟값 찾기

```java
int[] intArr = {3, 27, 13, 8, 235, 7, 22, 9, 435, 31, 54};
int minValue = Integer.MAX_VALUE;
int maxValue = Integer.MIN_VALUE;
// for each 구문
for (int num : intArr){
    minValue = Math.min(minValue, num);
    maxValue = Math.max(maxValue, num);
}
System.out.printf("min: %d, max: %d",minValue, maxValue); // min: 3, max: 435 // %n은 개행문자
```



> 예시) 2차원 배열의 원소 중 3의 배수 개수와 합을 출력

```java
public static void main(String[] args){
    int[][] grid = { 
        {2, 3, 1, 4, 7}, {8, 13, 3, 33, 1}, {7, 4, 5, 80, 12}, {17, 9, 11, 5, 4},
        {4, 5, 91, 27, 7}
    };
    int count = 0;
    int sum = 0;
    for (int[] row : grid) {
        for (int num : row) {
            if (num%3 == 0) {
                count++;
                sum+=num;
            }
        }
    } 
    System.out.printf("개수: %d, 총합: %d%n", count, sum);
}
```



-----------------

### < 리스트(List)>

리스트 자료형에는 ArrayList, Vector, LinkedList 등이 있다.

##### 리스트의 특징

- 배열과 달리 크기가 정해져 있지 않고 동적으로 변한다

##### 리스트 메서드

- `add(객체)` : 리스트에 자료를 삽입한다. (파이썬 `lst.append(n)`과 동일)

```java
import java.util.*; // 혹은 import java.util.ArrayList;
public class Sample {
    public static void main(String[] args) {
        ArrayList arr = new ArrayList();
        arr.add("138"); 
        arr.add("129");
        arr.add("142");
        arr.add(0,"133"); // 특정 위치에 삽입하는 것도 가능
    }
}
```

- `get(인덱스)` : 리스트에서 특정 인덱스의 값을 추출한다. (파이썬 `lst[i]`와 동일 )

```java
// 위의 예시 계속
System.out.println(arr.get(2)); // 129
```

- `size` : 리스트에 담긴 요소의 갯수( = 리스트의 길이)를 반환한다. (파이썬 `len(lst)`와 동일)

```java
System.out.println(arr.size()); // 4
```

- `contains(객체)` : 리스트 안에 해당 항목이 있는지 판별하여 boolean형으로 반환한다. (파이썬 `a in lst`와 동일) 

```java
System.out.println(arr.contains("142")); // true
```

- `remove(객체)` : 리스트에서 객체에 해당하는 항목을 삭제하고 삭제한 결과(true, false)를 반환한다.
- `remove(인덱스)` : 리스트에서 해당 인덱스의 항목을 삭제하고, 삭제된 항목을 리턴한다. (파이썬의 lst.pop()과 동일)

```java
// remove(객체)
System.out.println(arr.remove("138")); // true

// remove(인덱스)
System.out.println(arr.remove(0)); // 133
```

##### 리스트 정렬

- `Comparator.naturalOrder()` : 오름차순 정렬
- `Comparator.reverseOrder()` : 내림차순 정렬

```java
import java.util.*;
public class Sample {
    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList("138", "129", "142"));
        arr.sort(Comparator.naturalOrder());  // 오름차순 정렬
        System.out.println(arr);  // [129, 138, 142]
        arr.sort(Comparator.reverseOrder()); // 내림차순 정렬
        System.out.println(arr); // [142, 138, 129]
    }
}
```



> 리스트의 메서드는 아니지만, 리스트를 하나의 문자열로 만들 때 쓰는 String메서드

- `String.join("구분자", 배열 또는 리스트)`: 배열 또는 리스트 자료형에 "구분자"를 삽입하여 하나의 문자열로 만듦

```java
import java.util.*;
public class Sample {
    public static void main(String[] args) {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList("138", "129", "142"));
        String result = String.join(",", arr);
        System.out.println(result);  // "138,129,142"
```



##### 제네릭스(Generics)

`ArrayList<자료형> 객체명 = new ArrayList<>(); `  -> 좀 더 자주 쓰는 표기법

`ArrayList<자료형> 객체명 = new ArrayList<자료형>();`

여기서 `<자료형>`, `<>`  를 쓴 것을 제네릭스라고 한다.  제네릭스의 의미는 "ArrayList 안에 담을 수 있는 자료형은 <>안에 쓴 자료형뿐이다"라는 의미이다.

- 장점
  - 명확한 타입 체크가 가능하다
  - 값을 가져올 때 형변환을 하지 않아도 된다.
    - 제네릭스를 사용하지 않을 경우 자료를 추출했을 때, 기본적으로 Object자료형으로 인식한다

```java
// 제네릭스를 사용하지 않을 경우
ArrayList arr = new ArrayList();
arr.add("138");
String one = (String) arr.get(0); // Object자료형으로 인식한 것을 (Strings)로 캐스팅 해줌

// 제네릭스를 사용할 경우
ArrayList<String> arr = new ArrayList<>();
arr.add("138");
String one = arr.get(0); // 형 변환이 필요없음
```



##### ArrayList 생성 방법

```java
import java.util.*;

public class Sample {
    public static void main(String[] args) {
       	// 방법1) 객체 생성 후 직접 데이터 입력
        ArrayList<String> arr = new ArrayList<>();
        arr.add("138");
        arr.add("129");
        
        // 방법2-1) 이미 데이터가 있을 경우
        String[] data = {"138", "129", "142"};
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(data));
        // 방법2-2)
        ArrayList<String> arr = new ArrayList<>(Arrays.asList("138", "129", "142"));
    }
}
```

--------------

### < 맵(Map)>

키(Key)와 값(Value)을 한 쌍으로 가지는 자료형(=딕셔너리(dictionary))

맵의 가장 큰 특징은 순서에 의존하지 않고 키로 값을 가져오는데 있다.

맵 자료형에는 HashMap, LinkedHashMap, TreeMap 등이 있다.

> - LinkedHashMap : 입력된 순서대로 데이터를 저장함
>
> - TreeMap: 입력된 키의 오름차순 순서대로 데이터를 저장함

맵 자료형을 제네릭스를 사용해서 생성하고자 할 땐, 키와 값의 타입에 대해서 둘 다 정의해주어야 한다. 

#### HashMap

##### HashMap 메서드

- `객체.put(key, value)` : 맵 객체에 키와 값 쌍을 삽입

```java
import java.util.*;
public class Sample {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>(); // 문자열을 키와 값으로 가지는 맵
        map.put("people", "사람"); // "people":"사람"
        map.put("sports", "축구"); // "sports":"축구"
    }
}
```

- `객체.get(key)` : 키에 해당하는 값을 얻기 위해서는 다음과 같이 한다. 만약 키에 해당하는 값이 없을 경우 `null` 을 리턴한다.

```java
System.out.println(map.get("people")); // "사람"
System.out.println(map.get("Happy")); // null
```

- `객체.getOrDefault(key, value)` : `get`과 동일한 기능이지만, 키에 해당하는 값이 없을 경우 null을 반환하지 않고 기입한 기본값을 리턴한다. (파이썬 딕셔너리의 get메서드와 유사하지만, 자바에서는 기본값으로 맵에 추가되지는 않음)

```java
System.out.println(map.getOrDefault("Happy", "SIWON"));  // "SIWON"
```

- `객체.containsKey(key)` : 맵에 해당 키가 있는지 유무를 true, false로 반환한다.

```java
System.out.println(map.containsKey("people"));  // true
System.out.println(map.containsKey("Amazing"));  // false
```

- `객체.remove(key)` : 맵에서 키와 값의 쌍을 삭제한 후, 값을 반환한다. (키:쌍) 둘 다 삭제됨

```java
System.out.println(map.remove("people"));  // "사람"
```

- `객체.size()` : 맵의 갯수(크기)를 반환한다.

```java
System.out.println(map.size()); // 1
```

- `객체.keySet()` : 맵의 모든 키를 모아서 집합(Set) 자료형으로 반환한다.

```java
System.out.println(map.keySet()); // [baseball] <집합 자료형>
List<String> klst = new ArrayList<>(map.keySet()); // 집합 자료형을 리스트 자료형으로 변환
```

-------

### <집합(Set)>

집합 자료형에는 HashSet, LinkedHashSet, TreeSet 등이 있다.

- HashSet : 일반적인 집합 자료형
- TreeSet : 오름차순으로 값을 정렬하여 저장하는 특징이 있는 집합 자료형
- LinkedHashSet : 입력한 순서대로 값을 정렬하여 저장하는 특징이 있는 집합 자료형

##### 집합 자료형의 특징

- 중복을 허용하지 않는다.
- 순서가 없다(=인덱싱이 불가능하다)

##### 집합 자료형 생성

ArrayList객체를 생성하면서 HashSet 클래스를 입힌다고 보면 됨

```java
import java.util.*;
public class Sample {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>(Arrays.asList("H", "e", "l", "l", "o"));
        System.out.println(set);  //  [e, H, l, o]
    }
}
```

##### 집합 메서드

- `객체.add(자료형)` : 집합 자료형에 특정 값을 1개 추가함
- `객체.addAll(자료형, 자료형, ...)` : 집합 자료형에 값을 여러 개 추가함
- `객체.remove(자료형)` : 특정 값을 1개 제거함

```java
import java.util.*;

public class Sample {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        
        // 자료 추가
        set.add("Jump"); // 자료형 1개 추가
		set.addAll(Arrays.asList("To", "Java")); // 자료형 여러 개 추가
        System.out.println(set);  // [Java, To, Jump]
        
        // 자료 삭제
        set.remove("To");
        System.out.println(set);  // [Java, Jump]
    }
}
```



##### 교집합, 합집합, 차집합

```java
import java.util.*;
public class Sample {
    public static void main(String[] args) {
        HashSet<Integer> s1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        HashSet<Integer> s2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8, 9));
        
        // 교집합(retainAll)
        HashSet<Integer> intersection = new HashSet<>(s1);  // s1으로 intersection 생성
        intersection.retainAll(s2);  // 교집합 수행
        System.out.println(intersection);  // [4, 5, 6]
        
        // 합집합(addAll)
        HashSet<Integer> union = new HashSet<>(s1);  // s1으로 union 생성
        union.addAll(s2); // 합집합 수행
        System.out.println(union);  // [1, 2, 3, 4, 5, 6, 7, 8, 9]
        
        // 차집합(removeAll)
        HashSet<Integer> substract = new HashSet<>(s1);  // s1으로 substract 생성
        substract.removeAll(s2); // 차집합 수행
        System.out.println(substract);  // [1, 2, 3]
    }
}
```

--------------------

### <형 변환>

- Promotion(묵시적 형변환): 작은 데이터 → 큰 데이터(자동 캐스팅, 데이터 손실 위험이 없을 경우)

  <묵시적 형변환 규칙>

  - `byte` → `short` → `int` → `long` → `float` → `double`

  - `char` → `int` → `long` → `float` → `double`

- Demotion(명시적 형변환): 큰 데이터 → 작은 데이터(데이터 손실 때문에 명시적 캐스팅 필요)



**문자(char) → 숫자**

- `char - '0'`: 문자(char)에서 '0'을 뺀다

```java
char num = '3';
int num1 = num - '0';
```



**숫자 → 문자(char)**

- `int + '0'`: 숫자에 '0'을 더한다

```java
int num = 3;
char char_num = num + '0';
```



**문자열(String) → 문자(char)**

- `"문자열".charAt(0)`

```java
char num = "3".charAt(0);
```



**문자(char) → 문자열(String)**

- `char + ""`: 문자(char)에 ""(쌍따옴표)를 더한다.

```java
char char_num = '3';
String num = char_num + "";
```



**문자열(String) → 정수형**

- `Integer.parseInt(문자열)`

```java
public class Sample {
    public static void main(String[] args) {
        String num = "123";
        int n = Integer.parseInt(num);
        System.out.println(n); // 123
    }
}
```

※ `문자열객체.charAt(인덱스)` 하여 나온 타입은 `char`임. 정수 문자열을 charAt으로 뽑아서 나온 숫자(char)는 정수형으로 치환했을 때, 자동 치환되는데 그 값이 "7" → 7로 바뀌는 게 아님을 유의

```java
String num = "123456789";
int n = num.charAt(6); // 6번 인덱스에 있는 값은 "7"이나, char형을 int형으로 묵시적 변환 발생
int n2 = Integer.parseInt(num.charAt(6)+""); // char형 자료에 ""을 붙여 문자열로 치환함
System.out.println(n); // 55
System.out.println(n2); // 7
```

 

**문자열(String) → 실수형**

- `Double.parseDouble(문자열)`
- `Float.paresFloat(문자열)`

```java
public class Sample {
    public static void main(String[] args) {
        String num = "123.456";
        double d = Double.parseDouble(num);
        float f = Float.parseFloat(num);
        System.out.println(d); // 123.456
        System.out.println(f); // 123.456
    }
}
```



**정수형 → 문자열(String)**

- `""+정수형`
- `String.valueOf(정수형)`
- `Integer.toString(정수형)`

```java
public class Sample {
    public static void main(String[] args) {
        int n = 123;
        String num = "" + n;
        String num1 = String.valueOf(n);
        String num2 = Integer.toString(n);
        System.out.println(num);  // 123
        System.out.println(num1);  // 123
        System.out.println(num2);  // 123       
    }
}
```



**형변환(casting, 캐스팅) **

`(타입)피연산자` 형태로 사용

- 정수를 실수로 바꿀 때는 캐스팅이 필요없지만, 실수를 정수로 바꿀 때는 반드시 정수형으로 캐스팅해줘야한다.

```java
public class Sample {
    public static void main(String[] args) {
        int n1 = 123;
        double d1 = n1;  // 정수를 실수로 바꿀때에는 캐스팅이 필요없다
        System.out.println(d1);  // 123.0

        double d2 = 123.456;
        int n2 = (int) d2; // 실수를 정수로 바꿀때에는 반드시 정수형으로 캐스팅해 주어야 한다
        System.out.println(n2);  // 123
    }
}
```

-----

### <제어문>

##### for each문

- 기본적으로 for문과 같지만, 구조가 조금 다르다.

- 일반적인 for문은 변수 i가 지정한 크기까지 i++한다면, for each문은 iterable 자료형에 대해 순차적으로 시행하는 구문이다. 따라서 for each 구문은 배열 및 ArrayList 자료형 등만 가능하다.

  ```python
  # 파이썬으로 보여주는 java의 for 구문, for each 구문
  # for 구문
  for i in range(1,n+1):
      print(i)
      
  # for each 구문
  for num in number_list:
      print(num)
  ```



- for each 문 기본 구조

```java
for (type var: iterate) {
    body-of-loop
}

// 평범한 for문
String[] numbers = {"one", "two", "three"};
for(int i=0; i<numbers.length; i++) {
    System.out.println(numbers[i]);
}

// for each문
String[] numbers = {"one", "two", "three"};
for(String number: numbers) {
    System.out.println(number);
}
```

-----

### <논리 연산자>

논리 연산자에는 &, &&, |, || 등이 있다.

- `&` , `&&` : `and`의 개념
- `|` , `||` : `or`의 개념

이 중, `&&`과 `||`은 '단축 연산자'라 해서, `&`과 `|`보다 빠르다.

- `A && B` : A와 B 둘 다 true일 경우 true를 반환. 단, A가 false이면 B는 체크하지 않고 false를 반환
- `A || B` : A 또는 B 둘 중 하나가 true이면 true를 반환. 단, A가 false이면 B는 체크하지 않고 false를 반환



##### 조건 삼항 연산자

- `변수 = (조건) ? 값1 : 값2` : 조건이 참이면 값1을 변수에 넣고, 아니면 값2를 변수에 대입

```java
int score = 78;
boolean check;
check = (score > 80) ? true : false; // false를 반환
```

------

### <조건 연산자>

`?`는 조건 연산자를 정의할 떄 사용 가능하다.
조건 연산자란 파이썬의 조건 표현식(Conditional Expression)과 동일하다.

`조건 ? (조건이 맞을 경우) 결과 : (조건이 아닐 경우) 결과` 행태로 쓴다.

```java
return x >= 0 ? x : -x; // x가 0이상이면 x를 반환하고, 아니면 -x를 반환한다.
```

------

<span style="color:RED">※ 이 부분부터는 일단 쓰고나서 이해하면서 천천히 정리할것</span>

### <클래스>

![20220301_164344](https://user-images.githubusercontent.com/93081720/156185661-69e2e2ee-948b-44c3-99d6-7e3b6aa9b2cb.jpg)

- **관련 있는 변수와 함수를 묶어서 만든 사용자 정의(user-defined) 자료형**

```
[접근 제한자] [활용 제한자] class 클래스명
[public / default] [final / abstract] class 클래스명
```

- 객체지향적 관점: 모든 객체들의 생산처, 객체를 생성하는 틀
- 프로그래밍적 관점: 서로 관련된 변수를 정의하고, 이들에 대한 작업을 수행하는 함수들을 정의하는 것
- 프로그래밍이 쓰이는 목적을 생각하여 어떤 객체를 만들어야 하는지 결정한다
  - 클래스를 설계한다 → 클래스로부터 객체를 생성한다 → 생성된 객체는 클래스에 정의한 속성과 동작을 가지고 동작한다.
- 각 객체들이 어떤 특징(속성과 동작)을 가지고 있을지 결정한다
  - 속성(Attribute) : 멤버 변수(클래스에서 정의된 변수) ex) `int count;`
  - 동작(Behavior) : 메서드(멤버 함수) ex) `public void channelUp(){...}`
- 객체들 사이에서 메시지를 주고 받도록 만들어준다
- 변수를 선언하는 것(만드는 것)은 위치를 저장할 수 있는 '공간'을 만드는 개념임
  - ex) `Person p1 = new Person(); ` → p1이라는 변수가 Person 클래스가 갖고 있는 내용을 '참조'함



### <변수>

- 변수의 종류(변수 선언위치로 구분)

  - 클래스 변수, 인스턴스 변수, 지역변수

  | 변수 종류     | 영역                               | 생성 시기                                                    |
  | ------------- | ---------------------------------- | ------------------------------------------------------------ |
  | 클래스 변수   | 클래스 영역                        | 클래스가 메모리에 올라갈 때                                  |
  | 인스턴스 변수 | 클래스 영역                        | 인스턴스가 생성되었을 때                                     |
  | 지역변수      | 메서드, 생성자, 초기화 블럭{} 내부 | 변수 선언문이 실행되었을 때, <br />메서드 내에서만 사용, 메서드 종료시 사용 불가 |

  - 클래스 변수와 인스턴스 변수를 통합해 멤버 변수라고 부른다.
    - 클래스 변수는 변수 타입 앞에 `static`이 붙는다. 그래서 static 변수가 공유되는 것임
    - 클래스 변수는 객체명.변수명으로도 접근이 가능하지만, 이렇게 사용하면 클래스 변수를 인스턴스 변수로 오해할 수도 있으므로 `클래스명.변수명`으로 접근한다.
    - 카드 클래스를 만드는 경우, 카드 무늬 및 숫자는 카드마다 다양하므로 인스턴스 변수로 선언하고,
      카드 크기(가로, 세로)는 모든 카드가 고정이어야 하므로 클래스 변수로 선언한다.
  - for문, while문 블랙 내 선언된 변수도 지역변수이며, `{}`블럭 내부에서만 사용이 가능하다.
    따라서 지역변수의 최기화는 필수적이다.

#### 참조변수 `this.`

- 인스턴스(객체) 자기 자신을 나타내는 키워드(python의 self와 흡사)
  - this에는 인스턴스 주소가 저장되어 있으며, 모든 인스턴스 메서드에서 지역변수로 숨겨진채 존재함
  - 따라서, static 변수에는 this를 사용할 수 없음
- 지역변수의 이름과 멤버 변수의 이름이 같을 때, 멤버 변수를 지칭해 줄 수 있는 키워드
  - `this.멤버 변수` 형태로 호출



#### 참조변수 `super.`

- 조상 클래스의 멤버변수를 참조하기 위한 키워드
  - 자식 클래스 멤버와 변수명이 동일할 때 이를 구분하기 위해서 사용함
- `super.조상멤버 변수` 형태로 호출




### <메서드>

- 객체가 할 수 있는 행동을 정의한 것(함수)

- 메서드에는 `클래스 메서드(=static 메서드)`와 `인스턴스 메서드`가 존재함 

  - 인스턴스 메서드는 `인스턴스.메서드();`로 호출

  - 클래스 메서드(static메서드)는 `클래스명.메서드();`로 호출

  - 일반적으로 보통 인스턴스 변수로 작업을 해야하는 경우 인스턴스 메서드로 선언하고, 그렇지 않을 경우 클래스 메서드(static 메서드)로 선언한다.

    ※ 인스턴스 멤버(인스턴스 변수, 인스턴스 메서드)가 존재하는 상태에서는 클래스 멤버는 항상 존재함.
    그 이유는 클래스 멤버는 클래스 선언과 동시에 정의되는 반면, 인스턴스 멤버는 클래스를 통해 인스턴스를 생성하고 나서야 존재하기 때문임


```
[접근 제한자] [활용 제한자] 반환값 메서드이름(매개변수들){...}
[public / protected / default() / private] [static / final / abstract / synchronized] [void] 메서드 이름(매개변수들){...}
```

- 반환(return) 유형은 메서드를 선언할 때 정의하며, 반환 유형이 정해져 있으면 반드시 해당 유형의 값을 반환해야함
  - 반환 유형이 없는 `void`의 경우 사실 컴파일러에서 자동적으로 return;을 해주고 있는 것임
    원래 모든 메서드는 return이 있어야한다.

- 클래스를 매개변수로 받을 때도 기존의 정수, 문자 자료형을 받을 때와 마찬가지로 `클래스 객체명`으로 씀

```java
static void printPerson(Person p) {
    System.out.println(p.name+"입니다.");
}
```



#### <메서드 오버로딩(Overloading)>

- 한 클래스 내에서 동일한 이름의, 매개 변수의 수, 매개 변수의 타입이 다른 메서드를 여러 개 정의하는 것(new)
  - **이름은 동일해야하고, 매개 변수의 수와 타입은 반드시 달라야함**. 매개 변수의 수와 타입에 따라 그에 맞는 메서드를 호출하기 때문



#### <메서드 오버라이딩(Overriding)>

Overriding의 사전적 의미는 Overwrite(덮어쓰다)임. 상속받은 메서드의 내용만 덮어쓰는 것(modify)

- 오버라이딩의 조건
  - 메서드의 선언부는 부모 메서드와 반드시 일치해야함
    - 단, 접근 제어자의 경우 변경은 가능하나 부모 메서드보다 접은 범위로 변경 불가능
  - 부모 메서드보다 많은 예외 선언 불가능




### <생성자(constructor)>

- 객체가 생성될 때 처음 딱 한번만 실행되는 함수, 객체 생성의 초기화를 담당
  - 연산자 `new`가 인스턴스를 생성하는 것이지, 생성자(constructor)가 인스턴스를 생성하는 것이 아님
    생성자는 '인스턴스 초기화'를 담당하는 특별한 '메서드'임

- 자바의 생성자는 클래스 명과 같음
- void는 아니지만, 문법적으로 반환 유형이 없음
- 생성자를 하나도 만들지 않으면, 몸통이 비어있는 기본 생성자를 컴파일러가 자동으로 생성해줌. 이를 `디폴트 생성자(default constructor)`라고 한다.
  - ex) `Dog(){}`
- 자바는 함수 이름만 갖고 구분하지 않고 매개변수까지 같이 포함하여 구분하므로, 같은 이름의 함수가 존재해도 됨. 이는 생성자 역시 하나의 함수이기 때문에 마찬가지임 → `메서드 오버로딩` 

```java
class Dog {
  String name;
  int age;
  // 생성자
  Dog(String n, int a) {
    name = n;
    age = a;
  }
}

public class Sample {
  public static void main(String[] args) {
    Dog dog = new Dog("Happy", 3);
    System.out.printf("이름: %s, 나이: %d", dog.name, dog.age); // 이름: Happy, 나이: 3
  }
}
```



#### 생성자 호출 `this();`

- 동일한 명의 다른 생성자를 호출할  때 사용함. 생성자 이름 대신 `this(인자값)` 형태로 호출함.
  단, 반드시 첫 줄에 써야만 호출이 가능함 (=현재 생성자에서 아직 아무것도 하지 않았어야 함)
  - `this(인자값)` : 타 생성자 호출
    - 생성자 내에서 `클래스 명()` 형태로 호출 시 에러 발생
  - `this()` 생성자 호출 시 제한사항
    - 생성자 내에서만 호출이 가능함
    - 생성자 내에서 첫번째 구문에 위치해야함
  - static 영역에서는 사용이 불가능함

```java
class Exdate {
  int year;
  int month;
  int day;

  Exdate() { // 생성자 1
    this(2021,4,1); // 다른 생성자를 호출함. 넣고자 하는 기본값을 넣어야 함. 변수형태로는 X
  }

  Exdate(int year, int month, int day) { // 생성자 2(메서드 오버로딩)
    this.year = year;
    this.month = month;
    this.day = day;
  }
  void showDate() {
    System.out.println(this.year+"년 "+this.month+"월 "+this.day+"일");
  }
}

public class Sample {
  public static void main(String[] args) {
    Exdate ex01 = new Exdate();
    ex01.showDate(); // 2021년 4월 1일

    Exdate ex02 = new Exdate(2021,8,9);
    ex02.showDate(); // 2021년 8월 9일
  }
}
```



####  부모 생성자 호출 `super()`

부모의 생성자를 호출함. 매개변수가 있다면 당연히 매개변수를 입력으로 받으므로 써줘야함



※ 매개변수(parameter)와 인자(arguments)

매개변수는 메소드에 입력으로 전달된 값을 받는 변수를 의미하고, 인자는 메소드를 호출할 때 전달하는 입력값을 의미함



```java
class Animal {
  String name;
  public void setName(String name) { //void : return 값이 없음, static이 아니기 때문에 공유 x
    this.name = name;
  }
}
public class Sample {
  public static void main(String[] args) {
    Animal cat = new Animal();
    Animal dog = new Animal();
    cat.setName("Happy"); // cat.name = "Happy";
    dog.name = "Funny"; // dog.setName("Funny");
    System.out.println(cat.name);
    System.out.println(dog.name);
  }
}
```



#### <접근 제어자>

변수나 메소드의 사용 권한은 접근 제어자를 사용하여 설정할수 있다.

접근 제어자의 종류에는 **private, default, protected, public**이 있다. 기본은 default이나 생략되어 있음

`public > protected > default > private    ` 순으로 보다 많은 접근을 허용한다.



##### 1. private

- `private`이 붙은 변수, 메서드는 해당 클래스에서만 접근이 가능하다.
  - 생성자 앞에 `private`을 붙이면 다른 클래스에서 `new`를 통해 해당 클래스를 생성할 수 없다.


```java
public class Sample {
    private String secret;
    private String getSecret() {
        return this.secret;
    }
} // secret 변수와 getSecret 메서드는 오직 Sample 클래스에서만 접근이 가능하고 다른 클래스에서는 접근이 불가능하다.
```



##### 2. default

- 접근 제어자를 별도로 설정하지 않는다면 접근 제어자가 없는 변수, 메서드는 default 접근 제어자가 되어 해당 패키지 내에서만 접근 가능하다.



##### 3. protected

- `protected` 접근 제어자가 붙은 변수, 메서드는 동일 패키지의 클래스 또는 해당 클래스를 상속 받은 다른 패키지의 클래스에서만 접근 가능하다.



##### 4. public

- `public` 접근 제어자가 붙은 변수, 메서드는 어떤 클래스에서라도 접근이 가능하다.



#### <활용 제한자 >

##### 1. static

정적인, 클래스의, 공통의

- 항상 값이 변하지 않는 경우라면 `static` 사용 시 메모리 상의 이점을 얻을 수 있다. 변수에 `static`을 붙이면 자바는 메모리 할당을 딱 한번만 하기 때문이다.
- 값을 '**공유**'하기 위해서 `static`을 사용하기도 한다.  같은 메모리의 주소를 가르키기 때문에 변수의 값을 공유하게 되는 것이다.

```java
// 1. 메모리 할당 예시
class HouseLee {
    static String lastname = "이";
}

public class Sample {
    public static void main(String[] args) {
        HouseLee lee1 = new HouseLee(); // HouseLee class의 lastname변수에 static이 없으면
        HouseLee lee2 = new HouseLee(); // 객체를 생성할 때마다 같은 값임에도 메모리를 할당함
    }
}
```



```java
// 2. 변수값 공유 목적 예시
// 변수에 static을 붙이지 않았을 경우
class Counter  {
    int count = 0;
    Counter() {
        this.count++;
        System.out.println(this.count);
    }
}
public class Sample {
    public static void main(String[] args) {
        Counter c1 = new Counter(); // 1
        Counter c2 = new Counter(); // 1
    }
}

// 변수에 static을 붙였을 경우
class Counter  {
    static int count = 0;
    Counter() {
        count++;  // count는 더이상 객체변수가 아니므로 this를 제거하는 것이 좋다.
        System.out.println(count);  // this 제거
    }
}
public class Sample {
    public static void main(String[] args) {
        Counter c1 = new Counter(); // 1
        Counter c2 = new Counter(); // 2 -> 변수를 공유했기 때문에 ++;하여 2를 출력한다.
    }
}
```



**static 메서드**

- 메서드 앞에 `static`을 붙이면 객체 생성없이 클래스를 통해 메서드를 직접 호출할 수 있다.
- `static` 메서드 안에서는 인스턴스 변수에 접근이 불가능하다.
- 왜 static 메서드를 사용하는가?
  - 인스턴스 메서드는 인스턴스를 생성하고 호출해야 하지만 static메서드는 바로 사용 가능하기 때문에 편리하고 빠름
  - 그래서 보통 `static` 메서드는 유틸리티성 메서드를 작성할 때 많이 사용함


```java
class Counter  {
    static int count = 0;
    Counter() {
        count++;
        System.out.println(count);
    }
    // static 메서드
    public static int getCount() {
        return count;
    }
}
public class Sample {
    public static void main(String[] args) {
        Counter c1 = new Counter(); // 1
        Counter c2 = new Counter(); // 2
        System.out.println(Counter.getCount());  // 2 //클래스를 이용해서 static메서드 호출
    }
}
```



##### 2. final

마지막의, 변경될 수 없는

- `final`+변수 => 상수(constant)
- `final`+클래스 => 변경/확장이 불가능한 클래스
- `final`+메서드 => 변경/재정의가 불가능한 메서드



##### 3. abstract

추상의, 미완성의

- `abstract`+클래스 => 클래스 내에 abstract 메서드가 있음을 알려줌
  - abstract 클래스는 인스턴스 생성이 불가능함. 왜냐하면 `미완성`이기 때문
- `abstract`+메서드 => 선언부만 작성하고 구현부는 작성하지 않은 추상 메서드



### <상속(Inheritance)>

`class 자식 클래스명 extends 부모 클래스명`
=> *"자식 클래스가 부모 클래스를 확장한다", "부모 클래스가 자식 클래스에 상속시켜준다", "자식 클래스가 부모로부터 상속을 받는다"*

<img src="https://user-images.githubusercontent.com/93081720/157363176-15f5e568-0c1a-477e-804f-c685fc328c24.png" alt="image" style="zoom: 67%;" />

- Child(자식 클래스)는 Parent(부모 클래스)의 모든 멤버를 상속 받는다(생성자, 초기화 블럭 예외)
  - 자식 클래스의 멤버 수 >= 부모 클래스의 멤버 수
- 자식 클래스에서 새롭게 선언한 변수 및 메서드는 부모에 영향을 줄 수 없다.

※ 자바에서는 **단일 상속(Single Inheritance)**만 가능하다.
Why? 여러 클래스가 있을 때, 해당 클래스에서 동일한 메서드가 있다면, 어느 클래스의 어느 메서드에서 상속을 받을 것인가? 만약 static메서드라면 클래스명을 통해 구분이 가능하겠지만, 그렇지 않은 경우 구분이 매우 힘들다. 따라서 자바는 다중 상속의 장점을 포기하고, 클래스 간의 명확성 및 코드의 신뢰성을 위해 단일 상속을 택했음

####  ※ 포함(Composite) 관계?

한 클래스를 정의하면서 해당 클래스의 멤버 변수로 다른 클래스 타입의 참조변수(다른 클래스의 인스턴스)를 선언하는 것을 의미

예) Circle(원)이라는 클래스를 정의하면서, Point 클래스의 인스턴스인 p를 선언해서 원의 중심부를 멤버 변수로 가져오고, 반지름 r을 선언함

```java
Class Circle {
    Point p = new Point();
    int r
}
```



#### ※ 상속 - 포함관계 결정 및 구분하기

- 추상화의 관점에서 접근하기
- `is`(상속) vs. `have`(포함)
  - 보다 적절하게 문장이 이해되는 것이 더 올바른 관계

```
1. SuperCar와 Car
    SuperCar is Car (○) → 상속 관계가 더 적절함
    SuperCar has Car (△)

2. Circle과 Point
	Circle is Point (△)
	Circle has Point (○) → 포함 관계가 더 적절함
```



### <예외처리> 

#### 예외처리 구문

- try, catch