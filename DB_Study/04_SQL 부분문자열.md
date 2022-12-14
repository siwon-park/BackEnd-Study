# 04_SQL 부분 문자열

LIKE 외에 필드 값의 부분 문자열을 찾는 방법

## SUBSTR

특정 문자열에서 부분 문자열을 추출함

### 사용법

- `str`에서 `pos`위치와 그 이후의 모든 문자를 읽어옴

```mysql
SUBSTR(str, pos)
```

- `str`에서 `pos`위치의 문자를 `len`개 읽어옴

```mysql
SUBSTR(str, pos, len)
```

<br>

### 예시

- 예시 1

```mysql
SELECT SUBSTR(name, 3)
FROM contry
WHERE name = 'KOREA';
```

- 결과

```mysql
'REA'
```

<br>

- 예시 2

```mysql
SELECT SUBSTR(name, 2, 3)
FROM contry
WHERE name = 'KOREA';
```

- 결과

```mysql
'ORE'
```

<br>

## INSTR

문자열에 특정 문자열이 포함되어 있는지 여부를 확인하는 함수

정확히는 특정 문자열을 찾으면 해당 위치를 숫자로 반환함

### 사용법

```mysql
INSTR(칼럼명, '찾고자하는 문자열')
```

<br>

### 예시

- WHERE 절에서 사용
  - 부분 문자열을 찾는데 왜 0보다 크다는 조건식으로 들어가 있는지 궁금할 수도 있는데, 그 이유는 만약 찾고자 하는 문자열이 해당 문자열에 있다면 어쨌든 인덱스는 1부터 출발하니까 무조건 0보다 크기 때문.
  - 일반적으로는 WHERE 절에서 문자열을 찾을 땐 LIKE를 쓰는 것이 더 효율적임

```mysql
SELECT *
FROM emp
WHERE INSTR(ename, 'SIWON') > 0
```

<br>

- CASE 표현식에서 사용

```mysql
SELECT empno, ename, INSTR(ename, 'MI'),
CASE WHEN INSTR(ename, 'MI') > 0 THEN 'Y'
ELSE 'N'
END AS "포함여부"
FROM emp
WHERE JOB = 'CLERK'
```

