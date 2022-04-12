# 220314_TIL

## DB(Data Base)

체계화된 데이터의 모음

논리적으로 연관된 하나 이상의 자료의 모음으로 그 내용을 구조화함으로써 검색 및 갱신의 효율화를 꾀한 것

몇 개의 파일을 조직적으로 통합하여 자료 항목의 중복을 없애고, 구조화시켜놓은 자료의 집합체

- DB 사용의 장점: 중복 최소화, 무결성(정확성), 일관성, 독립성(물리적/논리적), 표준화, 보안 등



### 1. RDB(Relational DataBase)

관계형 DB

- 키(KEy)와 값(Value)들의 형태가 표(Table)의 형태로 정리된 DB
- RDBMS: 관계형 데이터베이스 관리 시스템
  - MySQL, sqlite3 등...
    - sqlite3는 서버 형태가 아닌 파일 형태로 응용 프로그램에서 사용하는 비교적 가벼운 DB

### 2. Data Type

1. NULL : None, 값이 없음
2. INTEGER : 크기에 따라 0, 1, 2, 3, 4, 6, 8바이트에 저장된 부호있는 정수
3. REAL : 8바이트 부동 소숫점 숫자로 저장된 부동 소숫점 값
4. TEXT
5. BLOB : 입력된 그대로 저장된 데이터

※ Type Affinity: 특정 칼럼에 저장하도록 권장하는 데이터 타입



## SQL(Structured Query Language)

> ※ `.sql`파일은 SQL 쿼리문을 작성하는 파일이고, `.sqlite3`는 DB파일이다.
>
> ※ `.`은 sqlite 프로그램의 `기능`을 실행하는 것임

### 1. 정의

RDBMS의 데이터 관리를 위해 설계된 특수 목적의 프로그래밍 언어(구조적 쿼리 언어)

- 활용:
  - DB 스키마, 테이블 생성 및 수정
  - 자료의 검색 및 관리
  - DB객체 접근 및 조정 관리



### 2. 분류

#### 1. DDL(Data Definition Language) - 데이터 정의 언어

관계형 데이터베이스 구조(스키마, 테이블)를 정의하기 위한 명령어

- `CREAT TABLE <테이블명>`
- `DROP TABLE <테이블명>`



#### 2. DML(Data Manipulation Language) - 데이터 조작 언어

데이터를 저장, 조회, 수정, 삭제 등을 하기 위한 명령어

- `INSERT INTO --` : 생성
- `SELECT * FROM --` : 조회
- `UPDATE --- SET -- ` : 수정, 업데이트

- `DELETE FROM --` : 삭제



#### 3. DCL(Data Control Language) - 데이터 제어 언어

데이터베이스 사용자의 권한 제어를 위해 사용하는 명령어



### 3. 생성 및 삭제

- Run query : 모든 쿼리 구문 순차적으로 실행
- Run selected query : 선택한 구문만 실행

#### 1. 터미널을 통한 생성

##### 1. 데이터베이스 생성하기

```sqlite
$ sqlite3 mydb.sqlite3  -- <파일명>.sqlite3
sqlite> .database
```



##### 2. CSV 파일을 Table로 만들기

```sqlite
sqlite> .mode csv
sqlite> .import <파일명.csv> <테이블명>
sqlite> .tables
```



#### 2. SQL statement

SQL 구문의 끝에는 반드시 `;`(세미 콜론)을 붙여준다. Django가 아니므로, trailing comma를 쓰지 않도록 유의 할 것!

##### 1. `CREATE TABLE + <테이블명>`

```sql
CREATE TABLE users (
  id INTEGER PRIMARY KEY,
  name TEXT NOT NULL,
  age INT NOT NULL,
  address TEXT NOT NULL  
);
```

- `.schema <테이블명>`으로 터미널에서 스키마 확인 가능
- `PRIMARY KEY`를 설정할 때는 정수형의 경우 반드시 `INTEGER`로 선언해야함
- 기본적으로 id(pk) 속성을 정의하지 않으면 sqlite에서는 `rowid`라는 속성을 자동적으로 정의하고 있음
  - 숨겨져 있기 때문에 조회하려면 `SELECT rowid, * FROM <테이블명>`을 해야함
- 공백으로 비워둬서는 안 되는 정보는 `NOT NULL`속성을 정의할 수 있다.



##### 2. `DROP TABLE + <테이블명>`

```sql
DROP TABLE users;
```



### 4. CRUD

모든 SQL 구문은 소문자여도 작동은 하나, 대문자로 쓰는 것을 권장한다.

