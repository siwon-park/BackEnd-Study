# 14_프로시저(Procedure)

## 1. 프로시저란?

> 일련의 쿼리를 하나의 **함수(Function)**처럼 실행하기 위한 쿼리들의 집합
>
> 매개 변수를 받을 수 있고 반복적으로 사용 가능한 블럭

일반적으로 저장(store)해서 함수처럼 호출해서 사용하기 때문에 `저장 프로시저(Stored Procedure)`라고 부른다.

<br>

## 2. 프로시저의 장점

- 하나의 요청으로 여러 SQL문을 실행할 수 있기 때문에 네트워크 부하를 줄일 수 있음
  - 요청을 여러 번 수행하는 것은 아무래도 네트워크 부하가 한 번 요청하는 것보다 많을 수밖에 없음
- 안전성
  - 기본 데이터 베이스 테이블에 접근할 수 있는 권한을 따로 부여하지 않고 응용 프로그램이 저장 프로시저에 접근할 수 있는 권한을 부여하는 방식으로 사용

<br>

## 3. 프로시저의 단점

- 연결된 프로시저들이 많을 수록 메모리 사용량과 CPU 사용량이 증가함
- 디버깅 및 개발, 유지보수가 쉽지 않음

<br>

## 4. 프로시저의 생성 방법

```SQL
DELIMITER $$

CREATE PROCEDURE '프로시저명' (
    IN 파라미터명 데이터 타입,
    IN 파라미터명 데이터 타입,
    OUT 파라미터명 반환 데이터 타입
)
BEGIN
    DECLARE 변수명 VARCHAR (45) DEFAULT NULL;
    
    수행할 쿼리
    ...
    
END $$
DELIMITER ;
```

### DELIMITER

저장 프로시저의 시작과 끝을 알리는 구분자 역할을 함

사용 이유?

- 프로시저 내부에서 사용하는 SQL문도 일반 SQL문이기 때문에 세미콜론(`;`)으로 문장을 끝맺어야 한다. 그런데 아무것도 없이 세미콜론으로 끝내버린다면 저장 프로시저의 작성이 완료되지 않았음에도 불구하고 SQL문이 실행되는 일이 발생한다.
- 따라서 프로시저 생성 전에 구분자 `DELIMITER`를 `$$`로 바꿔주고 프로시저의 끝에 `END $$`로 작성이 끝났음을 알려준다. 그 후 구분자를 원래대로 되돌리기 위해 `DELIMITER ;`으로 작성한다.

### IN

프로시저에서 받을 파라미터가 있을 경우 `IN`을 사용하여 받을 수 있다.

### OUT

프로시저에서 반환할 결과값이 있을 경우 해당 결과값의 타입을 `OUT`으로 명시할 수 있다.

### DECLARE

수행할 쿼리를 작성하기 전에 선언할 변수가 있다면 `DECLARE`로 변수명과 내용을 정의 가능하다.

<br>

## 5. 프로시저 작성 예시

- 답변 테이블에서 원본 글인지 답변 글인지 판별하고, 답변 여부에 따라 삭제 및 업데이트 처리

```SQL
DELIMITER $$ 
DROP PROCEDURE IF EXISTS deleteReboard $$ #같은 이름이 있다면 지우기
CREATE PROCEDURE deleteReboard #저장 프로시저 생성 
( 
	#변수 선언 
    m_no INT, 
    m_step INT, 
    m_groupNo INT 
) 
BEGIN #SQL 프로그래밍 부분 시작 
DECLARE cnt INT; #답변 변수 설정 
SET cnt=0; #변수 초기화 
/*답변이 달린 원본 글인 경우에는 삭제하지 말고 delFlag를 Y 로 update하자*/ 
IF m_step=0 THEN /*원본글인 경우*/ 
  /*답변이 달렸는지 확인*/ 
  SELECT COUNT(*) INTO cnt FROM reboard WHERE groupno=m_groupNo; 
  IF cnt > 1 THEN /*답변이 달린 경우*/ 
    UPDATE reboard SET delflag='Y' WHERE NO=m_no; 
  ELSE /*답변이 안 달린 경우*/ 
      DELETE FROM reboard WHERE NO=m_no; 
  END IF; 
ELSE /*답변글인 경우*/ 
	DELETE FROM reboard WHERE NO=m_no; 
END IF; 
END$$ 
DELIMITER ;
```

- 프로시저 호출

```SQL
CALL deleteReboard(4, 0, 4);
```

- 프로시저 내용 확인

```SQL
SHOW CREATE PROCEDURE [저장 프로시저 이름];
```

- 저장 프로시저 삭제

```SQL
DROP PROCEDURE [저장 프로시저 이름];
```

<br>

- Book 테이블에 책 정보를 넣고, 책의 종류에 따라 판매가를 설정하여 행 추가 삽입

```SQL
DELIMITER $$
CREATE PROCEDURE INSERT_BOOK
(  IN _BOOKNAME VARCHAR(20), /* 전달 받을 파라미터 */
   _BOOKPRICE DOUBLE,        /* IN이 없어도 파라미터 전달 받을 수 있음 */
   _BOOKTYPE VARCHAR(10),    
   OUT RESULT INT            /* 결과를 반환할 파라미터  */
 )
 BEGIN 
   
   /* 변수 선언 */
   DECLARE SELL_PRICE DOUBLE;
   DECLARE INSERTID INT;
   
   /* SQL에러 발생시 ROLLBACK */
   DECLARE EXIT HANDLER FOR SQLEXCEPTION
     BEGIN
        ROLLBACK;
        SET RESULT = -1;
     END;
     
     /* 트랜잭션 시작 */
     START TRANSACTION;
     
     /* books table에 insert */
     INSERT INTO books(bookName, bookPrice, bookType) VALUE(_BOOKNAME, _BOOKPRICE, _BOOKTYPE);
     
     /* books table에 insert한 key 반환 */
     SET INSERTID = LAST_INSERT_ID(); 
     
     IF _BOOKTYPE = 'novel' THEN
       SET SELL_PRICE = _BOOKPRICE + _BOOKPRICE * (10/100);
     ELSEIF _BOOKTYPE = 'art' THEN
       SET SELL_PRICE = _BOOKPRICE + _BOOKPRICE * (15/100);
     ELSE
       SET SELL_PRICE = _BOOKPRICE + _BOOKPRICE * (20/100);
     END IF;
     
     INSERT INTO books_sell(bookCode, bookSellPrice, bookType) VALUE(INSERTID, SELL_PRICE, _BOOKTYPE);
   
   /* 커밋 */
   COMMIT;
   
   /* 잘되면 1 반환 */
   SET RESULT = 1;
   
END $$
DELIMITER ;
```

