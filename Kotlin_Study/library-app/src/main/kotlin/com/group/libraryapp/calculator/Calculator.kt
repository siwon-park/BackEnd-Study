package com.group.libraryapp.calculator

class Calculator (
    var number: Int
) {
    // 외부에서 접근하기 위한 퍼블릭 getter생성
//    val number: Int
//        get() = this._number
    // 더하기
    fun add(operand: Int) {
        this.number += operand
    }

    // 뺄셈
    fun minus(operand: Int) {
        this.number -= operand
    }

    // 곱셈
    fun multiply(operand: Int) {
        this.number *= operand
    }

    // 나눗셈
    fun divide(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0으로는 나눌 수 없습니다.")
        }
        this.number /= operand
    }
    
}