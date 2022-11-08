package com.group.libraryapp.domain.book

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Book(
    val name: String, // 코틀린에서 getter는 기본적으로 생성

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null // 디폴트 파라미터는 가장 마지막에 있는 것이 관례
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("이름은 비어 있을 수 없습니다.")
        }
    }


}