문자열은 쌍따옴표가 아닌 일반 따옴표로 쓴다.

#### 1. CREATE

- `INSERT INTO`: 특정 테이블에 레코드(행) 삽입(생성)
  - 모든 열에 데이터가 있는 경우에는 칼럼을 명시하지 않아도 됨
    - id를 스키마에 직접 정의해줬다면, id의 칼럼과 값도 SQL구문에 넣어줘야함

```sql
INSERT INTO 테이블명 (칼럼1, 칼럼2, ...) VALUES (값1, 값2, ...);
```

- 한번에 여러 데이터를 넣기 → VALUES 입력 부분만 여러 번 쓰고 끝에 세미콜론(`;`)으로 마무리하면 됨

```sql
INSERT INTO countries VALUES
('0203', '2019-12-31', '2020-01-03', 'suite', 900),
('1102', '2020-01-04', '2020-01-08', 'suite', 850),
('303', '2020-01-01', '2020-01-03', 'deluxe', 500),
('807', '2020-01-04', '2020-01-07', 'superior', 300);
```



#### 2. READ

- `SELECT` : 테이블에서 데이터를 조회함
  - 다양한 절(clause)와 함께 사용 가능: `ORDER BY`, `DISTINCT`, `WHERE`, `LIMIT`, `GROUP BY` 등

```sql
SELECT 컬럼1, 컬럼2, ... FROM 테이블명;

SELECT rowid, name, age FROM users;
SELECT rowid, * FROM users; -- *(asterics)는 전체를 의미함
```



##### ※ clause(절) 옵션

- `DISTINCT` : 특정 컬럼을 기준으로 중복없이 데이터를 조회함 
  - SELECT 키워드 바로 뒤에 작성해야함

```sql
SELECT DISTINCT 컬럼 FROM 테이블명;

SELECT DISTINCT age FROM users;
```



- `LIMIT` : 쿼리에서 반환되는 행의 수를 제한함
  - 특정 행 다음부터 조회하기 위해 `OFFSET` 키워드와 함께 사용하기도 함 

```sql
SELECT rowid, name FROM users LIMIT 1; -- 1개의 상위 레코드 조회
SELECT rowid, name, age FROM users LIMIT 1 OFFSET 2; -- 3행에 있는 1개의 레코드 조회
```



- `WHERE` : 특정 검색 조건을 지정하여 쿼리에서 행을 반환받음

```sql
SELECT 컬럼1, 컬럼2, ... FROM 테이블명 WHERE 조건;

SELECT age FROM users WHERE age>=30; -- users에서 age가 30이상인 age 데이터를 조회함
SELECT * FROM users WHERE address='대전'; -- users에서 주소가 대전인 모든 데이터를 조회함
```

 - `AND` / `OR` 옵션

```sql
-- age가 30이상이고(AND), 성이 김씨인 사람의 데이터를 조회
SELECT * FROM users WHERE age>=30 AND last_name='김';

-- age가 20이상이거나(OR), 성이 박씨인 사람의 데이터를 조회
SELECT * FROM users WHERE age>=20 OR last_name='박';
```



- `ORDER BY` : 조회 결과에 대한 정렬을 함
  - `ASC` : 오름차순(default), `DESC` : 내림차순
  - `WHERE`절이 있다면 그 다음에 써야하고, `GROUP BY절`이 있다면 그 다음에 써야함(사실상 거의 끝)

```sql
SELECT * FROM 테이블명 ORDER BY 컬럼1, 컬럼2, ... ASC;
SELECT * FROM 테이블명 ORDER BY 컬럼1, 컬럼2, ... DESC;

-- users에서 나이순 오름차순 정렬하여 상위 10개의 데이터 조회
SELECT * FROM users ORDER BY age ASC LIMIT 10;

-- users에서 나이, 성순 내림차순 정렬하여 상위 10개의 데이터 조회
SELECT * FROM users ORDER BY age, last_name DESC LIMIT 10;
```

 

- `GROUP BY` : 행 집합에서 요약 행 집합을 만듦
  - `WHERE`절이 있다면 반드시 그 뒤에 작성해야함

```sql
SELECT 컬럼1, aggregate_function(컬럼) FROM 테이블명 GROUP BY 컬럼1, 컬럼2;

-- users에서 각 성이 몇 명씩 있는지 조회
SELECT last_name, COUNT(*) FROM users GROUP BY last_name;
SELECT last_name, COUNT(*) AS last_name_count FROM users GROUP BY last_name;
```

※ AS를 활용해서 aggregate function에 해당하는 부분을 새로 정의한 컬럼 명으로 바꿔서 조회 가능



