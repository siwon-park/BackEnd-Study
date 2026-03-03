# 28_Call by Value

> 자바가 변수를 다루는 방식

<br>

## 1. 요약

Java는 Call by Value이다.

- 변수를 다루는 방식(함수에 인자를 전달하는 방식) → call by value
  - 값 자체를 복사하여 넘긴다
- 객체를 다루는 방식 → 참조 타입 (reference type; 주소값을 들고 다님)
  - 참조 타입은 call by reference와 다름
    - 참조 타입: 변수가 객체의 주소를 들고 다닌다
    - call by reference: 함수 호출 시 변수 자체를 넘긴다

<br>

## 2. call by value와 call by reference

> 함수에 인자를 전달하는 방식

### 1) call by value

> "값 자체를 복사하여 넘긴다"

자바는 함수에 인자를 전달할 때, 값 자체를 복사하여 넘기기 때문에 원본은 불변이다. 함수 안에서 변하는 것은 결국 복사본에 대한 변경이다.

```java
void add(int n) {
    n = n + 1;  // 복사본을 변경;
}

int a = 10;
add(a);
System.out.println(a); // 10 → 원본 불변
```

#### (1) Java는 call by value인데 헷갈리는 이유?

Java가 객체를 넘길 때는 "주소값을 복사하여 넘기기" 때문. 이 때 객체의 내부를 수정하여 건드릴 수 있는 것처럼 보여서 참조처럼 느껴지지만 엄밀히 말해서는 **함수 인자로 넘긴 변수에 (주소)값을 복사하여 넘긴 것**이기 때문에 call by value임.

```java
// 예시 1
Object a = new Object(); // a는 0x1234 주소를 가리킴
Object b = a;            // b도 0x1234 주소를 복사해서 가짐

// a가 가리키는 객체 내부를 변경하면
a.someField = "변경";

// b도 같은 주소를 가리키고 있으므로 반영됨
System.out.println(b.someField); // "변경"

// 예시 2
class User {
    String name;
}

void changeName(User u) {
    u.name = "변경";  // 인자로 받은 User 객체와 같은 주소를 참조; 객체 내부를 수정 → 원본에 반영됨
}

void changeRef(User u) {
    u = new User();   // 인자로 받은 User 객체에 새로운 객체 할당; 복사된 주소값 자체를 바꿈 → 원본에 미반영
    u.name = "변경2"; // 인자로 받은 User 객체와 다른 주소값을 가짐
}

User user = new User();
user.name = "원본";

changeName(user);
System.out.println(user.name); // "변경" ← 반영됨

changeRef(user);
System.out.println(user.name); // "변경" ← 반영 안됨, 여전히 "변경"
```

<br>

### 2) call by reference

> "변수 자체의 주소를 넘긴다"

반면 C++의 경우 변수 자체의 주소를 참조하여 넘기기 때문에 함수에 인자로 전달했을 때, 원본을 전달하여 값을 변경할 수 있다.

```cpp
void add(int& n) {  // & → 참조로 받겠다
    n = n + 1;      // 원본을 직접 변경
}

int a = 10;
add(a);
cout << a; // 11 → 원본이 바뀜
```

