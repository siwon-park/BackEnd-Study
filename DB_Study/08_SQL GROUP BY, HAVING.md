# 08_SQL GROUP BY, HAVING

![image](https://user-images.githubusercontent.com/93081720/165551388-253de24c-477f-49ea-a41c-4229fbbf5b32.png)

## 1. GROUP BY

> 그룹화를 하기 위해 사용

특정 칼럼들을 그룹화하여 조회할 때 사용한다.

아래 예시에서, 테이블에 USER_ID, PRODUCT_ID가 중복으로 여러 개 있다고 할 때, 재구매가 일어난 상품과 회원 리스트를 구해야 하는 경우 `GROUP BY`를 사용하여 그룹화 시켜준다.

- 단, 단순히 그룹의 구분 없이 중복만 제거해야 하는 경우에는 `DISTICT`를 사용하는 것이 이득

```sql
-- 재구매가 일어난 상품과 회원 리스트 구하기
SELECT USER_ID, PRODUCT_ID
FROM ONLINE_SALE
GROUP BY USER_ID, PRODUCT_ID # USER_ID, PRODUCT_ID 쌍을 기준으로 그룹화
HAVING COUNT(PRODUCT_ID) >= 2
ORDER BY USER_ID ASC, PRODUCT_ID DESC;
```

<br>

## 2. HAVING

> GROUP BY 절로 그룹화한 데이터에 대해 집계 함수의 조건을 주기 위해 사용

WHERE 절과 동일한 형식으로 사용함

GROUP BY 절에 조건을 주기 위해 사용하므로, HAVING 단독만으로 쓰일 수 없다.

### ※ HAVING과 WHERE절의 차이?

- HAVING은 그룹 전체, 즉 `그룹을 나타내는 결과 집합의 행`에 대해서만 적용됨
- WHERE은 `조건을 만족하는 개별 행`에 모두 적용됨

### 1) 집계 함수와 WHERE, HAVING

> WHERE 절에서는 직접적으로 집계 함수(SUM, COUNT, AVG, MAX, MIN 등)을 사용할 수 없다

`집계 함수(Aggregate Function)`은 값들의 집합을 계산해서 하나의 값을 반환하는 함수를 말한다.

여기서 말하는 값들은 여러 행(row)이며, 즉 여러 행을 입력으로 받아 하나의 값을 반환하는 함수이다.

그런데 **WHERE 절은 직접적으로 집계 함수와 같이 쓸 수 없다.**

- WHERE 절은 "조건을 만족하는 `개별 행`"에 모두 적용된다고 했으므로, `여러 행들이 하나의 결과를 반환하는 집계 함수`와 개념적으로 어긋난다.
- 집계 함수의 집계 연산은 WHERE 절 다음에 진행된다.
  - 따라서 특정 조건을 만족하는 여러 행들에 대해서 집계 연산을 사용할 수는 있는데, 조건 자체에는 집계 함수가 올 수 없다.
    - 즉, `WHERE (조건)`을 만족하는 여러 행에 대해 `SELECT 구문에서 집계 함수를 사용`하는 것은 가능하지만, `WHERE 절 자체에서 집계 함수를 호출하여 사용하는 것은 불가능`하다.
- 단, 서브쿼리를 사용하는 경우에 서브 쿼리가 먼저 실행되기 때문에 WHERE 절에서 먼저 row들을 필터하기 전에 서브쿼리가 실행되므로 간접적으로 집계함수를 사용할 수 있는 경우가 있다.

따라서 GROUP BY 절로 그룹화한 데이터에 대해 집계 함수를 사용하여 그 결과를 조건을 걸기 위해서 HAVING을 사용한다.(집계 함수를 사용하여 WHERE 절에서 조건을 거는 것과 같은 형태로 사용)

- 예) 학년별로 그룹화하고 총점 평균이 60점 이상인 학년 데이터를 가져올 때 → `HAVING AVG(score) >= 60`

| 항목 | WHERE                           | HAVING                            |
| ---- | ------------------------------- | --------------------------------- |
| 용도 | 개별행들에 특정 조건을 적용     | 집계 함수 결과에 대한 조건을 적용 |
| 제약 | WHERE절에서 집계 함수 호출 불가 | 반드시 GROUP BY 절과 함께 사용    |

```sql
-- 재구매가 일어난 상품과 회원 리스트 구하기
SELECT USER_ID, PRODUCT_ID
FROM ONLINE_SALE
GROUP BY USER_ID, PRODUCT_ID # USER_ID, PRODUCT_ID 쌍을 기준으로 그룹화
HAVING COUNT(PRODUCT_ID) >= 2 # PRODUCT_ID가 2번 이상 등장했다면, 재구매가 발생한 것임
ORDER BY USER_ID ASC, PRODUCT_ID DESC;
```

- WHERE 절은 GROUP BY 절보다 먼저 실행되므로 재구매가 일어난 USER_ID, PRODUCT_ID 쌍을 찾기에는 부적절하다.

<br>

### 2) 예시

#### (1) WHERE절

> 조건에 만족하는 모든 개별 행을 찾음

|                         원본 테이블                          |                   WHERE 할인가 <= 32,000원                   |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/5606f6a6-d561-42d1-93cb-94428995531a) | ![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/d093fcef-c208-4b4f-99b9-184542a9f7d5) |

<br>

#### (2) GROUP BY, HAVING 절

> 각 그룹으로 묶고 그룹별로 연산 → 그룹별로 하나의 결과를 반환

| 원본 테이블                                                  | GROUP BY 상품명                                              | HAVING MAX(할인가)                                           |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/5606f6a6-d561-42d1-93cb-94428995531a) | ![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/d84deaef-69f7-4342-bbe0-87fd073c0ec0) | ![image](https://github.com/siwon-park/BackEnd_Study/assets/93081720/df14fc9d-b43e-44d7-8b53-3a798f0ce6d0) |

GROUP BY 자체도 하나의 행을 반환하지만, HAVING절의 집계함수와 함께 쓰여 집합 내에서 하나의 결과를 만들어 냄.
