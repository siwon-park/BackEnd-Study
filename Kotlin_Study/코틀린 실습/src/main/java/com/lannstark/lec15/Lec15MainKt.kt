package com.lannstark.lec15

class Lec15MainKt {
}

fun main() {

    val array = arrayOf(100, 200)

    for (i in array.indices) {
        println(i) // 인덱스
    }

    for ((idx, value) in array.withIndex()) {
        println("${idx} ${value}")
    }

    array.plus(300) // 배열에 요소 추가

    

}