package com.example.kotlinsecurity.controller

import com.example.kotlinsecurity.dto.UserLoginReq
import com.example.kotlinsecurity.dto.UserLoginRes
import com.example.kotlinsecurity.dto.UserSignUpReq
import com.example.kotlinsecurity.service.UserService
import com.example.kotlinsecurity.util.JwtTokenProvider
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
class UserController(
    private val userService: UserService,
    private val jwtTokenProvider: JwtTokenProvider,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @PostMapping("/signup")
    fun signup(@RequestBody userSignUpReq: UserSignUpReq): String {
        try {
            if (userService.createUser(userSignUpReq)) {
                return "회원가입 성공"
            } else {
                return "회원가입 실패"
            }
        } catch (e: Exception) {
            return "에러"
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody userLoginReq: UserLoginReq): UserLoginRes? {
        val phone = userLoginReq.phone
        val password = userLoginReq.password
        val user = userService.getUserByPhone(phone)

        if (user == null) {
            return null
        }

        if (bCryptPasswordEncoder.matches(password, user.password)) {
            val accessToken = jwtTokenProvider.createAccessToken(phone)
            val refreshToken = jwtTokenProvider.createRefreshToken(phone)
            return UserLoginRes(accessToken, refreshToken)
        }

        return UserLoginRes("잘못된 비밀번호입니다", "잘못된 비밀번호입니다.")
    }

    @GetMapping("/admintest")
    fun admintest(): String {
        return "올바른 ADMIN 권한을 가진 유저입니다."
    }

    @GetMapping("/membertest")
    fun membertest(): String {
        return "올바른 member 권한을 가진 유저입니다."
    }


}