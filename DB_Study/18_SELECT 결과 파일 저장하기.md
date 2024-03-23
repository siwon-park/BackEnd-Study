# 18_SELECT 결과 파일 저장하기

> DB에서 SELECT 쿼리의 결과를 파일로 저장하는 방법

## 1. MySQL / MariaDB

※ 경로를 지정하지 않으면 DB를 실행할 수 있는 bin 파일이 있는 경로에 저장된다.

### 1) 방법 1

`>` 사용

```bash
[mysql/mariadb] -u root -p -e "SELECT * FROM my_tbl;" [dbname] > [경로/파일명]

# 예시
mysql -u root -p 0000 -e "SELECT * FROMG my_tbl;" test_db > output.txt
```

<br>

### 2) 방법 2

`INTO OUTFILE`

```mysql
SELECT [칼럼] FROM [테이블명] INTO OUTFILE [경로/파일명];

# 예시1
SELECT * FROM my_tbl INTO OUTFILE 'output.txt';

# 예시2 - csv로 저장할 때, 구분자를 지정가능함
SELECT co1, col2, col3 INTO OUTFILE '~/output.csv' FIELDS TERMINATED BY ',' FROM my_tbl;
```

<br>

## 2. PostgreSQL

### 1) 방법 1

`>` 사용(mysql / mariadb)와 동일

```bash
psql -U username -p 5432 -d dbname -c "SELECT * FROM my_tbl;" > output.txt
```

<br>

### 2) 방법 2

`\o`명령어 사용

```postgresql
\o [경로/파일명] [SELECT 쿼리]; \q

# 예시
\o output.txt
SELECT * FROM my_tbl;
\q
```

