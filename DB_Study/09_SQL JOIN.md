# 09_SQL JOIN

> JOIN(조인)이란 두 개 이상의 테이블을 묶어서 하나의 결과 집합으로 만들어 내는 것을 의미함

즉, 2개 이상의 테이블에서 데이터를 가져올 때 사용함

일반적으로 PK 및 FK에 대해 사용하지만 논리적 관계만으로도 JOIN이 가능함

- JOIN의 필요성
  - 데이터를 가져와야 하는데, 여러 테이블에 걸쳐서 원하는 데이터가 분산되어 있을 경우, 이들을 합쳐서 원하는 데이터를 가져오기 위함
- JOIN의 단점
  - 경우에 따라 쿼리가 복잡해질 수 있어 속도가 저하될 수도 있다.
  - 잘못 사용할 경우 카타시안 곱이 발생하여 속도가 매우 심하게 떨어진다.

※ 카타시안 곱 : 두 테이블에 존재하는 데이터 셋의 경우의 수의 모든 조합을 출력하게 되는 것을 말함

<br>

## 1. INNER JOIN

>  JOIN 조건에 해당하는 칼럼 값이 양쪽 테이블 모두에 존재하는 경우에만 조회 가능

MY SQL에서 `INNER JOIN`으로 써도 되지만, `JOIN`만 쓰더라도 INNER JOIN으로 인식한다.

또한, INNER JOIN은 WHERE 절을 통해서도 표현 가능하다.

```sql
SELECT <칼럼1, 칼럼2, ...>
FROM <기준 테이블>
	INNER JOIN <참조할 테이블>
	ON <조인 조건>
[WHERE 검색조건];
```

### INNER JOIN의 집합 표현

A와 B, 두 집합 중 두 집합이 공통적으로 일치하는 교집합의 개념

![image](https://user-images.githubusercontent.com/93081720/209431751-3859c142-30cb-4ce2-a1a2-5438714d7fac.png)

예시1)

```sql
-- 보호 시작일보다 입양일이 더 빠른 동물의 아이디와 이름을 조회하는 SQL문을 작성
-- 보호 시작일은 ANIMAL_INS, 입양일은 ANIMAL_OUTS에 있으므로 JOIN이 필요한 상황
-- 두 테이블 모두에 ANIMAL_ID라는 공통 필드가 존재하므로 INNER JOIN 사용 가능
-- ANIMAL_ID가 같은 데이터에 대해 보호 시작일과 입양일의 비교해서 조건에 맞는 원하는 데이터를 출력
SELECT ai.ANIMAL_ID, ai.NAME
FROM ANIMAL_INS ai
	INNER JOIN ANIMAL_OUTS ao
	ON ai.ANIMAL_ID = ao.ANIMAL_ID
WHERE ao.DATETIME < ai.DATETIME
ORDER BY ai.DATETIME ASC;
```

<br>

예시2)

```sql
SELECT GG.GG_ID, GG.NAME, S.TITLE
FROM GIRL_GROUP AS GG
JOIN SONG AS S
ON S.SONG_ID = GG.HIT_SONG_ID;
```

결과

<img src="https://user-images.githubusercontent.com/93081720/209431100-679d3670-ac8a-4a86-9b3f-1446157123d8.png" referrerpolicy="no-referrer" alt="image" height=300px>

<br>

## 2. OUTER JOIN

> JOIN 조건에 해당하는 칼럼 값이 한쪽 테이블에만 존재하더라도 조회 가능

두 테이블에서 **하나의 테이블에 조인 조건 데이터가 존재하지 않더라도**(= 조인 조건을 만족하지 않더라도) 두 테이블에서 데이터를 조회하기 위해 사용

즉, OUTER JOIN에 존재하는 JOIN 조건인 `ON`은 **IF POSSIBLE의 개념으로 'JOIN 조건에 만족하는 데이터가 있으면 JOIN해서 가져오고, 그렇지 않으면 그냥 있는 그대로 가져와라'는 의미**임

