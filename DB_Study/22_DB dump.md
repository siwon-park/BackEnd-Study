# 22_DB dump

## 1. DB dump의 개념과 목적

### 1) 개념

DB의 구조와 테이블의 데이터를 특정 시점을 기준으로 저장하기 위해 DB의 데이터를 파일로 내보내는 과정.

dump 과정에서 만들어진 파일을 dump file(덤프 파일)이라고 한다.

덤프 파일에는 DB의 테이블 구조, 인덱스, 스키마, 데이터 등을 포함할 수 있다.

<br>

### 2) 목적

#### (1) 백업 및 롤백

주기적으로 백업하여 데이터 손실에 대해 대비하는 목적과 문제가 생겼을 때 특정 시점으로 롤백하기 위한 목적이 있다.

#### (2) 데이터 이전

다른 서버나 환경으로 DB와 데이터를 이동하고자할 때 덤프 파일을 활용하여 데이터를 손쉽게 이전할 수 있다.

#### (3) 복제 및 테스트

개발 및 테스트 환경에서 운영 서버의 DB 환경 및 데이터와 동일한 상태에서 활용과 테스트가 가능하다.

<br>

## 2. dump 방법

### 1) MySQL / Maria DB

#### (1) 하나의 DB에 대한 dump

```bash
mysqldump -u [username] -p[password] [db_name] > [dump_file_name].sql
```

#### (2) 서버 내 모든 DB에 대한 dump

```bash
mysqldump -u [username] -p[password] --all-databases > [dump_file_name].sql
```

#### (3) DB의 특정 테이블만 dump

```bash
mysqldump -u [username] -p[passowrd] [db_name] [table_name] > [dump_file_name].sql
```

#### (4) dump 시 유의 사항

- mysql에서 -p와 패스워드는 붙여서 입력해야 한다. 그렇지 않으면 명령어 입력 후 패스워드를 입력하는 프롬프트 창이 나온다.
- 덤프 시 추가 옵션으로
  - `--no-data`: 데이터를 제외하고 스키마만 덤프
  - `--single-transaction`: 덤프가 트랜잭션 내에서 이루어지며, 트랜잭션 데이터의 일관성을 유지하기 위해 사용한다.
  - `--quick`: 메모리를 적게 사용하기 위해 데이터를 줄 단위로 읽게한다.

#### (5) dump 파일을 사용하여 밀어넣기/복원

```bash
mysql -u [username] -p[password] [db_name] < [dump_file_name].sql
```

덤프 파일을 DB에 밀어넣는 명령어로, DB에 현재 데이터가 있으면 dump 파일 기준으로 밀어넣어지기 때문에 반드시 유의해야 한다.

<br>

### 2) PostgreSQL

PostgreSQL은 dump 시 pg_dump, pg_dumpall이라는 명령어를 사용한다.

#### (1) 하나의 DB에 대한 dump

```bash
pg_dump -U [username] -W -F p -d [db_name] > [dump_file_name].sql
```

- `-W`: 비밀번호 입력 요청
- `-F`: 포맷 설정
  - `p`: 일반 텍스트 SQL 형식

#### (2) 서버 내 모든 DB에 대한 dump

서버 내 모든 DB에 대한 dump를 하려면 pg_dumpall 명령어를 사용해야 한다.

```bash
pg_dumpall -U [username] -W > [dump_file_name].sql
```

#### (3) DB의 특정 테이블만 dump

```bash
pg_dump -U [username] -W -d [db_name] -t [schema_name].[table_name] > [dump_file_name].sql
```

- 보통 일반적으로 스키마 이름은 생략되는 경우가 많다.
- 추가적인 테이블을 dump 뜨려면 `-t`를 사용하여 테이블 이름을 추가 명시해주면 된다.

#### (4) dump 파일을 사용하여 밀어넣기/복원

```bash
psql -U [username] [db_name] < [dump_file_name].sql
```