package com.lannstark.lec15

class CollectionKt {
}

fun main() {
    
    // 리스트
    val numbers = listOf(100, 300) //  불변 리스트
    val numbers2 = mutableListOf(100, 200) // 가변 리스트; 타입을 추론할 수 있어 생략 가능
    val emptyList = emptyList<Int>() // emptyList는 타입을 반드시 지정해줘야함

    printNumber(emptyList()) // 단, 추론 가능할 경우는 생략 가능

    numbers.get(0) // 0번째 인덱스의 숫자를 가져옴
    println(numbers[0])

    for (number in numbers) {
        println(number)
    }

    // 집합
    val numberSet1 = setOf<Int>(100, 200) // 불변 집합
    val numberSet2 = mutableSetOf<Int>(100, 200) // 가변 집합

    for (number in numberSet1) {
        println(number)
    }

    for ((idx, num) in numberSet1.withIndex()) {
        println("${idx} ${num}")
    }

    // 맵
    val oldMap = mutableMapOf<Int, String>() // 가변 맵
    oldMap[1] = "월요일"
    oldMap[2] = "월요일"

    val map = mapOf(1 to "월요일", 2 to "화요일") // 불변 맵

    // 반복문
    for (key in oldMap.keys) {
        println(key)
        println(oldMap[key])
    }

    for ((key, value) in oldMap.entries) {
        println(key)
        println(value)
    }


}

private fun printNumber(numbers: List<Int>) {

}