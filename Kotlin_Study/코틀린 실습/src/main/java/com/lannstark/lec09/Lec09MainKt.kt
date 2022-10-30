package com.lannstark.lec09

class Lec09MainKt {
}

    class Person(
        name: String = "박시원",
        var age: Int = 1) {

        var name = name
            set(value) {
                field = value.uppercase()
            }

//        val uppercaseName: String
//            get() = this.name.uppercase() // getter 를 호출할 경우 대문자 호출

//        fun getUppercaseName(): String {
//            return this.name.uppercase()
//        }




        val isAdult: Boolean
            get() {
                return this.age >= 20
            }

//        val isAdult: Boolean = this.age >= 20

//        fun isAdult(): Boolean {
//            return this.age >= 20
//        }




    }

class Car


fun main() {

    val person = Person("시원", 30)
    println(person.name) // 시원
    println(person.age) // 30

    person.age = 29
    println(person.age) // 29

    val car = Car()

    println(Person().isAdult)
}
