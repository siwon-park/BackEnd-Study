# DB

## 01. DB 기초

### 정의

여러 사람이 공유하고 사용할 목적으로 통합 관리되는 정보의 집합

**논리적으로 연관된 하나 이상의 자료 모음**으로 그 내용을 **고도로 구조화**함으로써 **검색과 갱신의 효율화**를 꾀한 것

몇 개의 자료 파일을 조직적으로 통합하여 자료의 중복을 없애고 자료를 구조화시켜놓은 집합체

<br>

### 장점

- 데이터 중복 최소화
- 데이터 공유
- 데이터 일관성 및 무결성
- 데이터 보안 유지
- 데이터 표준화
- 데이터의 논리적, 물리적 독립성

- 데이터 저장 공간 절약 등

<br>

### 단점

- 때로는 전문적인 지식이 필요
- 데이터가 많을 수록 많은 비용 부담
- 데이터의 백업과 복구가 어려움
- 시스템이 복잡함 등

<br>

### DBMS(Database Management System)

데이터 베이스 관리 프로그램

- DB 조작 인터페이스 제공(CRUD)
- 효율적인 데이터 관리 기능 제공
- DB 구축, 데이터 복구, 사용자 권한 등 데이터 관련 다양한 기능 제공

<br>

---

## 02. 관계형 데이터 베이스(Relational DB)

### RDB 정의

**행과 열로 구성된 2차원 테이블(Table) 기반**의 Database

데이터를 테이블 단위로 관리, 테이블 간의 관계를 이용하여 필요한 데이터 검색 가능

각 필드(열, 속성)별로 고유한 데이터 속성이 지정되어 있음

각 레코드(행)은 실제 데이터를 의미하며, 여러 속성에 해당하는 데이터가 담겨 있음

※ MySQL과 MariaDB는 많이 비슷함

<br>

----

## 03. SQL(Structured Query Language)

### SQL 정의

관계형 데이터 베이스에서 데이터 조작과 데이터 정의를 위해 사용하는 언어

※ 실무에서는 **데이터 조회**가 차지하는 비중이 높음

- 데이터 조회, 삽입, 삭제, 수정 및 사용자 권한 제어 등의 기능

- 표준 SQL은 모든 DBMS에서 사용 가능함

<br>

### 특징

- 배우고 사용하기엔 쉽지만 응용은 어려움
- SQL구문의 대소문자를 구분하진 않지만 대문자로 쓰는 것을 권장한다.(※ 데이터의 대소문자는 구문한다
- 절차적인 언어가 아니라 선언적인 언이이다
- DBMS에 종속적이지 않다 => 표준 SQL은 모든 DBMS에서 사용 가능함

<br>

### 분류

- DML(Data Manipulation Language): 데이터 조작 언어
  - DB에서 데이터를 조작, 조회할 때 사용함
  - 데이터의 CRUD
  - SELECT, INSERT, UPDATE, DELETE

<br>

- DDL(Data Definition Language): 데이터 정의 언어
  - DB객체(table, view, user, index 등)의 구조를 정의
  - CREATE, ALTER, DROP, RENAME

<br>

- TCL(Transaction Control Language):  트랜잭션 제어 언어
  - 트랜잭션 단위로 실행한 명령문을 적용, 취소
  - COMMIT, ROLLBACK

<br>

- DCL(Data Control Language): 데이터 제어 언어
  - DB, 테이블에 대한 접근 권한, CRUD 권한 정의/ 부여 등
  - GRANT, REVOKE

<br>

---

## 04. DDL

### 데이터베이스 생성하기

`CREATE DATABASE databasename;` => 새 데이터베이스 생성

`SHOW DATABASES;` => 데이터베이스 목록 확인

<br>

### 데이터베이스 삭제하기

데이터베이스의 모든 테이블을 삭제하고, 데이터베이스를 삭제함

`DROP {DATABASE | SCHEMA} [IF EXISTS] db_name;`

- 삭제 시 DROP DATABASE 권한 필요
- DROP SCHEMA는 DROP DATABASE와 동의어
- IF EXISTS는 데이터베이스가 없을 시 발생할 수 있는 에러를 방지함

<br>

### 데이터베이스 사용하기

`USE db_name;`

<br>

### 테이블 생성하기

```sql
CREATE TABLE table_name (
column1 datatype [options],
column2 datatype, ...);
```

- options
  - NOT NULL => 빈 값을 허용하지 않음
  - UNIQUE => 컬럼에 중복된 값을 저장할 수 없음(단, NULL값은 허용)
  - DEFAULT => 값이 전달되지 않을 경우 기본값을 설정함
  - AUTO INCREMENT => 새 레코드가 추가될 때 마다 필드 값의 PK를 자동으로 1 증가시킴
  - PRIMARY KEY => PK값으로 지정함
  - FOREIGN KEY => 외래키 설정. NULL값을 허용. 어떤 칼럼의 어떤 데이터를 참조하는지 반드시 지정 필요
  - UNSIGNED => 숫자인 경우에만 해당하며, 숫자가 0 또는 양수로 제한됨

<br>

----

## 05. DML

데이터베이스에서 CRUD를 할 때 사용

<br>

### INSERT ... INTO

테이블에 새로운 레코드를 삽입

- 생성 시 작성한 모든 컬럼에 입력 값이 주어지면 컬럼명들을 생략 가능
- 컬럼이름과 입력 값의 순서가 일치하도록 작성
- 튜플형태로 묶어서 ,로 단위를 끊어 여러 레코드들을 넣을 수도 있음
- NULL, DEFAULT, AUTO INCREMENT 설정 필드의 경우 생략 가능

<br>

### SELECT ... FROM

하나 이상의 테이블에서 레코드들을 조회할 때 사용

![image](https://user-images.githubusercontent.com/93081720/165551388-253de24c-477f-49ea-a41c-4229fbbf5b32.png)

<br>

### UPDATE ... SET

테이블의 행을 수정

- WHERE(조건)을 쓰지 않을 경우 테이블의 모든 데이터가 바뀌므로 주의

<br>

### DELETE ... FROM  

테이블의 행을 삭제

<br>

----

## 트랜잭션(Transaction)

### 정의

커밋(Commit)하거나 롤백(Rollback)할 수 있는 가장 작은 **작업 단위**

- 커밋: 트랜잭션을 종료하여 변경사항에 대해 서 영구적으로 저장하는 SQL
- 롤백: 트랜잭션에 의해 수행된 모든 변경사항을 실행 취소하는 SQL

※ MySQL에서는 기본적으로 autocommit 설정 상태임. 따라서 SQL문이 오류를 반환하지 않으면 commit을 수행함

### 명령어

START TRANSACTION

COMMIT

ROLLBACK
