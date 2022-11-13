package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import com.group.libraryapp.dto.book.response.BookStatResponse
import com.group.libraryapp.type.UserLoanStatus
import com.group.libraryapp.util.fail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService (
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @Transactional
    fun saveBook(request: BookRequest) {
        val book = Book(request.name, request.type)
        bookRepository.save(book)
    }

    @Transactional
    fun loanBook(request: BookLoanRequest) {
        // val book = bookRepository.findByName(request.bookName).orElseThrow(::IllegalArgumentException)
        val book = bookRepository.findByName(request.bookName) ?: fail()
        if (userLoanHistoryRepository.findByBookNameAndStatus(request.bookName, UserLoanStatus.LOANED) != null) {
            throw IllegalArgumentException("진작 대출되어 있는 책입니다")
        }

        val user = userRepository.findByName(request.userName) ?: fail()
        user.loanBook(book)
    }

    @Transactional
    fun returnBook(request: BookReturnRequest) {
        val user = userRepository.findByName(request.userName) ?: fail()
        user.returnBook(request.bookName)
    }

    @Transactional(readOnly = true)
    fun countLoanedBooks(): Int {
        // return userLoanHistoryRepository.findAllByStatus(UserLoanStatus.LOANED).size
        return userLoanHistoryRepository.countByStatus(UserLoanStatus.LOANED).toInt()
    }

    @Transactional(readOnly = true)
    fun getBookStatistics(): List<BookStatResponse> {
        return bookRepository.getStats()
//        return bookRepository.findAll() // List<Book>
//            .groupBy { book -> book.type } // Map<BookType, List<Book>>
//            .map { (type, books) -> BookStatResponse(type, books.size.toLong()) } // List<BookStatResponse>

//        val results = mutableListOf<BookStatResponse>() // 통계 결과를 반환할 빈 리스트
//        val books = bookRepository.findAll() // 책들을 가져옴
//        for (book in books) { // 책들을 반복하면서 책의 종류와 dto의 타입이 일치하는 것을 찾음
//            results.firstOrNull { dto -> dto.type == book.type }?.plusOne()
//                ?: results.add(BookStatResponse(book.type, 1))
////            val targetDto = results.firstOrNull { dto -> dto.type == book.type }
////            if (targetDto == null) { // 만약 해당하는 Dto가 없다면 최초 등장이므로 dto를 생성하여 등장횟수 1로 넣음
////                results.add(BookStatResponse(book.type, 1))
////            } else { // 이미 등장했다면 등장횟수를 증가시킴
////                targetDto.plusOne()
////            }
//        }
//        return results
    }

}