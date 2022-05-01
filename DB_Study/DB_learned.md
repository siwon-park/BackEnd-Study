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

## 04. DDL(Data Definition Language)

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

## 05. DML(Data Manipulation Language)

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

----

## MySQL Built-in Function

CREATE DATABASE 'SCOTT' DEFAULT CHARACTER SER 'utf8mb4';

=> SCOTT이라는 이름의 데이터베이스를 생성, 이모지까지 등록 가능한 DB로



SHOW DATABASES;

=> 데이터베이스들 현황을 보여줌



USE SCOTT;

=> SCOTT이라는 데이터베이스를 사용함



CREATE TABLE IF NOT EXISTS 'BONUS' (

'ENAME' varchar(10) DEFAULT ....

...);

=> BONUS라는 이름의 테이블을 만드는데 존재하지 않을 경우에만 만듦



DROP TABLE IF EXISTS 'DEPARTMENT';

=> DEPARTMENT라는 이름의 테이블이 있으면 삭제해라





SELECT AVG(sal + IFNULL(comm, 0)) FROM employee;



----

### 숫자 관련 MySQL 함수

POW

SELECT POW(2, 3);



ROUND

SELECT ROUND(1526.159) => 1526

SELECT ROUND(1526.159, 2) => 1526.16 (반올림해서 소수 두 번째자리까지 표현해라)

SELECT ROUND(1526.159, -1) => 1530 (1의 자리에서 반올림해서 표현해라)



SELECT concat('나의 이름은', ename, '입니다.') FROM emp WHERE job = 'PRESIDENT';



SELECT length('siwon'); => 5



SELECT locate('abc', 'ababcabc'); => 3(MySQL에서는 1번부터 인덱스를 잡는다)



SELECT left('Hello SIWON', 5); => Hello

SELECT right('Hello SIWON', 5); => SIWON

<br>

### 날짜 관련 함수

SELECT now(); => 현재 시간 출력

SELECT DAY(now()); => 현재의 날짜만 뽑음

SELECT month(now()); => 현재의 월을 뽑음

SELECT monthname(now()); => 현재 월의 이름을 뽑음

SELECT yearweek(now()); => 현재의 연도의 주차를 뽑음

<br>

SELECT datediff('2022-12-31', date(now()));



HAVING => 그룹으로 묶은 결과에 조건을 추가할 경우 사용(GROUP BY가 있을 경우에 사용)

<br>

----

## JOIN

둘 이상의 테이블에서 데이터를 조회하기 위해 사용

일반적으로 PK 및 FK에 대해 사용하지만 논리적 관계만으로도 JOIN 가능

- INNER JOIN: **JOIN 조건에 해당하는 칼럼 값이 양쪽 테이블 모두에 존재하는 경우에만** 조회. 동등 조인(equal join)이라고도 한다. N개의 테이블 조인 시 N-1개의 조인 필요
- OUTER JOIN: **조인 조건에 해당하는 칼럼 값이 한쪽 테이블에만 존재 하더라도 조회 가능**. 기준 테이블에 따라 LEFT OUTER JOIN, RIGHT OUTER JOIN으로 구분 가능

### 조인의 필요성?

사번이 7788번인 사원의 이름과 업무, 부서 이름 등을 조회하고자 하는데, 사원에 대한 정보는 emp라는 테이블에, 부서에 대한 정보는 dept라는 테이블에 있을 경우 두 테이블에서 정보를 합쳐서 가져와야함



※ 카타시안 곱 : 두 테이블에 존재하는 데이터 셋의 경우의 수의 모든 조합을 출력하게 되는 것을 말함



### 1. INNER JOIN

 **JOIN 조건에 해당하는 칼럼 값이 양쪽 테이블 모두에 존재하는 경우에만** 조회 가능

INNER JOIN을 쓸 때는 ON을 써야함

```mysql
SELECT ename, emp.deptno, dname
FROM emp
INNER JOIN dept
ON e.deptno = d.deptno
WHERE e.empno = 7788;
-- emp라는 테이블에 dept를 INNER JOIN하는데, e.deptno == d.deptno인 데이터를 JOIN시키고, e.empno가 7788인 데이터를 조인한 테이블에서 조회함
```



※ INNER 조인 절을 쓰지 않고, WHERE절 만으로도 INNER JOIN이 가능함

```mysql
SELECT column_name, ...
FROM table_1, table_2, ...
WHERE table_1.column_name = table_2.column_name;
-- 이와 같은 형태로 사용 가능
```



### 2. OUTER JOIN

두 테이블에서 **하나의 테이블에 조인 조건 데이터가 존재하지 않더라도**(= 조인 조건을 만족하지 않더라도) 두 테이블에서 데이터를 조회하기 위해 사용

기준 테이블에 따라 JOIN 형태로 구분함(FULL OUTER JOIN은 mySQL에서 지원하지 않는다.)

