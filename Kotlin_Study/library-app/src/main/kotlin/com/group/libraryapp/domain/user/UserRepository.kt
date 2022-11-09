package com.group.libraryapp.domain.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name: String): User? // Optional<User>
    // Optional은 어떤 값이 Null인지 아닌지를 나타내기 위해서 JDK8부터 등장했지만,
    // 코틀린에서는 타입?를 통해서 이를 해결할 수 있으므로 크게 필요가 없다.

}