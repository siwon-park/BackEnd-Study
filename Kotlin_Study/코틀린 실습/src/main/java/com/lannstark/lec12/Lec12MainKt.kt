package com.lannstark.lec12

class Lec12MainKt {
}

fun main() {
    println(Singleton.a) // 0
    Singleton.a += 1
    println(Singleton.a) // 1
}


class Person private constructor(
    var name: String,
    var age: Int,
) {
    companion object Factory : Log { // 이름을 붙이거나 인터페이스를 구현 가능
        private const val MIN_AGE = 1 // const 를 붙이면 컴파일 시 변수가 할당됨(붙이지 않으면 런타임 시 할당)
        @JvmStatic // 자바에서 코틀린의 companion object 를 사용할 수 있게 함
        fun newBaby(name: String): Person {
            return Person(name, MIN_AGE)
        }

        override fun log() {
            println("나는 Person 클래스의 동행 객체 Factory 입니다.")
        }
    }
}

object Singleton {
    var a: Int = 0
}

