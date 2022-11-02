package com.lannstark.lec17

class Lec17MainKt {
}

val fruits = listOf(
    Fruit("사과", 1000),
    Fruit("사과", 1200),
    Fruit("사과", 1200),
    Fruit("사과", 1500),
    Fruit("바나나", 3000),
    Fruit("바나나", 3200),
    Fruit("바나나", 2500),
    Fruit("수박", 10000)
)

fun main() {

    
    // 람다를 만드는 방법(이름 없는 함수) (Fruit) -> Boolean은 함수의 타입을 작성해준 것
    val isApple: (Fruit) -> Boolean = fun(fruit: Fruit): Boolean {
        return fruit.name == "사과"
    }
    
    // 람다를 만드는 방법2
    val isApple2: (Fruit) -> Boolean = { fruit: Fruit -> fruit.name == "사과"}

//    isApple(fruits[0])
//    isApple.invoke(fruits[0])

    println(filterFruits(fruits, isApple))
    filterFruits(fruits) {fruit -> fruit.name == "사과" }
    filterFruits(fruits) {it.name == "사과" } // 파라미터가 한 개일 경우 it이라는 키워드를 사용 가능함

    var targetFruitName = "바나나"
    targetFruitName = "수박"
    filterFruits(fruits) { fruit -> fruit.name == targetFruitName }

}
    private fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean): List<Fruit> {
        val results = mutableListOf<Fruit>()
        for (fruit in fruits) {
            if (filter(fruit)) {
                results.add(fruit)
            }
        }
        return results
    }


