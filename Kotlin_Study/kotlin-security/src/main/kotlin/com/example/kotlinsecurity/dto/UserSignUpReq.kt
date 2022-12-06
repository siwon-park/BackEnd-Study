package com.example.kotlinsecurity.dto

data class UserSignUpReq(
    val phone: String,
    val name: String,
    val password: String,
) {

}