기준 테이블에 따라 `LEFT OUTER JOIN`, `RIGHT OUTER JOIN`, `FULL OUTER JOIN`으로 구분 가능

- FULL OUTER JOIN은 MY SQL에서 지원하지 않는다

이 때, **첫 번째 테이블을 LEFT, 두 번째 테이블을 RIGHT**라고 한다.

```sql
SELECT <칼럼1, 칼럼2, ...>
FROM <첫 번째 테이블(LEFT)>
    <LEFT | RIGHT | FULL> [OUTER] JOIN <두 번째 테이블(RIGHT)>
    ON <조인 조건>
[WHERE 검색조건];
```

### OUTER JOIN의 필요성

OUTER JOIN은 왜 필요한가?

- 예를 들어, 직원 테이블과 부서 테이블이 있고, 전체 직원의 명단을 조회하고자 할 때
- 부서 번호가 조인 조건으로 하여 INNER JOIN을 통해 조회하면, 부서 번호가 NULL인 직원은 검색되지 않는다.
- 따라서 부서 번호가 NULL인 경우, 즉 조건을 만족하지 않아도 두 데이블에서 데이터를 가져오기 위해서 OUTER 조인을 사용하는 것이다.
- 다만, OUTER JOIN은 INNER JOIN보다는 사용 빈도가 확실히 떨어진다.

내가 어디를 기준으로 두느냐에 따라 데이터가 나올 수도 안 나올 수도 있음. 

따라서 `LEFT JOIN`을 해야하는지, `RIGHT JOIN`을 해야 하는지 아는 것도 중요

<br>

### LEFT JOIN (LEFT OUTER JOIN)

첫 번째 테이블을 기준으로 JOIN하여 JOIN조건에 부합하지 않는 데이터까지 조회

 `LEFT OUTER JOIN`으로 써도 되지만, `LEFT JOIN`만 쓰더라도 LEFT OUTER JOIN으로 인식한다.

#### LEFT JOIN의 집합 표현

A와 B, 두 집합 중 두 집합이 공통적으로 일치하는 부분과 나머지 A의 집합 => A의 모든 요소 집합

![image](https://user-images.githubusercontent.com/93081720/209431814-05165349-5231-42b7-ad35-f02b64705d72.png)

예시1)

```sql
-- 유저 테이블의 데이터를 모두 조회하면서(LEFT JOIN, 첫 번째 테이블 데이터는 모두 조회),
-- 유저 테이블의 유저 ID와 구매 테이블의 유저 ID가 같은 데이터가 있다면, JOIN하여 데이터를 조회해라
SELECT U.USER_ID, U.ADDRESS, U.PHONENUMBER, B.PRODUCT_ID
FROM USER_TBL U
    LEFT JOIN BUY_TBL B
    ON U.USER_ID = B.USER_ID
WHERE B.PRODUCT_ID IS NULL
ORDER BY U.USER_ID ASC;
```

예시2)

```sql
-- 걸 그룹 테이블을 기준으로 하여 걸 그룹의 히트송 ID와 노래 테이블의 노래 ID가 같다면 JOIN해서
-- 걸 그룹 ID, 걸 그룹 이름, 노래 제목 데이터를 출력하라
SELECT GG.GG_ID, GG.NAME, S.TITLE
FROM GIRL_GROUP AS GG
LEFT OUTER JOIN SONG AS S
ON S.SONG_ID = GG.HIT_SONG_ID;
```

결과

- 걸 그룹 테이블을 기준으로 출력
- INNER JOIN과 달리, 히트송 ID가 없는 데이터에 대해서도 title이 NULL로서 출력됨을 확인 가능

<img src="https://user-images.githubusercontent.com/93081720/209431025-7373ebfe-5ecf-44c4-a74a-72f2b0e0ab20.png" referrerpolicy="no-referrer" alt="image" height=300px>

<br>

### RIGHT JOIN (RIGHT OUTER JOIN)

