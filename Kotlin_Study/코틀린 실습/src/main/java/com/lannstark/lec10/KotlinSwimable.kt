package com.lannstark.lec10

interface KotlinSwimable {

    val swimAbility: Int // backing field가 없는 프로퍼티

    fun act() {
        println("어푸 어푸")
    }

    fun swim() // 추상 메서드

}

