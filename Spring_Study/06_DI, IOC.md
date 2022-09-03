![image](https://user-images.githubusercontent.com/93081720/172037595-5d53b57e-9d25-48b5-8433-485d78b311c8.png)

# 06_DI와 IoC

## 01_DI(Dependency Injection) - 의존성 주입

의존성 주입 => 외부에서 객체를 생성하여 주입함

외부에서 객체를 생성하여 주입함으로써 모듈 간 결합 관계를 낮출 수 있음 => 코드 수정이 용이해짐

한 클래스를 수정한다고 했을 때, 다른 클래스까지 수정해야하는 상황이 발생하지 않으며, 외부에서 객체를 주입받는 부분만 수정하면 됨

### 방법

#### 생성자(권장)

```java
public class Chef {
    private Knife knife;
    
    public Chef(Knife knife) { // 외부에서 생성된 knife 객체를 Chef 클래스의 생성자의 인자로 받음
        this.knife = knife;
    }
}
```

#### setter

```java
public class Chef {
    // setter를 통해서 외부에서 생성된 knife 객체를 전달받음(Spring에서 @Autowired 애너테이션이 필요)
    public void setKnife(Knife knife) {
        this.knife = knife;
    }
}
```

#### @Autowired

```java
public class Chef {
    
    @Autowired // 속성에 @Autowired 애너테이션을 적용하여 객체를 주입받음
    private Knife knife;
}
```

<br>

## 02_IoC(Inversion of Control) - 제어의 역전

제어의 역전 => 제어의 흐름을 바꾼다

메서드나 객체의 호출 작업을 개발자가 결정하는 것이 아니라, 외부(프레임워크)에서 결정하는 것을 의미 => 개발자가 주도해서 개발하는 것이 아니라, 프레임워크의 구조와 틀에 맞게 필요한 부분을 개발해서 적용한다.

### Why?

기존의 new를 통한 방식의 객체 생성은 객체 간의 의존성을 갖게 만듦 => 클래스 내부에서 객체를 생성하여 사용하게 되면 해당 클래스는 생성한 객체에 의존성을 갖게 된다.

해당 클래스 수정이 어려워지는 문제가 발생한다. 클래스 내부에 생성된 객체에 변경 사항이 발생했을 경우 해당 클래스뿐만 아니라 연결된 다른 클래스까지 전부 변경해줘야한다 => 소프트웨어 모듈화에 큰 방해가 됨

### So?

이를 해결하기 위해 의존성 주입이라는 개념이 생겨났음
