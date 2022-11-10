package com.group.libraryapp.service.book

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KotlinBookServiceTest @Autowired constructor(
    private val bookService: BookService,
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        userRepository.deleteAll()
        // userLoanHistoryRepository.deleteAll() 로직은 실행하지 않아도 괜찮다.
        // 왜냐하면 User 모델에 OneToMany로 UserLoanHistory를 갖고 있고,
        // deleteAll()은 자식 테이블까지 찾아서 제거해주기 때문에 userLoanHistoryRepository.deleteAll()을 실행하지 않아도 된다.
    }


    @Test
    @DisplayName("책 등록이 정상 동작한다.")
    fun saveBookTest() {
        // given
        val request = BookRequest("어린 왕자", "컴퓨터")

        // when
        bookService.saveBook(request)

        // then
        val books = bookRepository.findAll()
        assertThat(books).hasSize(1)
        assertThat(books[0].name).isEqualTo("어린 왕자")
        assertThat(books[0].type).isEqualTo("컴퓨터")
    }

    @Test
    @DisplayName("책 대출이 정상 작동한다.")
    fun loadBookTest() {
        // given
        bookRepository.save(Book.fixture("어린 왕자"))
        val savedUser = userRepository.save(User("박시원", 30))
        val request = BookLoanRequest("박시원", "어린 왕자")

        // when
        bookService.loanBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("어린 왕자")
        assertThat(results[0].user.id).isEqualTo(savedUser.id)
        assertThat(results[0].isReturn).isFalse // isFalse 대신 isFalse()를 사용할 수도 있음
    }

    @Test
    @DisplayName("책이 이미 대출되어 있다면, 신규 대출이 실패한다.")
    fun loanBookFailTest() {
        // given
        bookRepository.save(Book.fixture("어린 왕자"))
        val savedUser = userRepository.save(User("박시원", 30))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "어린 왕자", false))
        val request = BookLoanRequest("박시원", "어린 왕자")

        // when & then
        assertThrows<IllegalArgumentException> {
            bookService.loanBook(request)
        }.apply {
            assertThat(message).isEqualTo("진작 대출되어 있는 책입니다")
        }
    }

    @Test
    @DisplayName("책 반납이 정상 작동한다")
    fun returnBookTest() {
        // given
        bookRepository.save(Book.fixture("어린 왕자"))
        val savedUser = userRepository.save(User("박시원", 30))
        userLoanHistoryRepository.save(UserLoanHistory(savedUser, "어린 왕자", true))
        val request = BookReturnRequest("박시원", "어린 왕자")

        // when
        bookService.returnBook(request)

        // then
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].isReturn).isTrue // isTrue 대신 isTrue()를 사용할 수도 있음
    }

}