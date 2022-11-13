package com.group.libraryapp.domain.user

import com.group.libraryapp.domain.user.QUser.user
import com.group.libraryapp.domain.user.loanhistory.QUserLoanHistory.userLoanHistory
import com.querydsl.jpa.impl.JPAQueryFactory

class UserRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : UserRepositoryCustom {

    override fun findAllWithHistories(): List<User> {
        return queryFactory.select(user)
            .distinct()
            .from(user)
            .leftJoin(userLoanHistory)
            .on(userLoanHistory.user.id.eq(user.id))
            .fetchJoin()
            .fetch() // fetch(): 전체 쿼리를 실행시켜서 결과를 가져오게함
    }


}