package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest // 스프링 컨텍스트가 관리하는 테스트이기 때문에 @SpringBootTest 어노테이션을 붙여준다.
class KotlinUserServiceTest @Autowired constructor( // constructor 앞에 @Autowired를 붙이면 각 프로퍼티에 붙이는 것을 생략 가능
    private val userRepository: UserRepository,
    private val userService: UserService,
) {

    // 각 테스트 코드를 개별적으로 수행하면 통과하지만, 전체 실행 시에는 통과를 못함
    // 그 이유는 SpringContext가 테스트 메서드를 공유하고 있기 때문임
    // 따라서 @AfterEach로 clean 함수를 만들어서 각 테스트 메서드가 종료되었을 때, 레포지토리를 비워준다.
    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("유저 저장이 정상 동작한다.")
    fun saveUserTest() {
        // given
        val reqeust = UserCreateRequest("박시원", 30)

        // when
        userService.saveUser(reqeust)

        // then
        // 개수가 지금 1개이므로 개수를 확인
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        // 요소도 제대로 들어갔는지 확인
        assertThat(results[0].name).isEqualTo("박시원")
        assertThat(results[0].age).isEqualTo(30)
        // 만약 request를 생성할 때 age의 자료형이 Integer이므로 null을 넣을 수 있는데, null을 넣었다면,
        // assertThat(results[0].age).isNull()에서 테스트가 실패한다.
        // 그 이유는 자바에서 Integer형이 null이 가능한 것은 코틀린 입장에서는 모르기 때문에
        // 해당 코드가 통과하기 위해선 getter에 @NotNull 어노테이션을 붙여줘야 한다.(results[0].age이 getter이므로)
    }

    @Test
    @DisplayName("유저 조회가 정상 동작한다.")
    fun getUsersTest() {
        // given
        userRepository.saveAll(listOf(
            User("A", 20),
            User("B", 25),
            User("C", null)
        ))
        // when
        val results = userService.getUsers()

        // then
        assertThat(results).hasSize(3) // [UserResponse(), UserResponse(), ...]
        assertThat(results).extracting("name").containsExactlyInAnyOrder("A", "B", "C") // ["A", "B", ...]
        assertThat(results).extracting("age").containsExactlyInAnyOrder(20, 25, null) // ["A", "B", ...]
    }

    @Test
    @DisplayName("유저 수정이 정상 동작한다.")
    fun updateUserNameTest() {
        // given
        val savedUser = userRepository.save(User("A", null))
        val request = UserUpdateRequest(savedUser.id!!, "B") // savedUser.id!! Null 아님 단언

        // when
        userService.updateUserName(request)

        // then
        val result = userRepository.findAll()[0]
        assertThat(result.name).isEqualTo("B")
    }

    @Test
    @DisplayName("유저 삭제가 정상 동작한다.")
    fun deleteUserTest() {
        // given
        userRepository.save(User("A", null))

        // when
        userService.deleteUser("A")

        // then
        assertThat(userRepository.findAll()).isEmpty()
    }

}