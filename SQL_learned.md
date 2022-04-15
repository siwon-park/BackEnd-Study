# SQL_learned

## 01. DB(Data Base)

체계화된 데이터의 모음

논리적으로 연관된 하나 이상의 자료의 모음으로 그 내용을 구조화함으로써 검색 및 갱신의 효율화를 꾀한 것

몇 개의 파일을 조직적으로 통합하여 자료 항목의 중복을 없애고, 구조화시켜놓은 자료의 집합체

- DB 사용의 장점: 중복 최소화, 무결성(정확성), 일관성, 독립성(물리적/논리적), 표준화, 보안 등



### 01. RDB(Relational DataBase)

관계형 DB

- 키(Key)와 값(Value)들의 형태가 표(Table)의 형태로 정리된 DB
- RDBMS: 관계형 데이터베이스 관리 시스템
  - MySQL, sqlite3 등...
    - sqlite3는 서버 형태가 아닌 파일 형태로 응용 프로그램에서 사용하는 비교적 가벼운 DB

### 02. Data Type

1. NULL : None, 값이 없음
2. INTEGER : 크기에 따라 0, 1, 2, 3, 4, 6, 8바이트에 저장된 부호있는 정수
3. REAL : 8바이트 부동 소숫점 숫자로 저장된 부동 소숫점 값
4. TEXT
5. BLOB : 입력된 그대로 저장된 데이터

※ Type Affinity: 특정 칼럼에 저장하도록 권장하는 데이터 타입



## 02. SQL(Structured Query Language)

> ※ `.sql`파일은 SQL 쿼리문을 작성하는 파일이고, `.sqlite3`는 DB파일이다.
>
> ※ `.`은 sqlite 프로그램의 `기능`을 실행하는 것임

### 01. 정의

RDBMS의 데이터 관리를 위해 설계된 특수 목적의 프로그래밍 언어(구조적 쿼리 언어)

- 활용:
  - DB 스키마, 테이블 생성 및 수정
  - 자료의 검색 및 관리
  - DB객체 접근 및 조정 관리



### 02. 분류

#### 01. DDL(Data Definition Language) - 데이터 정의 언어

관계형 데이터베이스 구조(스키마, 테이블)를 정의하기 위한 명령어

- `CREAT TABLE <테이블명>`
- `DROP TABLE <테이블명>`



#### 02. DML(Data Manipulation Language) - 데이터 조작 언어

데이터를 저장, 조회, 수정, 삭제 등을 하기 위한 명령어

- `INSERT INTO --` : 생성
- `SELECT * FROM --` : 조회
- `UPDATE --- SET -- ` : 수정, 업데이트

- `DELETE FROM --` : 삭제



#### 03. DCL(Data Control Language) - 데이터 제어 언어

데이터베이스 사용자의 권한 제어를 위해 사용하는 명령어



### 03. 생성 및 삭제

- Run query : 모든 쿼리 구문 순차적으로 실행
- Run selected query : 선택한 구문만 실행

#### 01. 터미널을 통한 생성

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



#### 02. SQL statement

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



### 04. CRUD

모든 SQL 구문은 소문자여도 작동은 하나, 대문자로 쓰는 것을 권장한다.

문자열은 쌍따옴표가 아닌 일반 따옴표로 쓴다.

#### 01. CREATE

- `INSERT INTO`: 특정 테이블에 레코드(행) 삽입(생성)
  - 모든 열에 데이터가 있는 경우에는 칼럼을 명시하지 않아도 됨
    - **id를 스키마에 직접 정의해줬다면, id의 칼럼과 값도 SQL구문에 넣어줘야함**

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



#### 02. READ

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



#### 03. DELETE

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



#### 04. UPDATE

- `UPDATE` : 기존 행의 데이터를 수정, 업데이트함
  - `SET`을 사용하여 새로운 값을 설정함

