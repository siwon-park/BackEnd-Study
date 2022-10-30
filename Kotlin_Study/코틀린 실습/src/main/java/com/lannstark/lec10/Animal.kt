package com.lannstark.lec10

abstract class Animal (
    protected val species: String,
    protected open val legCount: Int, // 추상 프로퍼티가 아니라면, 상속받을 때 open 을 붙여줘야한다.
) {
    abstract fun move()
}

