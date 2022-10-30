package com.lannstark.lec10

class KotlinCat(
    species: String
) : Animal(species, 4) {
    override fun move() {
        println("사뿐 사뿐")
    }
}