package com.group.libraryapp.repository.user.loanhistory

import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.type.UserLoanStatus
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class UserLoanHistoryQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    // 원래 findByBookName을 find로 간결화 가능 + 필드를 동적으로 전달하여 쿼리문을 사용 가능
    fun find(bookName: String, status: UserLoanStatus? = null): UserLoanHistory? {
        return queryFactory.select(userLoanHistory)
            .from(userLoanHistory)
            .where(
                userLoanHistory.bookName.eq(bookName), // where절 안에 콤마(,)를 쓰면 조건들이 AND로 연결되는 것과 같음
                status?.let { userLoanHistory.status.eq(status)} // status가 null이면 이 구문은 동작하지 않음(무시)
            )
            .limit(1) // 가져올 개수를 선정 sql의 limit과 유사
            .fetchOne() // 1개를 가져오기로 했으므로, fetchOne을 사용 => Entity 하나만을 return
    }

    fun count(status: UserLoanStatus): Long {
        return queryFactory.select(userLoanHistory.id.count())
            .from(userLoanHistory)
            .where(
                userLoanHistory.status.eq(status)
            )
            .fetchOne() ?: 0L // fetchOne을 쓰면 결과가 null인 경우도 있으므로 elvis 연산자를 활용하여 null에 대해 처리해줌
    }

}