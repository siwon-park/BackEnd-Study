package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.type.UserLoanStatus
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService (
    private val userRepository: UserRepository,
){

    @Transactional
    fun saveUser(request: UserCreateRequest) {
        val newUser = User(request.name, request.age)
        userRepository.save(newUser)
    }

    @Transactional(readOnly = true)
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll()
            .map { user -> UserResponse.of(user) }
    }

    @Transactional
    fun updateUserName(request: UserUpdateRequest) {
        //val user = userRepository.findById(request.id).orElseThrow(::IllegalArgumentException)
        // findById와 같이 기본적으로 Optinal을 반환하는 것에 대해서는 우리가 컨트롤할 수 없지만
        // 코틀린에서 확장함수를 사용하면 이를 해결할 수 있다.
        // val user = userRepository.findByIdOrNull(request.id) ?: fail()
     val user = userRepository.findByIdOrThrow(request.id) // 확장함수를 사용하여 더욱 간략화
        user.updateName(request.name)
    }

    @Transactional
    fun deleteUser(name: String) {
        // val user = userRepository.findByName(name).orElseThrow(::IllegalArgumentException)
        // repository에서 Optional을 사용했다면 orElseThrow를 쓸 수 있지만, 타입? 로 바꿨다면 elvis 연산자를 써야함
        // val user = userRepository.findByName(name) ?: throw IllegalArgumentException()
        val user = userRepository.findByName(name) ?: fail()
        userRepository.delete(user)
    }

    @Transactional(readOnly = true) // n + 1 문제가 발생한다
    fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
        return userRepository.findAll().map { user ->
            UserLoanHistoryResponse(
                name = user.name,
                books = user.userLoanHistories.map { history ->
                    BookHistoryResponse(
                        name = history.bookName,
                        isReturn = history.status == UserLoanStatus.RETURNED
                    )
                }
            )
        }
    }


    @Transactional(readOnly = true) // n + 1 문제가 발생한다
    fun getUserLoanHistoriesImproved(): List<UserLoanHistoryResponse> {
        return userRepository.findAllWithHistories().map { user ->
            UserLoanHistoryResponse.of(user) // .map(UserLoanHistoryResponse::of)
//            UserLoanHistoryResponse(
//                name = user.name,
//                books = user.userLoanHistories.map { history ->
//                    BookHistoryResponse.of(history)
//                } // books = user.userLoanHistories.map(BookHistoryResponse::of)
//            )
        }
    }
}