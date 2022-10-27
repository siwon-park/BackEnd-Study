package com.lannstark.lec01

fun main () {
    var number1 = 10L;
    number1 = 5;

    val number2 = 10L;
//    number2 = 5; // 에러 발생

    var number3 : Int = 5;
    var number4: Int;
    
    // Long 레퍼런스 타입으로 보이지만 코틀린 내부에서 필요에 따라 원시형(long)으로 변환시킴
    var number: Long = 1000L;
    var number5: Long = 1_000L;

    var number_null = 1000L;
    // number_null = null; // 불가능

    var null_number: Long? = 1000L;
    null_number = null;

    var person = Person("시원박");

}