##### ※ Aggregate function

SELECT 구문에서만 활용되며, 값 집합에 대한 계산 결과 등을 반환함

- COUNT(칼럼) : 칼럼의 항목 수를 가져옴
- AVG(칼럼) : 칼럼에 해당하는 값들의 평균을 계산해서 가져옴
- MAX(칼럼) : 칼럼에 해당하는 값들 중 최댓값을 가져옴
- MIN(칼럼) : 칼럼에 해당하는 값들 중 최솟값을 가져옴
- SUM(칼럼) : 칼럼에 해당하는 모든 값의 합을 계산함



##### ※ LIKE

패턴 매칭을 통해 데이터를 조회하는 방법

sqlite는 패턴 매칭을 위한 2가지 wildcards를 제공함

- `%` : 0개 이상의 문자 => 이 자리에 문자열이 있을 수도 있고 없을 수도 있다.
  - SIW%N → SIWON, SIWEN, SIWN
- `_` : 임의의 1개 문자 => 이 자리에 반드시 한 개의 문자가 존재한다.
  - SIW_N → SIWON, SIWEN

WHERE 구문과 함께 쓰이며, 이 때의 WHERE구문 뒤에는 조건이 아니라 칼럼이 온다.

```sql
SELECT * FROM 테이블명 WHERE 칼럼 LIKE '와일드카드 패턴';

-- users에서 나이가 20대인 사람을 조회
SELECT * FROM users WHERE age LIKE '2_';

-- users에서 전화번호 중간이 9245인 사람만 조회
SELECT * FROM users WHERE phone LIKE '%-9245-%';

-- hotels에서 방 번호가 0으로 시작하거나 등급이 'deluxe'인 데이터를 조회
SELECT * FROM hotels WHERE room_num LIKE '0%' OR grade='deluxe';

-- hotels에서 체크인 날짜가 2020-01-04이면서 방 번호가 0으로 시작하지 않는 데이터를 가격을 기준으로 오름차순해서 정렬 조회
SELECT * FROM hotels WHERE check_in='2020-01-04' AND room_num NOT LIKE '0%' ORDER BY price ASC ;
```



#### 3. DELETE

- `DELETE` : 테이블에서 행을 제거함
  - 일반적으로 고유한(unique)값인 id(rowid)를 기준으로 삭제함

```sql
DELETE FROM 테이블명 WHERE 조건;
DELETE FROM users WHERE rowid=3; -- id가 3인 레코드를 삭제함
```

※ sqlite는 기본적으로 id를 삭제해도 데이터 추가 시 삭제되었던 id값을 **재사용**함
재사용하는 것을 방지하기 위해 스키마 정의 시 `AUTOINCREMENT`를 설정하면 됨(django에서는 기본 설정값)

```sql
CREATE TABLE 테이블명 (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
);
```



#### 4. UPDATE

- `UPDATE` : 기존 행의 데이터를 수정, 업데이트함
  - `SET`을 사용하여 새로운 값을 설정함

```sql
UPDATE 테이블명 SET 컬럼1=값1, 컬럼2=값2, ... WHERE 조건;

-- id가 3인 레코드의 age, address 데이터 변경
UPDATE users SET age=30, address='대전' WHERE rowid=3; 
```

※ 조건 WHERE 절을 명시하지 않을 시 테이블에 있는 모든 데이터가 SET에 입력된 데이터로 변경됨을 유의



### 5. ALTER TABLE

- 테이블의 이름을 변경

```sql
ALTER TABLE 기존 테이블명 RENAME TO 새로운 테이블명;
```



- 테이블에 새로운 칼럼을 추가

```sql
ALTER TABLE 테이블명 ADD COLUMN 칼럼명 데이터타입;

-- articles 테이블에 TEXT타입인 created_at칼럼을 추가함
ALTER TABLE articles ADD COLUMN created_at TEXT;
ALTER TABLE articles ADD COLUMN others TEXT NOT NULL DEFAULT '비고';
```

※ 단, 이 때 NOT NULL을 설정해 줄 수 없다. 왜냐하면 기존에 존재하던 레코드 데이터 값들에는 새롭게 추가하는 칼럼에 대한 정보가 없기 때문이다.
따라서 위의 예시와 같이 그냥 데이터 타입만 명시해주거나, NOT NULL 다음에 DEFAULT값을 설정해주면 된다.



- 테이블 이름 수정(sqlite 3.25버전 이후)

```sql
DROP COLUMN 기존 칼럼명 TO 새로운 칼럼명;
```



### 6. 기타

- sql에서 주석은 `--`(하이픈 2개) 이다