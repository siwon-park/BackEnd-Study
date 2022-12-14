# 04_SQL 부분 문자열

## SUBSTR

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

