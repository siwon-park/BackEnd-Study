package com.group.libraryapp.type

// Boolean 필드도 enum class로 만드는 것이 나중에 확장성을 고려한 개발을 하기 유리함
// 예를 들어 boolean 상태에 따라 여러 케이스로 나뉘는데, 조합으로 모든 경우의 수를 구현했을 때
// 논리적으로 존재할 수 없는 경우가 나올 수도 있기 때문 => 예) 휴면 유저(IsActive)이면서 탈퇴한 유저(IsDeleted)인 경우는 존재하지 않음
enum class UserLoanStatus {
    RETURNED, // 반납된 상태
    LOANED, // 대충 중인 상태
}