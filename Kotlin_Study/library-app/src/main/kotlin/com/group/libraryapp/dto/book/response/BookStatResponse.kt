package com.group.libraryapp.dto.book.response

import com.group.libraryapp.type.BookType

data class BookStatResponse(
    val type: BookType,
    var count: Long, // 쿼리의 count 결과는 항상 Long이기 때문에 Long으로 선언
) {
                    // 사실 count를 var로 하고 plusOne 함수를 만드는 것은 좋지 못함
    fun plusOne() { // 왜냐하면, dto를 사용하면서 어디선가 count값이 언제든지 바뀔 수 있기 때문
        count++
    }
}