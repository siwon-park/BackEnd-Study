# 06_SQL 몫, 나머지

SQL에서 몫과 나머지를 계산하는 방법

## 몫

### FLOOR

```mysql
FLOOR(나눌 값 / 나누는 값)
```

<br>

## 나머지

### MOD

```mysql
MOD(나눌 값 , 나누는 값)
```

### REMAINDER

```mysql
REMAINDER(나눌 값, 나누는 값)
```

<br>

## 반올림, 올림, 내림

### ROUND

반올림

```sql
ROUND(값, 자릿수, 반올림 여부)
```

※ 자릿수는 양수일 경우 소수점을 기준으로 하고, 음수일 경우 정수를 기준으로 함

- 소수점 양수자리까지 반올림, 정수부 음수의 절댓값의 자리수까지 반올림

※ 반올림 여부는 음수면 버림 처리, 0이나 없으면 반올림 처리

```sql
-- 소수점 반올림
SELECT ROUND(945.13, 1) -- 945.10
SELECT ROUND(945.16, 1) -- 945.20
SELECT ROUND(945.16, 1, -1) -- 945.10

-- 정수 반올림
SELECT ROUND(946, -1) -- 950
SELECT ROUND(946, -2) -- 900
SELECT ROUND(946, -1, -1) -- 940
```

<br>

### CEILING

올림

```sql
CEILING(값)
```

```sql
SELECT CEILING(940.16) -- 941
```

<br>

### FLOOR

내림

```sql
FLOOR(값)
```

```sql
SELECT FLOOR(945.16) -- 945
```



