# 21_SQL 쿼리 결과 파일 저장

> SELECT 쿼리의 결과를 파일로 저장하기

## 1. PostgreSQL

### 1) COPY

DB를 실행시키고 SQL 터미널에서 COPY 명령어를 통해 서버에 파일을 생성한다.

```sql
COPY (SELECT 쿼리) TO '저장할 경로/파일명.확장자' WITH (FORMAT 포맷);

-- 예시
COPY (SELECT name, age FROM users WHERE created_at >= '2024-07-23') TO '/tmp/output.txt' WITH (FORMAT text);
```

단, 파일을 생성해야 하기 때문에 서버에 접속한 사용자가 파일 시스템을 사용할 수 있는 권한이 있어야 한다.

### 2) 리다이렉션

`>`(리다이렉션)을 사용하여 파일을 생성

#### (1) psql + 리다이렉션

```sql
psql -d [DB] -c "SELECT 쿼리" > 파일명.확장자

-- 예시
psql -d my_database -c "SELECT * FROM users" > output.txt
```

#### (2) SQL 파일 실행 후 결과 리다이렉션

```sql
-- .sql 파일 생성 후 쿼리 작성
psql -d [DB] -c 생성한sql파일.sql > 파일명.확장자

-- 예시
psql -d my_database -c "selectqueries.sql" > output.txt
```

<br>

## 2. MySQL / MariaDB

### 1) SELECT ... INTO OUTFILE

```sql
SELECT ... 
INTO OUTFILE '파일경로/파일명.확장자'
FIELDS TERMINATED BY '구분자'
ENCLOSED BY '구분자'
LINES TERMINATED BY '구분자'
FROM [table];
```

- FIELDS TERMINATED BY: 각 필드를 구분자로 구분함
- ENCLOSED BY: 각 필드를 구분자로 감쌈
- LINES TERMINATED BY: 각 라인을 구분자로 구분함

```sql
SELECT id, name, email
INTO OUTFILE '파일경로/파일명.확장자'
FIELDS TERMINATED BY ',' -- ,(콤마)로 필드를 구분
ENCLOSED BY '""' -- 필드의 값을 ""으로 감싸서 표시
LINES TERMINATED BY '\n' -- 각 라인을 개행으로 구분 
FROM users;
```

### 2) mysqldump

```sql
mysqldump -u [user_name] -p [database_name] [table] > '파일경로/파일명.sql'

-- 예시
mysqldump -u admin -p my_db my_table > /tmp/dump.sql
```

<br>