package com.lannstark.lec12

class AnonymousClassKt {
}

    fun main() {
        moveSomething(object : Movable {
            override fun fly() {
                println("날다 날다")
            }

            override fun move() {
                println("움직인다")
            }
        })
    }

    private fun moveSomething(movable: Movable) {
        movable.move()
        movable.fly()
    }

