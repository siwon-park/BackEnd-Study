# 04_SQL 문자열

> SQL 문자열 조작 방법 및 조작에 필요한 함수 정리

<br>

## 1. 부분 문자열

LIKE 외에 필드 값의 부분 문자열을 찾는 방법

### 1) SUBSTR

특정 문자열에서 부분 문자열을 추출함

#### 사용법

- `str`에서 `pos`위치와 그 이후의 모든 문자를 읽어옴

```mysql
SUBSTR(str, pos)
```

- `str`에서 `pos`위치의 문자를 `len`개 읽어옴

```mysql
SUBSTR(str, pos, len)
```

<br>

#### 예시

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

### 2) INSTR

문자열에 특정 문자열이 포함되어 있는지 여부를 확인하는 함수

정확히는 특정 문자열을 찾으면 해당 위치를 숫자로 반환함

#### 사용법

```mysql
INSTR(칼럼명, '찾고자하는 문자열')
```

<br>

#### 예시

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

<br>

## 2. 문자열 연결

문자열 연결

### 1) CONCAT

> CONCATENATE; 연결하다

인자로 주어진 문자열들을 연결함

#### 사용법

콤마(`,`)로 구분하여 문자열을 연결할 수 있음. 단, JAVA에서와 마찬가지로 연결 시 공백으로 구분하지 않고 바로 문자열을 이음.

따라서 띄어쓰기를 하고 싶다면 공백을 앞이나 뒤에 추가하는 방식으로 사용할 수 있다.

```SQL
CONCAT(문자열1, 문자열2, ..., 문자열)
```

※ ORACLE의 경우 CONCAT 함수의 인자를 2개 밖에 허용하지 않기 때문에 문자열을 여러 개 연결하기 위해서는 CONCAT 함수를 중첩해서 사용해야 한다.

※ 또한 ORACLE의 경우 CONCAT 함수를 사용하면 묵시적으로 문자열로 변환해서 사용하지만, MYSQL의 경우 모든 것을 문자열로 바꾸지는 않는다.

- 예를 들어, ORACLE에서 CONCAT에 NULL이 있으면 공백으로 치환되지만, MYSQL은 그대로 NULL을 리턴한다.

<br>

#### 예시

- 예시

```SQL
SELECT CONCAT('삼성', '청년', 'SW', '아카데미') AS "SSAFY"
FROM MY_TABLE;
```

- 결과

```SQL
"삼성청년SW아카데미"
```

<br>
