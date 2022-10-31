package com.lannstark.lec14

class KotlinEnumClass {
}

    // when 을 enum 클래스와 같이 사용함
    // (1) if-else 보다 가독성 좋음
    // (2) 컴파일러가 country 의 모든 타입을 알고 있어 다른 타입에 대한 로직(else)를 작성하지 않아도 됨
    fun handleCountry(country: Country) {
        when(country) {
            Country.KOREA -> TODO()
            Country.AMERICA -> TODO()
        }
    }

    enum class Country(
        private val code: String,
    ) {
        KOREA("KOR"),
        AMERICA("USA")
    }