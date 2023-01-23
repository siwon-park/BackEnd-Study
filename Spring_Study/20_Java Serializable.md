# 20_Java Serializable

> 자바 직렬화

이전까지 프로젝트를 하면서 Django에서는 직렬화에 대한 개념을 적용시킨 적이 있지만, Spring으로 프로젝트를 할 때에는 직렬화를 사용하지 않아서 따로 필요 없는 내용인줄 알았다.

또한 Django를 배울 당시 직렬화를 하는 이유가 단순히 Validation(검증)에 있는 줄 알았다.

그런데 최근 Spring을 사용해서 개발 공부를 하다보니 `implements Serializable`이라고 되어 있는 Entity, VO, DTO를 보게 되는 경우가 있어서 "왜 사용하는가?"에 대한 궁금증이 생겼다.

![image](https://user-images.githubusercontent.com/93081720/213952778-34506a86-344d-4d7e-a00f-2c3cfe401648.png)

<br>

## 1. 직렬화(Serialization)

> 데이터 구조나 객체 상태를 동일 또는 다른 컴퓨터 환경에 저장하고, 나중에 재구성할 수 있는 포멧으로 변환하는 과정

 메모리 상에 저장된 객체 데이터를 뽑아서 byte array로 만드는 것을 "직렬화 한다"라고 한다.

※ 역으로 byte array 포멧을 프로그래밍 언어의 자료로 객체화하는 것을 deserialization(역직렬화)이라 함

![image](https://user-images.githubusercontent.com/93081720/164254179-eeb6729e-01a2-4e53-94a2-7e53764a170f.png)

- 직렬화: 객체(data, object) → 바이트(byte)
- 역직렬화: 바이트(byte) → 객체(data, object)

<br>

## 2. 자바의 직렬화

### 왜 하는가?

> 자바 시스템 간의 데이터 교환을 하기 위해서

대부분의 시스템에서는 데이터 교환 시, 해당 시스템과 관련이 없더라도 데이터 교환을 원할하게 할 수 있도록 CSV, JSON 등을 사용한다.

- JSON 사용
  - Jackson 등의 라이브러리를 통해서도 JSON으로 쉽게 변환이 가능하다.

```java
Member member = new Member("박시원", "test123@naver.com", 31);

// member객체를 json으로 변환 
String json = String.format(
        "",
        member.getName(), member.getEmail(), member.getAge());
System.out.println(json);
```

- 자바 직렬화
  - `java.io.ObjectOutputStream` 객체를 사용

```java
Member member = new Member("박시원", "test123@naver.com", 31);
byte[] serializedMember;
try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
    try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
        oos.writeObject(member);
        serializedMember = baos.toByteArray(); // serializedMember -> 직렬화된 member 객체 
    }
}
// 바이트 배열로 생성된 직렬화 데이터를 base64로 변환
System.out.println(Base64.getEncoder().encodeToString(serializedMember));
```

- 자바 역직렬화

  - 직렬화와 역직렬화를 진행하는 시스템의 소스 버전이 다를 수도 있음을 유의

  - 자바 직렬화 대상 객체는 동일한 `serialVersionUID`를 가지고 있어야 함

    - ```java
      private static final long serialVersionUID = 1L;
      ```

    - serialVersionUID를 기술하지 않는다면 역직렬화 시, 내부적으로 해당 해시 값 정보가 추가되어 클래스 변경 시 혼란을 야기한다.

```java
// 직렬화 예제에서 생성된 base64 데이터 
String base64Member = "...생략";
byte[] serializedMember = Base64.getDecoder().decode(base64Member);
try (ByteArrayInputStream bais = new ByteArrayInputStream(serializedMember)) {
    try (ObjectInputStream ois = new ObjectInputStream(bais)) {
        Object objectMember = ois.readObject(); // 역직렬화된 Member 객체를 읽어온다.
        Member member = (Member) objectMember;
        System.out.println(member);
    }
}
```



그러면 여기서 의문점이 생기기 마련이다.

**"그냥 자바도 CSV, JSON을 사용하면 되지 직렬화를 하는 이유가 있을까?"**

![image](https://user-images.githubusercontent.com/93081720/213952725-ba7992c0-5ade-4ee1-8499-35ffa78d096c.png)

### 진짜 왜 하는가?

결론부터 말하자면 위에서 언급한 대로 CSV나 JSON을 사용해도 된다.

정답은 없고, **사용 목적에 따라 적절하게 사용해야한다**

#### 자바 직렬화의 장점

- 자바 시스템 개발에 최적화되어 있어 복잡한 데이터 구조의 클래스 객체도 직렬화 기본 조건에만 부합하면 큰 작업 없이 직렬화가 가능해진다. (역직렬화도 마찬가지)
- 데이터 타입이 자동으로 맞춰지기 때문에 타입에 대해 신경을 쓰지 않아도 된다.

#### 자바 직렬화의 단점

- JSON 데이터에 비해 용량을 더 많이 차지한다. 따라서 적은 데이터만 입력하는 시스템 구조라면 큰 상관이 없겠지만, 트래픽이 큰 시스템이라면 유의해야 한다.

![image](https://user-images.githubusercontent.com/93081720/213953365-87683e8e-f226-4400-8402-a4494e23b04d.png)

- 변경이 잦은 데이터에 대해 자바 직렬화를 지양하는 것이 좋다. 그 이유는 자바 직렬화의 경우 타입에 대해 엄격하기 때문에 역직렬화 역시 마찬가지로 타입에 대해 엄격하다. 그런데 데이터가 변경된다면, 외부에 나가있던 직렬화된 데이터는 사실상 역직렬화가 불가능해지므로 쓰레기(Garbage)가 되어 버린다.
  - 언제 예외가 발생할지 모르는 지뢰같은 시스템이 될 수도 있다.

### 결론

1. 외부 저장소로 저장되는 데이터는 짧은 만료시간의 데이터를 제외하고 자바 직렬화를 사용을 지양합니다.
2. 역직렬화시 반드시 예외가 생긴다는 것을 생각하고 개발합니다.
3. 자주 변경되는 비즈니스적인 데이터를 자바 직렬화을 사용하지 않습니다.
4. 긴 만료 시간을 가지는 데이터는 JSON 등 다른 포맷을 사용하여 저장합니다.
   - 외부 DB, Redis, NoSQL 등에 장기간 저장될 데이터는 JSON 포맷을 사용하는 것이 보다 적절하다. 또한 다른 언어와의 호환성을 염두해서라도 JSON 포맷이 더 적절하다.