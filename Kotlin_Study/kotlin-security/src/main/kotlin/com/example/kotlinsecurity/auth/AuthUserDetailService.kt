package com.example.kotlinsecurity.auth

import com.example.kotlinsecurity.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AuthUserDetailService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(phone: String): UserDetails? {
        val user = userRepository.findByPhone(phone)
        if (user != null) {
            return AuthUserDetails(user)
        }
        return null
    }

}