```sql
UPDATE 테이블명 SET 컬럼1=값1, 컬럼2=값2, ... WHERE 조건;

-- id가 3인 레코드의 age, address 데이터 변경
UPDATE users SET age=30, address='대전' WHERE rowid=3; 
```

※ 조건 WHERE 절을 명시하지 않을 시 테이블에 있는 모든 데이터가 SET에 입력된 데이터로 변경됨을 유의



### 05. ALTER TABLE

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



### 06. 기타

- sql에서 주석은 `--`(하이픈 2개) 이다



---

## 03. SQL with Django_ORM(Object Relational Mapping)

### 01. 기본 CRUD 로직

1. 모든 user 레코드 조회

   ```python
   User.objects.all()
   ```

      ```sql
   SELECT * FROM users_user;
      ```

2. user 레코드 생성

   ```python
   User.objects.create(first_name="SIWON", last_name="PARK", age=30, country="대전", phone="010-9245-8873", balance = 10000)
   ```

   ```sql
   INSERT INTO users_user VALUES (101, '시원', '박', 30, '대전', '010-9245-8873', 0);
   ```

   * 하나의 레코드를 빼고 작성 후 `NOT NULL` constraint 오류를 orm과 sql에서 모두 확인 해보세요.

3. 해당 user 레코드 조회

   - `102` 번 id의 전체 레코드 조회

   ```python
   User.objects.get(pk=102)
   ```

   ```sql
   SELECT * FROM users_user WHERE rowid = 102;
   ```

4. 해당 user 레코드 수정

   - ORM: `102` 번 글의 `last_name` 을 '김' 으로 수정
   - SQL: `102` 번 글의 `first_name` 을 '철수' 로 수정

   ```python
   User.objects.filter(pk=102).update(last_name='김') # 쿼리셋 반환의 경우 함수적용 가능
   ```

      ```sql
   UPDATE users_user SET first_name='철수' WHERE rowid = 102;
      ```

5. 해당 user 레코드 삭제

   - ORM: `102` 번 글 삭제
   - `SQL`:  `101` 번 글 삭제 

   ```python
   User.objects.filter(pk=102).delete()
   ```

   ```sql
   DELETE FROM users_user WHERE rowid = 101;
   ```



> filter()와 get()의 차이
>
> - filter() : QuerySet 반환 => QuerySet함수 적용 가능
>   - 예) User.objects.filter(pk=10).update(first_name='시원')
> - get() : 객체(인스턴스) 반환



---



### 2. 조건에 따른 쿼리문

1. 전체 인원 수 

   - `User` 의 전체 인원수

   ```python
   User.objects.all().count() # 또는 User.objects.count()
   ```

   ```sql
   SELECT COUNT(*) FROM users_user;
   ```

2. 나이가 30인 사람의 이름

   - `ORM` : `.values` 활용
     - 예시: `User.objects.filter(조건).values(컬럼이름)`
   - ※ filter(조건) => QuerySet반환 / filter(조건).values() => QuerySet 딕셔너리 반환

   ```python
   User.objects.filter(age=30).values('first_name', 'last_name')
   ```

      ```sql
   SELECT first_name, last_name FROM users_user WHERE age=30;
      ```

3. 나이가 30살 이상인 사람의 인원 수

   -  ORM: `__gte` , `__lte` , `__gt`, `__lt` -> 대소관계 활용 / 문자열 관련: `__contains`, `__startswith`, `__endswith` 등

   ```python
   User.objects.filter(age__gte=30).count()
   ```

      ```sql
   SELECT COUNT(*) FROM users_user WHERE age >= 30;
      ```

4. 나이가 20살 이하인 사람의 인원 수 

   ```python
   User.objects.filter(age__lte=20).count()
   ```

   ```sql
   SELECT COUNT(*) FROM users_user WHERE age <= 20;
   ```

5. 나이가 30이면서 성이 김씨인 사람의 인원 수

   ```python
   User.objects.filter(age=30, last_name='김').count()
   ```

      ```sql
   SELECT COUNT(*) FROM users_user WHERE age = 30 AND last_name = '김';
      ```

