package com.example.kotlinsecurity.service

import com.example.kotlinsecurity.domain.User
import com.example.kotlinsecurity.dto.UserSignUpReq
import com.example.kotlinsecurity.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {
    fun createUser(userSignUpReq: UserSignUpReq): Boolean {
        if (userRepository.findByPhone(userSignUpReq.phone) != null) { // 이미 해당 번호로 가입한 유저가 있으면 false
            return false
        }

        val newUser = User(
            name = userSignUpReq.name,
            phone = userSignUpReq.phone,
            password = bCryptPasswordEncoder.encode(userSignUpReq.password),
            role = "MEMBER"
        )

        userRepository.save(newUser)
        return true

    }

    fun getUserByPhone(phone: String): User? {
        return userRepository.findByPhone(phone)
    }

}