package com.lannstark.lec02

class Lec02MainKt {

}

fun main() {
    val str: String? = "ABC"; // null이 들어갈 수 있는 문자열 변수
//    str.length; // 불가능
    str?.length; // 가능

    var str2: String? = null
    println(str?.length ?: 0) // 결과가 null이 아니면 원래 결과를 반환하고 null이면 :뒤의 값을 반환함

}

fun startsWithA1(str: String?): Boolean {
    return str?.startsWith("A") ?: throw IllegalArgumentException("null")
//    if (str == null) {
//        throw IllegalArgumentException("null");
//    }
//    return str.startsWith("A");
}

fun startsWithA2(str: String?): Boolean? {
    return str?.startsWith("A")
//    if (str == null) {
//        return null;
//    }
//    return str.startsWith("A");
}

fun startsWithA3(str: String?): Boolean {
    return str?.startsWith("A") ?: false
//    str.startsWith("A"); // 에러 발생
//    if (str == null) {
//        return false;
//    }
//    return str.startsWith("A");
}

fun startswithA5(str: String?): Boolean {
    return str!!.startsWith("A")
}