6. 나이가 30이거나 성이 김씨인 사람?

   ```python
   (User.objects.filter(last_name='김') | User.objects.filter(age=30)).count()
   # User.objects.filter(Q(age=30)|Q(last_name='김')).count()
   ```

   ```sql
   SELECT first_name, last_name FROM users_user WHERE age = 30 OR last_name = '김';
   ```

7. 지역번호가 02인 사람의 인원 수

   - `ORM`: `__startswith` 

   ```python
   User.objects.filter(phone__startswith='02').count()
   ```

      ```sql
   SELECT COUNT(*) FROM users_user WHERE phone LIKE '02-%';
      ```

8. 거주 지역이 강원도이면서 성이 황씨인 사람의 이름

   ```python
   User.objects.filter(country='강원도', last_name='황')
   ```

      ```sql
   SELECT first_name FROM users_user WHERE country = '강원도' AND last_name = '황';
      ```



---



### 3. 정렬 및 LIMIT, OFFSET

1. 나이가 많은 사람순으로 10명

   ```python
   User.objects.order_by('-age')[:10]
   ```

      ```sql
   SELECT * FROM users_user ORDER BY age DESC LIMIT 10;
      ```

2. 잔액이 적은 사람순으로 10명

   ```python
   User.objects.order_by('balance')[:10]
   ```

      ```sql
   SELECT * FROM users_user ORDER BY balance LIMIT 10;
      ```

3. 잔고는 오름차순, 나이는 내림차순으로 10명?

   ```python
   User.objects.order_by('balance', '-age')[:10]
   ```

   ```sql
   SELECT * FROM users_user ORDER BY balance ASC, age DESC LIMIT 10;
   ```

4. 성, 이름 내림차순 순으로 5번째 있는 사람

   ```python
   User.objects.order_by('-last_name', '-first_name')[4]
   ```

      ```sql
   SELECT * FROM users_user ORDER BY last_name DESC, first_name DESC LIMIT 1 OFFSET 4;
      ```



---



### 4. 표현식

#### 4.1 Aggregate

> - https://docs.djangoproject.com/en/3.2/topics/db/aggregation/#aggregation
> - '종합', '집합', '합계' 등의 사전적 의미
> - 특정 필드 전체의 합, 평균 등을 계산할 때 사용
> - `Django_aggregation.md` 문서 참고

1. 전체 평균 나이

   ```python
   User.objects.all().aggregate(Avg('age'))
   ```

      ```sql
   SELECT AVG(age) FROM users_user;
      ```

2. 김씨의 평균 나이

   ```python
   User.objects.filter(last_name='김').aggregate(Avg('age'))
   ```

      ```sql
   SELECT AVG(age) FROM users_user WHERE last_name = '김';
      ```

3. 강원도에 사는 사람의 평균 계좌 잔고

   ```python
   User.objects.filter(country='강원도').aggregate(Avg('balance'))
   ```

   ```sql
   SELECT AVG(balance) FROM users_user WHERE country = '강원도';
   ```

4. 계좌 잔액 중 가장 높은 값

   ```python
   User.objects.all().aggregate(Max('balance'))
   ```

      ```sql
   SELECT MAX(balance) FROM users_user;
      ```

5. 계좌 잔액 총액

   ```python
   User.objects.all().aggregate(Sum('balance'))
   ```

      ```sql
   SELECT SUM(balance) FROM users_user;
      ```



※ 필드 항목 변경 및 설정

- ORM
  - 예) User.objects.all().aggregate(my_avg=Avg('age')) : 'my_avg'라는 필드로 age의 평균을 반환
- SQL
  - 예) SELECT AVG(age) AS 'my_avg' FROM users_user WHERE last_name = '김';



#### 4.2 Annotate

1. 지역별 인원 수

   ```PYTHON
   User.objects.values('country').annotate(counts=Count('country'))
   ```

   ```SQL
   SELECT country, COUNT(*) FROM users_user GROUP BY country;
   ```

   