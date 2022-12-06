package com.example.kotlinsecurity.dto

data class UserLoginRes(
    val accessToken: String,
    val refreshToken: String,
) {

//    fun of(statusCode: Int, message: String, accessToken: String, refreshToken: String): UserLoginRes {
//
//    }
}