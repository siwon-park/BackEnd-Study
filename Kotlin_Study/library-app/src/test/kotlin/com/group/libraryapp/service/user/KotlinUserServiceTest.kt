package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.type.UserLoanStatus
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
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
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

    @Test
    @DisplayName("대출 기록이 없는 유저도 응답에 포함된다")
    fun getUserLoanHistoriesTest1() {
        // given
        userRepository.save(User("시원", 30))

        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("시원")
        assertThat(results[0].books).isEmpty()
    }

    @Test
    @DisplayName("대출 기록이 많은 유저의 응답이 정상 동작한다")
    fun getUserLoanHistoriesTest2() {
        // given
        val savedUser = userRepository.save(User("시원", 30))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
            )
        )
        // when
        val results = userService.getUserLoanHistories()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("시원")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }

    @Test
    @DisplayName("FETCH JOIN 테스트")
    fun getUserLoanHistoriesTest3() {
        // given
        val savedUser = userRepository.save(User("시원", 30))
        userLoanHistoryRepository.saveAll(
            listOf(
                UserLoanHistory.fixture(savedUser, "책1", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책2", UserLoanStatus.LOANED),
                UserLoanHistory.fixture(savedUser, "책3", UserLoanStatus.RETURNED),
            )
        )

        // when
        val results = userService.getUserLoanHistoriesImproved()

        // then
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("시원")
        assertThat(results[0].books).hasSize(3)
        assertThat(results[0].books).extracting("name")
            .containsExactlyInAnyOrder("책1", "책2", "책3")
        assertThat(results[0].books).extracting("isReturn")
            .containsExactlyInAnyOrder(false, false, true)
    }

}