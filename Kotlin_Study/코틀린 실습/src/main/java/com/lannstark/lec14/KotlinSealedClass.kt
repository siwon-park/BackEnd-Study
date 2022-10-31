package com.lannstark.lec14

class KotlinSealedClass {
}

    fun main() {
        handleCar(Avante())
    }

    fun handleCar(car: HyundaiCar) {
        when (car) {
            is Avante -> TODO()
            is Sonata -> TODO()
            is Grandeur -> TODO()
        }
    }

    sealed class HyundaiCar(
        val name: String,
        val price: Long,
    )

    class Avante : HyundaiCar("아반떼", 3000)
    class Sonata : HyundaiCar("소나타", 3500)
    class Grandeur : HyundaiCar("그렌저", 4000)
