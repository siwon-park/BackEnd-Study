package com.lannstark.lec11

class Lec11MainKt {
}

fun main() {
    Fruit("청포도", "박시원", 3000)

}

class Car internal constructor(val price: Int)

    class Fruit( // 이 클래스는 3개의 getter와 2개의 setter로 이루어져있음
       internal val name: String,
       private var buyer: String,
        _price: Int
        ) {
        var price = _price
            private set
    }
