# 17_Query DSL (2)

## Query DSL 기본 문법

### 조건절(where clause)

#### eq()

조건과 동일함

- `eq("something")`: = 'something'

#### ne()

조건과 동일하지 않음

- `ne("something")`: != 'something'

#### eq().not()

조건과 동일하지 않음. ne()와 같음

- `eq("something").not()`: != 'something'

#### like()

like절에서 앞 부분에만 %를 붙인 것과 같은 의미

- `like("%something")`: like '%something'

#### startsWith()

like절에서 뒷 부분에만 %를 붙인 것과 같은 의미

- `startsWith("something")`: like 'something%'

#### contains()

like절에서 앞뒤로 %를 붙인 것과 같은 의미

- `contains("something")`: like '%something%'

#### isNull()

null 인지 확인

- `isNull()`: is null

#### isNotNull()

null이 아닌지 확인

- `isNotNull()`: is not null

#### isEmpty()

비어 있는지 확인

- `isEmpty()`: 길이가 0

#### isNotEmpty()

비어 있지 않음을 확인

- `isNotEmpty()`: 길이가 0이 아님

#### in()

SQL의 IN()절과 같은 의미. 괄호 안에 콤마(,)로 구분한 값들 중에 하나 이상이 일치하면 조건에 맞는 것으로 판단

- `in("foo", "bar")`: in("foo", "bar")

#### notIn()

괄호 안에 있는 값들에 해당하지 않을 경우 조건과 일치하는 것으로 판단

- `notIn("foo", "bar")`: not in("foo", "bar")

#### in().not()

괄호 안에 있는 값들에 해당하지 않을 경우 조건과 일치하는 것으로 판단. notIn()과 동일

- `in("foo", "bar").not()`: not in("foo", "bar")

#### between()

A와 B 사이의 값일 경우 조건과 일치하는 것으로 판단

- `between(20, 30)`: between 20, 30

#### notBetween()

A와 B 사이에 속하지 않을 때 조건과 일치하는 것으로 판단

- `notBetween(20, 30)`: not between 20, 30

#### between().not()

A와 B 사이에 속하지 않을 때 조건과 일치하는 것으로 판단. notBetween()과 동일

- `between(20, 30).not()`: not between 20, 30

#### gt()

greater than A; A보다 값이 클 경우 조건과 일치하는 것으로 판단

- `gt(28)`: > 28

#### goe()

greater or equal A; A보다 값이 크거나 값이 동일할 경우 조건과 일치하는 것으로 판단

- `goe(28)`: >= 28

#### lt()

less than A; A보다 값이 작을 경우 조건과 일치하는 것으로 판단

- `lt(28)`: < 28

#### loe()

less or equal A; A보다 값이 작거나 값이 동일할 경우 조건과 일치하는 것으로 판단

- `loe(28)`: <= 28



### 결과 맵핑

Query DSL에서 결과를 맵핑하여 반환하는 방법

#### fetch()

리스트(List) 반환.

- 만약 결과가 없는 경우에는 빈 리스트를 반환함

#### fetchOne()

한 건의 결과를 반환

- 결과가 없는 경우: `null`을 반환
- 결과가 여러 개인 경우: `NonUniqueResultException` 발생

#### fetchFirst()

처음 한 건의 결과를 반환

- `limit(1).fetch()`의 결과와 동일

#### fetchResults(), fetchCount()

Query DSL 개발진 측에서 해당 함수들과  groupby, having을 함께 사용하는 복잡한 쿼리를 작성할 때 생기는 문제로 인해 `deprecated`하였음

따라서 fetch()를 통해 가져온 리스트의 size()를 반환하거나 `fetch().size()`를 사용하는 것을 권장하고 있다.

