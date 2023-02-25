

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

> GROUP BY 절에 조건을 주기 위해 사용

WHERE 절과 동일한 형식으로 사용함

GROUP BY 절에 조건을 주기 위해 사용하므로, HAVING 단독만으로 쓰일 수 없다.

### ※ HAVING과 WHERE절의 차이?

- HAVING은 그룹 전체, 즉 `그룹을 나타내는 결과 집합의 행`에 대해서만 적용됨
- WHERE은 `조건을 만족하는 개별 행`에 모두 적용됨

WHERE 절은 GROUP BY 절보다 먼저 실행되므로 재구매가 일어난 USER_ID, PRODUCT_ID 쌍을 찾기에는 부적절하다.

따라서 GROUP BY 절로 그룹화한 곳에 조건을 걸기 위해서 HAVING을 사용하여 WHERE 절에서 사용하는 조건절과 동일한 조건절을 작성한다.

```sql
-- 재구매가 일어난 상품과 회원 리스트 구하기
SELECT USER_ID, PRODUCT_ID
FROM ONLINE_SALE
GROUP BY USER_ID, PRODUCT_ID # USER_ID, PRODUCT_ID 쌍을 기준으로 그룹화
HAVING COUNT(PRODUCT_ID) >= 2 # PRODUCT_ID가 2번 이상 등장했다면, 재구매가 발생한 것임
ORDER BY USER_ID ASC, PRODUCT_ID DESC;
```

