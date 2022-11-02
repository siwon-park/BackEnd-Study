package com.lannstark.lec18

class Lec18MainKt {

}

    data class Fruit(
        val id: Long,
        val name: String,
        val factoryPrice: Long,
        val currentPrice: Long,
    ) {
        val isSamePrice: Boolean
            get() = factoryPrice == currentPrice

    }



fun main() {
    val fruits = listOf<Fruit>(
        Fruit(1L, "사과", 100L, 200L),
        Fruit(2L, "사과", 100L, 300L),
        Fruit(3L, "사과", 100L, 250L)
    )




    // filter
    val apples = fruits.filter {fruit -> fruit.name == "사과"};
    println(apples) // [Fruit(id=1, name=사과, factoryPrice=100, currentPrice=200), ..]

    // filterIndexed
    val applesIndexed = fruits.filterIndexed { idx, fruit ->
        println(idx)
        fruit.name == "사과"
    }
    println(applesIndexed)

    // 맵
    val applePrices = fruits.filter { fruit -> fruit.name == "사과" }.map { fruit -> fruit.currentPrice }
    println(applePrices) // [200, 300, 250]

    // mapIndexed
    val appleMapIndexed = fruits.filter { fruit -> fruit.name == "사과" }.mapIndexed{ idx, fruit ->
        println(idx)
        fruit.currentPrice
    }
    println(appleMapIndexed)


    // mapNotNull
//    val applesNotNull = fruits.filter { fruit -> fruit.name == "사과" }.mapNotNull { fruit -> fruit.nullOrValue()}

}
