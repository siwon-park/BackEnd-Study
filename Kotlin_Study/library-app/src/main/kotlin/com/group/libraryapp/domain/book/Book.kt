package com.group.libraryapp.domain.book

import com.group.libraryapp.type.BookType
import javax.persistence.*

@Entity
class Book(
    val name: String, // 코틀린에서 getter는 기본적으로 생성

    @Enumerated(EnumType.STRING) // DB에 문자열로 넣기 위한 어노테이션
    val type: BookType, // 책의 분야(enum class 활용)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null // 디폴트 파라미터는 가장 마지막에 있는 것이 관례
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다.")
        }
    }
    
    // 코틀린에서는 컴패니언 오브젝트를 가장 아래 작성하는 것이 컨벤션
    companion object {
        fun fixture(
            name: String = "책 이름",
            type: BookType = BookType.COMPUTER,
            id: Long? = null,
        ): Book {
            return Book(
                name = name,
                type = type,
                id = id,
            )
        }
    }


}