package com.lannstark.lec10

class KotlinPenguin(species: String) : Animal(species, 2), KotlinSwimable, KotlinFlyable {
    private val wingCount: Int = 2

    override fun move() {
        println("펭 펭")
    }

    override val legCount: Int
        get() = super.legCount + this.wingCount

    override fun act() {
        super<KotlinSwimable>.act()
        super<KotlinFlyable>.act()
    }

    override fun swim() {
        println("펭펭 수영")
    }

    override val swimAbility: Int
        get() = 3
}

