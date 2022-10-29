package com.lannstark.lec06

class Lec06MainKt {
}

fun main() {

    val numbers = listOf<Long>(1L, 2L, 3L)
    for (number in numbers) {
        println(number)
    }

    for (i in 1..3) {
        println(i)
    }

    for (i in 5 downTo 1) {
        println(i)
    }

    for (i in 1..5 step 2) {
        println(i)
    }

    var i = 1 // while문 안에서 i가 변경되어야 하므로 var로 선언!
    while (i <= 7) {
        println(i)
        i ++
    }

}