package com.lannstark.lec16

class Lec16MainKt {
}

fun main() {

    3.add(4)
    3.add2(4)
    3 add2 4

    thisFun()
}

fun Int.add(other: Int): Int {
    return this + other
}

infix fun Int.add2(other: Int): Int {
    return this + other
}

fun thisFun() {
    fun funInFun() {
        println("나는 지역 함수야")
    }

    funInFun()
}