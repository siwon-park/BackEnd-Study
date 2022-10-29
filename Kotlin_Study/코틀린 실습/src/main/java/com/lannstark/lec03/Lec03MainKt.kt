package com.lannstark.lec03

class Lec03MainKt {
}

fun main() {
    val num1 = 5;
    val num2: Long = num1.toLong();
    println(num2);

    val num3: Int? = 4;
    val num4: Long = num3?.toLong() ?: 0L
    println(num4);

    val person = Person("시원박", 30);
    println("안녕하세요, 저는 ${person.name}이고, 나이는 ${person.age}입니다.")
    val name = "시원박"
    println("안녕하세요, 저는 $name" + "입니다.")

    val str = "ABCDE"
    val ch = str[1] // B
    println(ch)
}


fun printPersonAge1(obj: Any) {
    if (obj is Person) {
        println(obj.age)
    } else if (obj !is Person) {
        println(123)
    }
}

fun printPersonAge2(obj: Any?) {
    val person = obj as Person
    print(person.age)

    val person2 = obj as? Person
    print(person2?.age)
}