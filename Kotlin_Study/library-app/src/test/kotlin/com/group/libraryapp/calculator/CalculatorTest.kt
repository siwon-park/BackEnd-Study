package com.group.libraryapp.calculator

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
    calculatorTest.divideExceptionTest()
}


class CalculatorTest {

    // 덧셈 함수 테스트
    fun addTest() {
        // given
        val calculator = Calculator(5) // 계산기 인스턴스 생성

        // when
        calculator.add(3)
        // but number가 private인데 어떻게 값을 알 수 있지?
        // 방법1) 계산기 클래스를 data class로 만들어주고 다음 코드를 작성
        // then
//        val expectedCalculator = Calculator(8)
//        if (calculator != expectedCalculator) {
//            throw IllegalStateException()
//        }

        // 방법2) number 필드를 public으로 변환(private를 삭제)
        // 방법3) number 필드를 다시 private으로 바꾸고, public getter를 생성하여 테스트에 사용
        // then
        if (calculator.number != 8) {
            throw IllegalStateException()
        }

    }

    // 뺄셈 함수 테스트
    fun minusTest() {
        // given
        val calculator = Calculator(10)

        // when
        calculator.minus(3)

        // then
        if (calculator.number != 7) {
            throw IllegalStateException()
        }
    }

    // 곱셈 함수 테스트
    fun multiplyTest() {
        // given
        val calculator = Calculator(10)

        // when
        calculator.multiply(3)

        // then
        if (calculator.number != 30) {
            throw IllegalStateException()
        }
    }
    
    // 나눗셈 테스트
    fun divideTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(2)

        // then
        if (calculator.number != 2) {
            throw IllegalStateException()
        }
    }

    // 0으로 나누는 테스트
    fun divideExceptionTest() {
        // given
        val calculator = Calculator(5)

        // when
        try {
            calculator.divide(0)
        } catch (e: IllegalArgumentException) {
            if (e.message != "0으로는 나눌 수 없습니다.") { // 예외처리 메세지가 다를 경우에도 예외를 던지도록 할 수 있음
                throw IllegalStateException() // 따라서 본 코드에서 예외처리 결과 메세지가 달라졌다면 테스트는 실패함
            }
          // 0으로 나눴을 때, IllegalArgumentException가 발생하면 테스트 성공
            return
        } catch(e: Exception) {
            throw IllegalStateException()
        }
        throw IllegalStateException("기대하는 예외가 발생하지 않았습니다.")
    }

}