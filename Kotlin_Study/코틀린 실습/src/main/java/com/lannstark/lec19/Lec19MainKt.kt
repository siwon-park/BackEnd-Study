package com.lannstark.lec19

import com.lannstark.lec19.a.printHelloWorld as printHelloWorldA
import com.lannstark.lec19.b.printHelloWorld as printHelloWorldB


class Lec19MainKt {
}

    class Person(
        val name: String,
        val age: Int,
    ) {
        operator fun component1(): String {
            return this.name
        }

        operator fun component2(): Int {
            return this.age
        }
    }


fun main() {

    val person = Person("박시원", 30)
    val (name, age) = person
//        val name = person.component1()
//        val age = person.component2()
    println("이름: ${name}, 나이: ${age}")

//    printHelloWorldA()
//    printHelloWorldB()

    loop@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 30) {
                break@loop
            }
            println(j)
        }
    }

    val numbers = listOf(1, 2, 3, 4, 5)
    numbers.forEach {number ->
        println(number)
        if (number == 3) {
            return@forEach
        }
    }


//    run {
//        numbers.forEach { number ->
//            if (number == 3) {
//                return@run
//            }
//            println(number)
//        }
//    }




    fun getNumberOrNullV1(number: Int): Int? {
        return if (number <= 0) {
            null
        } else {
            number
        }
    }

    fun getNumberOrNullV2(number: Int): Int? {
        // number가 0 초과일 경우에만 그대로 반환, 아닐 경우 null
        return number.takeIf { number > 0 }
    }

    fun getNumberOrNullV3(number: Int): Int? {
        return number.takeUnless { number <= 0 }
    }

    println(getNumberOrNullV1(0)) // null
    println(getNumberOrNullV2(1)) // 1
    println(getNumberOrNullV2(0)) // null
    println(getNumberOrNullV3(-1)) // null
    println(getNumberOrNullV3(1)) // 1

}

