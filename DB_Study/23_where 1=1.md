# 23_where 1=1

> where 1=1을 사용하는 이유

## 1. where 1=1

sql 쿼리를 보다보면 종종 where 1=1이라는 조건이 걸린 쿼리를 볼 수 있다.

where 1=1은 매우 당연히 항상 true인 조건이기 때문에 왜 넣는지 이해가 안 갈 수도 있다. 하지만 이는 실제 데이터를 필터링하기 위한 조건이 아니라 동적 쿼리와 편의성을 위해 작성하는 조건이다.

### 1) 동적 쿼리 작성

sql을 문자열로 경우에 따라 조건을 붙여야 하는 상황이 있을 수 있다.

```java
String sql = "SELECT * FROM users ";
if (name != null) sql += "WHERE name = '" + name + "'"; // 첫 번째 조건은 WHERE로 시작
if (age != null) {
    if (name == null) sql += "WHERE age = " + age;  // 앞선 조건이 없으면 WHERE
    else sql += "AND age = " + age; // 앞선 조건이 있으면 AND
    
}
```

위와 같이 코딩을 한다면, 첫번째 where절을 붙이기 위해서 계속해서 체크한 다음 결정해야 하는 번거로움이 생긴다.

하지만 where 1=1 조건을 아예 처음부터 붙여버리면 항상 참이기 때문에 쿼리에 영향을 주지 않는다. 여기에 이후에 붙는 모든 추가 조건을 AND로 연결하면 되는 큰 장점이 생긴다.

```java
String sql = "SELECT * FROM users WHERE 1=1 "; // 기본으로 깔아둠
if (name != null) sql += "AND name = '" + name + "'"; // 그냥 AND만 붙이면 끝
if (age != null) sql += "AND age = " + age;           // 이것도 그냥 AND
```

where절을 붙이기 위한 조건부 체크 로직 자체가 없어지고 상대적으로 코드도 간략해진다.

### 2) 쿼리 디버깅, 유지보수, 주석 처리 용이

where 1=1을 사용하면 동적 쿼리 작성에 용이하다는 점 외에 쿼리 디버깅이나 유지보수, 주석 처리가 용이해진다는 장점이 생긴다.

```sql
SELECT * 
  FROM products
 WHERE 1=1
   AND category = 'Electronics'
   AND price > 100000
   AND stock > 0;
```

첫번째 where 조건이 항상 true이기 때문에 그 다음 AND로 연결된 조건에 대해서 한 줄씩 주석/활성화 처리하기 용이하다는 장점이 생긴다.

이를 통해 조건에 대한 쿼리 디버깅, 유지보수가 용이하다는 장점이 생긴다.

### 3) 성능상 문제점?

oracle, mysql, postgresql 등 현대의 dbms의 옵티마이저에서는 1=1과 같은 항상 참인 조건은 쿼리 최적화 단계에서 자동으로 무시되기 때문에 실제 실행 시 성능에 미치는 영향은 아예 없다고 봐도 된다.

