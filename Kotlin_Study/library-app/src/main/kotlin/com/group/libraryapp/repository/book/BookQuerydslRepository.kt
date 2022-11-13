package com.group.libraryapp.repository.book

import com.group.libraryapp.domain.book.QBook.book
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class BookQuerydslRepository(
    private val queryFactory: JPAQueryFactory,
) {

    fun getStats(): List<BookStatResponse> {
        return queryFactory.select(
            Projections.constructor( // 주어진 DTO의 생성자를 호출한다는 의미
            BookStatResponse::class.java, // DTO
            book.type, // DTO의 파라미터들
            book.id.count(),
        ))
            .from(book) // select type, count(book.id) from book;
            .groupBy(book.type) // select type, count(book.id) from book group by type;
            .fetch()
    }

}