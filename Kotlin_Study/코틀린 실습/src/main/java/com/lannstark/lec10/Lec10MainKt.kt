package com.lannstark.lec10

class Lec10MainKt {
}

fun main() {
    Derived(300)
}

open class Base(open val number: Int = 100) {
    init {
        println("Base 클래스")
        println("number: ${number}")
    }
}

class Derived(override val number: Int) : Base(number) {
    init {
        println("Derived 클래스")
    }
}
