# 07_SQL 서브쿼리

## 1. 서브쿼리(Subquery)란?

>  하나의 SQL문 안에 포함되어 있는 SQL문

서브쿼리를 포함하는 SQL을 **외부쿼리(Outerquery)** 또는 **메인쿼리(Mainquery)**라고 부르며, 

서브쿼리는 **내부쿼리(Innerquery)**라고도 부른다

<br>

## 2. 서브쿼리의 특징

쿼리문 작성 상태에 따라 실행 순서가 메인 쿼리가 먼저 실행되는 경우가 있고, 서브쿼리가 먼저 실행되는 경우가 있다.

서 쿼리는 단일 행 또는 다중 행 비교 연산자와 함께 사용 가능하다.

- 단일 행 연산자: `<`, `>`, `=`, `<=`, `>=`, `!=`, `<>`
- 다중 행 연산자: `IN`, `ANY`, `ALL`
  - `IN` : 검색된 값중 하나만 일치하면 True
  - `ANY` : 검색된 값 중에서 조건에 맞는 것이 하나 이상이면 True
  - `ALL` : 모든 검색된 값과 조건이 맞을 경우 True

서브쿼리를 사용할 때는 **반드시 () 괄호로 감싸서 사용**해야 한다.

서브쿼리 안에서 ORDER BY 절은 사용할 수 없다.

<br>

## 3. 서브쿼리를 사용할 수 있는 곳

### 서브쿼리 사용 절

- SELECT 절
- FROM 절
- WHERE 절
  - INNER JOIN의 경우 WHERE을 통해서도 사용 가능하므로 JOIN 절에도 사용 가능하다.

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

- HAVING 절
- ORDER BY 절
- INSERT 구문의 VALUES 절
- UPDATE 구문의 SET 절

<br>

## 4. 서브쿼리의 종류

### 중첩 서브쿼리(Nested Subquery)

WHERE 절에 작성하는 서브쿼리

단일 행 서브쿼리, 다중 행 서브쿼리, 다중 열 서브쿼리로 나뉜다.

- 실행 순서
  - FROM -> WHERE -> WHERE절의 서브 쿼리(FROM -> WHERE -> SELECT) -> SELECT

#### 단일 행 서브쿼리(Single Row Subquery)

단일 행 연산자를 사용하여 **조건에 맞는 하나의 결과 값을 도출**

```sql
-- 학생 정보 테이블에서 몸무게가 90 미만인 학생의 ID와 학생 테이블의 학생 ID가 일치하는 학생의 ID, NAME를 학생 테이블에서 조회
SELECT ID, NAME
FROM STUDENT_TABLE
WHERE ID = (SELECT S_ID 
            FROM STUDENT_INFO
            WHERE S_WEIGHT < 90)
```

<br>

#### 다중 행 서브쿼리(Multi Row Subquery)

```sql
-- 학생 정보 테이블에서 몸무게가 90 미만인 학생 중 ID가 학생 테이블의 학생 ID보다 크거나 작은 학생의 ID , NAME를 학생 테이블에서 조회
SELECT ID, NAME
FROM STUDENT_TABLE
WHERE ID <> (SELECT S_ID 
    		FROM STUDENT_INFO
             WHERE S_WEIGHT < 90)
```

<br>

#### 다중 열 서브쿼리(Multi Column Subquery)

Oracle, MariaDB 지원 O, SQL Server 지원 X

비교 대상과 칼럼의 개수가 반드시 일치해야함. 주로 비교할 때는 IN을 사용함

```sql
-- 학생 테이블에서 나이별 가장 많은 몸무게를 차지하는 사람 조회
SELECT ID, NAME, AGE, WEIGHT
FROM STUDENT_TABLE
WHERE (AGE, WEIGHT) IN (SELECT AGE, MAX(WEIGHT) 
        FROM STUDENT_TABLE
        GROUP BY AGE)
```

<br>

### 인라인 뷰(Inline-view)

FROM 절에 작성하는 서브쿼리

```sql
-- 학생 테이블에서 나이별 가장 많은 몸무게를 차지하는 사람 중 나이가 26살인 사람 조회
SELECT ID, NAME, AGE, WEIGHT
FROM (SELECT AGE, MAX(WEIGHT)
      FROM STUDENT_TABLE
      GROUP BY AGE)
WHERE AGE = 26;
```

<br>

### 스칼라 서브쿼리(Scalar Subquery)

SELECT 문에 작성하는 서브쿼리

1행만 반환된다. => 각 SELECT 구문에서 1행의 결과만 반환한다는 의미

```sql
-- 학생 테이블에서 모든 학생의 평균 나이를 계산하여 ID, NAME, 평균 나이로 출력
-- 학생 테이블의 평균 나이를 구해서 SELECT 구문에 1행으로 반환
SELECT ID, NAME, (SELECT CEILING(AVG(AGE)) FROM STUDENT_TABLE) as '평균 나이'
FROM STUDENT_TABLE
```

