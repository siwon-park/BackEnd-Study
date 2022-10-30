package com.lannstark.lec08

class Lec08MainKt {
}

fun main() {
    println(maxV1(1, 3))
    println(maxV2(4, 2))
    println(maxV3(5, 8))
    repeat("Hello!")


    printNameAndGender(name = "박시원", gender = "남자")
    printNameAndGender(gender = "남자", name = "시원박")

    printAll("A", "B", "C")

    val arr = arrayOf("D", "E", "F")
    printAll(*arr) // 배열을 직접 넣을 때는 *을 써줘야 한다.

}

// block 사용
fun maxV1(a: Int, b: Int): Int {
    return if (a > b) {
        a
    } else {
        b
    }
}

// = 사용
fun maxV2(a: Int, b: Int) =
    if (a > b) {
        a
    } else {
        b
    }

// = 사용, 축약 버전
fun maxV3(a: Int, b: Int) = if (a > b) a else b

// 기본 인자 사용 함수
fun repeat(str: String, num: Int = 3, useNewLine: Boolean = true) {
    for (i in 1..num) {
        if (useNewLine) {
            println(str)
        } else {
            print(str)
        }
    }
}

fun printNameAndGender(name: String, gender: String) {
    println(name)
    println(gender)
}

    fun printAll(vararg strings: String) {
        for (str in strings) {
            println(str)
        }
    }