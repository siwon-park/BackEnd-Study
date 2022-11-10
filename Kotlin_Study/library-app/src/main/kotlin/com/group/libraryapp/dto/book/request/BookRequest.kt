package com.group.libraryapp.dto.book.request

import com.group.libraryapp.type.BookType

data class BookRequest(
    val name: String,
    val type: BookType, // 요청이 들어올 때 BookType 내의 값인지 확인함
)