package com.lannstark.lec18

class CollectionManipulationKt {
}

// 로컬 영역에서 작성하면 안 됨 => 전역에서 작성
val List<Fruit>.samePriceFilter: List<Fruit>
    get() = this.filter(Fruit::isSamePrice)

fun main() {

    val fruits = listOf<Fruit>(
        Fruit(1L, "사과", 100L, 200L),
        Fruit(2L, "사과", 100L, 300L),
        Fruit(3L, "사과", 100L, 250L),
        Fruit(4L, "메론", 100L, 250L),
        Fruit(5L, "포도", 100L, 250L),
        Fruit(6L, "오렌지", 100L, 250L),
    )
    
    // all
    val isAllApple = fruits.all { fruit -> fruit.name == "사과" }
    println(isAllApple) // false

    // none
    val isNoneApple = fruits.none { fruit -> fruit.name == "사과" }
    println(isNoneApple) // false

    // any
    val isAnyAppleOver300 = fruits.any { fruit -> fruit.currentPrice >= 300 }
    println(isAnyAppleOver300) // true

    val fruitCount = fruits.count()
    println(fruitCount) // 6

    val fruitOrderByAsc = fruits.sortedBy { fruit -> fruit.currentPrice }
    println(fruitOrderByAsc)

    val fruitOrderByDesc = fruits.sortedByDescending { fruit -> fruit.currentPrice }
    println(fruitOrderByDesc)


    val distinctFruitNames = fruits.distinctBy { fruit -> fruit.name }
        .map { fruit -> fruit.name } // 이름의 중복을 제거
    println(distinctFruitNames) // [사과, 메론, 포도, 오렌지]

    val firstValue = fruits.first()
    println(firstValue) // Fruit(id=1, name=사과, factoryPrice=100, currentPrice=200)

    val mappedFruit: Map<String, List<Fruit>> = fruits.groupBy { fruit -> fruit.name }
    println(mappedFruit) // {사과=[Fruit(id=1, name=사과, factoryPrice=100, currentPrice=200), ... }

    val mappedFruitCustom: Map<String, List<Long>> = fruits.groupBy(
        { fruit -> fruit.name}, {fruit -> fruit.factoryPrice }
    )
    println(mappedFruitCustom) // {사과=[100, 100, 100], 메론=[100], 포도=[100], 오렌지=[100]}

    val mappedFruit2: Map<Long, Fruit> = fruits.associateBy { fruit -> fruit.id }
    println(mappedFruit2) // {1=Fruit(id=1, name=사과, factoryPrice=100, currentPrice=200), ... }

    val mappedFruitCustom2: Map<Long, Long> = fruits.associateBy(
        { fruit -> fruit.id}, {fruit -> fruit.factoryPrice }
    )
    println(mappedFruitCustom2) // {1=100, 2=100, 3=100, 4=100, 5=100, 6=100}

    val fruitsInList: List<List<Fruit>> = listOf(
        listOf(
            Fruit(1L, "사과", 1000, 1500),
            Fruit(2L, "사과", 1200, 1500),
            Fruit(3L, "사과", 1200, 1500),
            Fruit(4L, "사과", 1100, 1500),
        ),
        listOf(
            Fruit(5L, "포도", 2000, 2500),
            Fruit(6L, "포도", 2200, 2500),
            Fruit(7L, "포도", 2200, 2500),
            Fruit(8L, "포도", 2100, 2500),
        ),
        listOf(
            Fruit(9L, "멜론", 3500, 3500),
            Fruit(10L, "멜론", 3500, 3500),
        ),
    )
    
    
    // 동일한 가격을 가지는 과일 객체로 리스트 평탄화
    val samePriceFruits = fruitsInList.flatMap { list ->
        list.filter{ fruit -> fruit.factoryPrice == fruit.currentPrice }
    }
    println(samePriceFruits) // [Fruit(id=9, name=멜론, factoryPrice=3500, currentPrice=3500), ...]

    // 리팩토링 버전
    val samePriceFruitsV2 = fruitsInList.flatMap { list -> list.samePriceFilter }
    println(samePriceFruitsV2) // [Fruit(id=9, name=멜론, factoryPrice=3500, currentPrice=3500), ...]

    val flattenFruits = fruitsInList.flatten()
    println(flattenFruits) // [Fruit(id=1, name=사과, factoryPrice=1000, currentPrice=1500), ... ]

}

