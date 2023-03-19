# 01_변수(Variable)

자바에서의 변수

## 1. 변수

자바에서 변수명은 카멜케이스(camelCase)로 명명한다.

### 변수(variable)

> 값을 저장할 수 있는 공간

`데이터 타입`과 `변수명`으로 선언한다.

<br>

### 상수(Constant)

> 변수와 같이 값을 저장할 수 있는 공간이지만, 값 변경이 불가능

보통 키워드 `final`을 붙여서 사용하며 `대문자`로 변수를 명명한다.

<br>

### 리터럴(Literal)

> 값 그 자체

```java
int year = 2023; // year은 변수, 2023은 리터럴
final int MAX_VALUE = 999; // MAX_VALUE는 상수, 999는 리터럴
```

<br>

## 2. 기본형과 참조형

### 기본형(Primitive Type)

> stack영역에 실제 값을 저장하는 데이터 타입

boolean, byte, char, short, int, float, long, double 총 8가지 존재

|  구분  | 1 byte (8 bit) | 2 byte (16 bit) | 4 byte (32 bit) | 8 byte (64 bit) |
| :----: | :------------: | :-------------: | :-------------: | :-------------: |
| 논리형 |    boolean     |                 |                 |                 |
| 문자형 |                |      char       |                 |                 |
| 정수형 |      byte      |      short      |       int       |      long       |
| 실수형 |                |                 |      float      |     double      |

int, long, double, float, boolean, char 등은 원시(primitive) 자료형이라고 부른다. 이러한 자료형들은 <span style="color:Red">`new`</span> 키워드로 생성할 수 없다. (=  <span style="color:Red">`객체로 만들 수 없고, 값만 할당 가능`</span>)

<br>

※ 데이터 크기

- 8 bit == 1 byte
- 1000 byte = 1 kb
- 1024 kb = 1 mb
- 1024 mb = 1 gb

<br>

### 참조형(Reference Type)

> stack영역에 주소 값을 저장하고, heap 영역에 실제 값을 저장하는 데이터 타입

8개의 기본형을 제외한 모든 타입을 참조형이라고 한다. 즉, 새롭게 선언한 클래스도 참조형이 될 수 있다.

 <span style="color:Red">`null`</span>값을 허용한다는 특징이 있다.

Boolean, Character, Integer, Long, Float, String, Double, ...

#### String

`String`은 리터럴 표기가 가능하지만, 기본 자료형은 아니다. 리터럴 표기가 가능하도록 자바에서 특별 대우 해주는 예외 케이스이다.

```java
String a = "happy java" // 리터럴 표기(사용 가능)
    
String a = new String("happy java") // String 객체 생성
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

<br>

### 형 변환

#### 묵시적 형 변환(small -> large)

Promotion(묵시적 형 변환): `작은 데이터 → 큰 데이터(자동 캐스팅, 데이터 손실 위험이 없을 경우)`

- `byte` → `short` → `int` → `long` → `float` → `double`

- `char` → `int` → `long` → `float` → `double`

#### 명시적 형 변환(large -> small)

Demotion(명시적 형 변환): `큰 데이터 → 작은 데이터(데이터 손실 때문에 명시적 캐스팅 필요)`

##### 형변환(casting, 캐스팅) 

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

#### 다양한 형 변환

##### 문자(char) → 숫자

- `char - '0'`: 문자(char)에서 '0'을 뺀다

```java
char num = '3';
int num1 = num - '0';
```

##### 숫자 → 문자(char)

- `int + '0'`: 숫자에 '0'을 더한다

```java
int num = 3;
char char_num = (char) (num + '0');
char char_num1 = (char) (num);
```

##### 문자열(String) → 문자(char)

- `"문자열".charAt(0)`

```java
char num = "3".charAt(0);
```

##### 문자(char) → 문자열(String)

- `char + ""`: 문자(char)에 ""(쌍따옴표)를 더한다.

```java
char char_num = '3';
String num = char_num + "";
```

##### 문자열(String) → 정수형

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

##### 문자열(String) → 실수형

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

##### 정수형 → 문자열(String)

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



