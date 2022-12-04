package com.example.kotlinsecurity.repository

import com.example.kotlinsecurity.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): User?
    fun findByPhone(phone: String): User?
}
