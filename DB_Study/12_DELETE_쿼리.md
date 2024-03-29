# 12_DELETE 쿼리

## 1. HARD DELETE vs. SOFT DELETE

DB에서 데이터를 삭제하는 방법은 크게 `HARD DELETE(물리적 삭제)`와 `SOFT DELETE(논리적 삭제)`가 있다.

### HARD DELETE(물리적 삭제)

SQL의 `DELETE` 명령어를 사용하여 직접적으로 데이터를 DB에서 삭제하는 방법

삭제 대상 데이터가 더 이상 완전히 필요 없을 경우에 사용함

### SOFT DELETE(논리적 삭제)

SQL의 `update` 명령어를 사용하여 필드 값을 해당 데이터가 삭제되었음을 판별할 수 있는 값으로 변경하여 데이터가 삭제되었음을 표시하는 방식으로 삭제하는 방법

실제 삭제가 이루어지지 않음

삭제를 하더라도 해당 데이터가 차후에 용도가 있어서 보관을 해야할 경우에 사용함

<br>

## 2. DELETE 쿼리를 사용하지 않는 이유?

현업에서는 DELETE 쿼리를 잘 사용하지 않는다.

예를 들어, 회원 탈퇴를 구현하더라도 실제 DB에 삭제 쿼리를 날리는 것이 아니라, 회원 칼럼의 status를 바꿔 놓는  UPDATE 쿼리를 날린다.

그 이유는 무엇일까?

### 이유

- DB에서 데이터를 삭제하는 것은 간단하다. 하지만 삭제한 해당 데이터를 복구하는 것은 백업이 없는 이상 거의 불가능하다.

- 저장된 데이터를 활용하기 위해서이다. 
  - 예를 들어, 회원 탈퇴를 했다고 해서 DB에서 삭제를 한다면, 탈퇴회원에 대한 통계(탈퇴 회원의 나이대, 성별 등의 데이터 통계)는 어떻게 구할 것인가? DB에서 삭제하지 않고 계속 저장해둔다면, 사용 가능한 정보로 가공이 가능해진다.
  - 또한 흔히 장바구니에 넣는 데이터는 쿠키를 사용하는 것으로 알려져 있어, 쿠키를 삭제하면 장바구니에 담아뒀던 데이터가 사라진다고 알고 있다. 그러나 이는 비회원에 해당하는 것이다. 커머스 회사에서는 회원의 장바구니 데이터도 DB에 저장하기도 한다. 그 이유는 바로 빅데이터로 활용하기 위함이다.
    - 고객들이 어느 시간대에 어떤 제품을 장바구니에 많이 담는지, 얼마나 오래 장바구니에 제품을 담아 놓는지 등 장바구니 데이터는 실제로 엄청난 활용성이 있다.