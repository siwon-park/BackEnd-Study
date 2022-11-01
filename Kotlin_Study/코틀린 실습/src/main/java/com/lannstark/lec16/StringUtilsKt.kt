package com.lannstark.lec16

class StringUtilsKt {
}

fun main() {
    val str = "ABC"
    println(str.lastChar())
    
}

// 확장 함수 => String 클래스를 확장함
fun String.lastChar(): Char {
    return this[this.length - 1]
}