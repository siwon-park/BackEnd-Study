package com.lannstark.lec20

class Lec20MainKt {
}

// 20번째 강의가 해당 내용을 다루지 않지만, 부 생성자에 대해 혼동이 와서 예시 코드를 작성해서 연습해봄

class User(val name: String, val age: Int) {
    var hobby: String = "과학" // 반드시 초기화 필요("" 빈 문자열이어도 가능)

    init {
        if (age < 0) {
            throw IllegalArgumentException("나이는 음수가 될 수 없습니다.")
        }
    }

    constructor(name: String, age: Int, hobby:String): this(name, age) {
        this.hobby = hobby
        println("이건 부생성자고, 제 취미는 " + hobby + "입니다.")
    }
}

fun main() {
    val user: User = User("시원", 30)
    println(user.name) // 시원
    println(user.hobby) // 과학

    val user1 = User("시원", 30, "코딩")
    println(user1.hobby) // 코딩

}