![image](https://user-images.githubusercontent.com/93081720/166097948-0c16e0c5-2e1b-4c5d-8aca-44f04db109f5.png)



OUTER JOIN의 필요성

- 부서명이 NULL인 직원이 있다고 했을 때, INNER JOIN(이너 조인, 동등 조인)으로 데이터를 검색하면 부서가 없는 직원은 검색되지 않음



#### LEFT OUTER JOIN

왼쪽 테이블을 기준으로 JOIN하여 JOIN조건에 부합하지 않는 데이터까지 조회

왼쪽 테이블의 데이터는 모두 조회하면서, e.deptno와 d.deptno가 같으면 JOIN해서 조회하라(IF POSSIBLE)

```mysql
SELECT e.ename, e.deptno, d.dname
FROM emp e LEFT OUTER JOIN dept d
ON e.deptno = d.deptno
```

 

#### RIGHT OUTER JOIN

오른쪽 테이블을 기준으로 JOIN하여 JOIN조건에 부합하지 않는 데이터까지 조회

```mysql
SELECT e.ename, e.deptno, d.dname
FROM dept d RIGHT OUTER JOIN emp e
ON e.deptno = d.deptno
-- 이렇게 쓸 경우 위에 LEFT JOIN과 결과는 같음
-- 그러나 RIGHT OUTER JOIN은 보통 이렇게 안 쓰고 아래와 같이 LEFT와는 조회 테이블 기준이 바뀐 채 씀

SELECT d.deptno, d.dname, e.empno, e.ename
FROM emp e RIGHT OUTER JOIN dept d
ON e.deptno = d.deptno;
```



내가 어디를 기준으로 두느냐에 따라 데이터가 나올 수도 안 나올 수도 있음. 따라서 LEFT OUTER JOIN을 해야하는지, RIGHT OUTER JOIN을 해야하는지 아는 것이 중요함



### 3. SELF JOIN



JOIN 키워드를 쓰지 않고 모든 사원 번호, 이름, 매니저 번호, 매니저 이름을 출력(같은 테이블에서 조회)

```mysql
SELECT e1.empno, e1.ename, e2.empno, e2.ename
FROM emp e1, emp e2
WHERE e1.mgr = e2.empno;
```



INNER JOIN을 사용

```mysql
SELECT e1.empno, e1.ename, e2.empno, e2.ename
FROM emp e1
INNER JOIN emp e2 -- INNER를 쓰지 않아도 동작함
ON e1.mgr = e2.empno;

-- 그런데 매니저 번호와 이름이 없는 사원에 대해서도 출력을 해야하므로 코드를 다음과 같이 수정해야함
SELECT e1.empno, e1.ename, e2.empno, e2.ename
FROM emp e1
LEFT OUTER JOIN emp e2 -- INNER를 쓰지 않아도 동작함
ON e1.mgr = e2.empno;
```



※ BETWEEN ... AND ... 은 이상(>=) 이하(<=)임



----

## Subquery

### 서브 쿼리란?

하나의 SQL문 안에 포함되어 있는 SQL문을 의미한다.

서브 쿼리를 포함하는 SQL을 외부 쿼리(outer query) 또는 메인 쿼리라고 부르며, 서브 쿼리는 내부 쿼리(inner query)라고도 부른다



### 서브 쿼리의 종류

중첩 서브 쿼리(Nested subquery) : WHERE 절에 작성하는 서브 쿼리

인라인 뷰(Inline-view) : FROM 절에 작성하는 서브 쿼리

스칼라 서브 쿼리(Scalar Subquery) : SELECT 문에 작성하는 서브 쿼리



#### 서브 쿼리를 포함할 수 있는 SQL 문

- SELECT, FROM, WHERE, HAVING, ORDER BY
- INSERT문의 VALUES
- UPDATE문의 SET



#### 서브 쿼리의 사용 시 주의사항

서브 쿼리는 반드시 ()로 감싸서 사용한다

서브 쿼리는 단일 행 또는 다중 행 비교 연산자와 함께 사용 가능하다.



서브 쿼리 예시) 사원번호가 7788인 사람의 부서명을 조회함(중첩 쿼리, 단일 행)

```mysql
SELECT dname
FROM dept
WHERE deptno = (SELECT deptno
               FROM emp
               WHERE empno = 7788);
```

- 실행순서
  - 메인 쿼리(외부 쿼리)부터 실행
    - FROM -> WHERE -> WHERE절의 서브 쿼리(FROM -> WHERE -> SELECT) -> SELECT
- `=` : 단일 행 비교 연산자



조인의 단점:

경우에 따라 쿼리가 복잡해지거나 카타시안 곱으로 인해 속도가 느려질 수도 있음



```mysql
-- 1. 매니저 이름이 'KING'인 사원의 사번, 이름, 부서번호, 업무를 조회
SELECT empno, ename, deptno, job
FROM emp
WHERE mgr = (SELECT empno
            FROM emp
            WHERE ename = 'KING');
            
-- 2. 7556번 사원보다 급여를 많이 받는 사원의 이름, 급여를 조회
SELECT ename, sal
FROM emp
WHERE sal > (SELECT sal
              FROM emp
              WHERE empno = 7566);
```



