package com.example.kotlinsecurity.controller

import com.example.kotlinsecurity.service.UserService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
) {

}