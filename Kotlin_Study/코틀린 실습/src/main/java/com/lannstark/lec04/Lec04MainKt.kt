package com.lannstark.lec04

class Lec04MainKt {
}

fun main() {
    val money1 = JavaMoney(2000);
    val money2 = JavaMoney(3000);

    if (money1 > money2) {
        println("money1이 더 큽니다.")
    } else {
        println("money2가 더 큽니다.")
    }
}