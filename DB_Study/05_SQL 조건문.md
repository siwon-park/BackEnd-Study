# 05_SQL 조건문

## IF

```mysql
IF(조건문, 참일 때의 값, 거짓일 때의 값)
```

### 예시

- 값에 대해 분기 처리를 해줄 수 있고

```mysql
SELECT IF(2 > 1, 'O', 'X') AS RESULT
FROM NUMBERS;
```

- 다른 컬럼에 대한 분기 처리도 가능하다.

```mysql
SELECT IF(column_name is null, colum_1, colum_2) AS RESULT
FROM COLUMNS_TABLE;
```

또한 IF 절 안에 IF절로 중첩이 가능하다.

<br>

## IFNULL

```mysql
IFNULL(칼럼명, NULL일 경우 대체할 값)
```

### 예시

```mysql
SELECT IFNULL(NAME, '이름 없음')
FROM STUDENTS;
```

역시 IF와 마찬가지로 중첩해서 사용이 가능하다.

<br>

## ISNULL

```mysql
ISNULL(칼럼명, NULL일 경우 대체할 값)
```

### 예시

```mysql
SELECT ISNULL(is_discount, 0) AS RESULT
FROM PRODUCTS;
```

is_discount 칼럼의 값이 NULL 이라면 0 을 출력하고, NULL 이 아니라면 is_discount 의 값을 출력한다.



※ `IS NULL`과 헷갈리지 않도록 유의

`IS NULL`은 `IS`와 `NULL`을 각각 쓴 것으로 어떤 필드의 값이 NULL인 경우 TRUE, NULL이 아닌 경우 FALSE를 나타낸다.

```sql
SELECT ORDER_ID, PRODUCT_ID, DATE_FORMAT(OUT_DATE, '%Y-%m-%d'),
    CASE
        WHEN OUT_DATE IS NULL THEN '출고미정'
        WHEN DATEDIFF(OUT_DATE, "2022-05-01") > 0 THEN '출고대기'
        ELSE '출고완료'
    END AS '출고여부'
FROM FOOD_ORDER ORDER BY ORDER_ID ASC;
```

<br>

## NVL

```mysql
NVL(칼럼명, NULL일 경우 대체할 값)
```

### 예시

```mysql
SELECT NVL(user_name, 'no_name')
FROM USERS;
```

user_name 칼럼 값이 NULL 이면 'no name' 을 출력하고, NULL이 아니라면 user_name 칼럼 값을 출력한다.

<br>

## CASE / WHEN / THEN / (ELSE) END

다른 언어들의 switch 구문과 유사함

WHEN과 THEN은 쌍을 이루어서 사용해야하고 

```mysql
CASE
WHEN 값 또는 조건1
THEN 일치할 경우 값1
WHEN 값 또는 조건2
THEN 일치할 경우 값2
...
ELSE
END
```

### 예시

```mysql
SELECT ANIMAL_ID, NAME,
CASE
WHEN SEX_UPON_INTAKE LIKE 'Neutered%' THEN 'O' -- 필드값에 Neutered가 포함되어있으면 'O'
WHEN INSTR(SEX_UPON_INTAKE, 'Spayed') THEN 'O' -- 필드값에 Spayed가 포함되어 있으면 'O'
ELSE 'X' -- 아니면 전부 'X'
END AS "중성화" -- END AS를 통해 해당 칼럼의 이름을 변경 가능
FROM ANIMAL_INS
ORDER BY ANIMAL_ID;
```

