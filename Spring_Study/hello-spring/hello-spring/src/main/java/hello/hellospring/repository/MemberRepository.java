package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.Optional;
import java.util.List;

public interface MemberRepository {
    Member save(Member member); // 맴버를 저장하고, 저장된 회원을 반환
    // Optional=> 없으면 null을 반환(해당 상황에 대해 null이라는 것을 구현을 해주지 않아도 됨)
    Optional<Member> findById(Long id);

    Optional<Member> findByName(String name);

    List<Member> findAll();


}
