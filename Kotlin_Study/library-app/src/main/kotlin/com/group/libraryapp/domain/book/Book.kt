package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Book(
    val name: String, // 코틀린에서 getter는 기본적으로 생성
    
    val type: String, // 책의 분야

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
            type: String = "컴퓨터",
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