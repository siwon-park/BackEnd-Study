package com.lannstark.lec19

import com.lannstark.lec17.Fruit

class TypeAliasKT {
}

fun main() {
//    filterFruits()
//    typeAliasFilterFruits()
}

// (Fruit) -> Boolean 이라는 타입이 너무 길다면..?
fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean) {
}

// typealias 키워드를 사용해서 축약 가능!
typealias FruitFilter = (Fruit) -> Boolean

fun typeAliasFilterFruits(fruits: List<Fruit>, filter: FruitFilter) {

}