두 번째 테이블을 기준으로 JOIN하여 JOIN조건에 부합하지 않는 데이터까지 조회

#### REFT JOIN의 집합 표현

A와 B, 두 집합 중 두 집합이 공통적으로 일치하는 부분과 나머지 B의 집합 => B의 모든 요소 집합

![image](https://user-images.githubusercontent.com/93081720/209431858-c72ab93b-e819-4cdd-b4fd-c48597674fd9.png)

예시)

```sql
-- 노래 테이블을 기준으로 하여 걸 그룹의 히트송 ID와 노래 테이블의 노래 ID가 같다면 JOIN해서
-- 노래 ID, 노래 제목, 걸 그룹 이름 데이터를 출력하라
SELECT GG.GG_ID, GG.NAME, S.TITLE
FROM GIRL_GROUP AS GG
RIGHT OUTER JOIN SONG AS S
ON S.SONG_ID = GG.HIT_SONG_ID;
```

결과

- 노래 테이블을 기준으로 출력
- INNER JOIN과 달리, 걸 그룹 이름이 NULL인 노래 데이터를 가져온 것을 확인 가능

<img src="https://user-images.githubusercontent.com/93081720/209431423-15d4f617-90b5-45ad-8f0d-8c27f7fc0fe3.png" referrerpolicy="no-referrer" alt="image" height=300px>

<br>

## 3. SELF JOIN

같은 테이블에서 두 번 참조해야 하는 경우에 사용하는 JOIN 방법

#### WHERE 절을 사용한 SELF JOIN

```sql
SELECT <칼럼1, 칼럼2, ...>
FROM <테이블>
WHERE 검색 조건 = (SELECT <칼럼1, 칼럼2, ...>
               FROM <테이블>
               WHERE 검색 조건);
```

#### INNER JOIN을 사용한 SELF JOIN

```sql
SELECT <칼럼1, 칼럼2, ...>
FROM <테이블 T1>
	INNER JOIN <테이블 T2(동일 테이블이나 alias를 달리함)>
	ON <T1.조인 조건 = T2.조인 조건>
[WHERE 검색조건];
```

예시1) 

- 동일한 테이블을 alias만 달리하여 그대로 가져와도 되지만,
- `서브쿼리`를 통해 새로운 테이블을 가공해서 가져와서 INNER JOIN으로 SELF JOIN도 가능함

```sql
-- 즐겨찾기가 가장 많은 식당 정보 출력하기
SELECT r1.FOOD_TYPE, r1.REST_ID, r1.REST_NAME, r1.FAVORITES
FROM REST_INFO r1
INNER JOIN (SELECT FOOD_TYPE, MAX(FAVORITES) AS FAVORITES
           FROM REST_INFO
           GROUP BY FOOD_TYPE) r2
ON r1.FOOD_TYPE = r2.FOOD_TYPE AND r1.FAVORITES = r2.FAVORITES
ORDER BY r1.FOOD_TYPE DESC;
```

예시2)

```sql
-- 헤비 유저가 소유한 장소
SELECT p1.ID, p1.NAME, p1.HOST_ID
FROM PLACES p1
INNER JOIN (SELECT HOST_ID, COUNT(HOST_ID) AS COUNT_HOST
           FROM PLACES
           GROUP BY HOST_ID) p2
ON p1.HOST_ID = p2.HOST_ID
WHERE p2.COUNT_HOST >= 2
ORDER BY ID ASC;
```

<br>

## 4. CROSS JOIN

CROSS JOIN은 한쪽 테이블의 행 하나당 다른 쪽 테이블의 모든 행을 하나씩 모든 행들을 각각 JOIN하는 것

CROSS JOIN이 바로 `카타시안 곱(Catesian Product)`임

```sql
SELECT *
FROM <테이블 1>
CROSS JOIN <테이블 2>;
```

![image](https://user-images.githubusercontent.com/93081720/209431961-d884e224-e7e2-4e61-91bf-362f5c059dfd.png)

<br>
