package com.lannstark.lec14

fun main() {
    val dto1 = KotlinPersonDto("박시원", 29)
    val dto2 = KotlinPersonDto("박시원", 29)
    println(dto1) // KotlinPersonDto(name=박시원, age=29) => toString
    print(dto1 == dto2) // true => equals
}

// class 앞에 data 만 붙이면 dto 가 됨 => equals, hashCode, toString 등이 존재
// 만약 named argument 까지 쓴다면 builder 패턴을 쓰는 것과 같은 효과!
data class KotlinPersonDto (
    val name: String,
    val age: Int,
)


