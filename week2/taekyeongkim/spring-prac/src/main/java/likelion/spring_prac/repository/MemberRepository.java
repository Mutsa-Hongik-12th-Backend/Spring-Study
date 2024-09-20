package likelion.spring_prac.repository;

import likelion.spring_prac.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    // Optional : Null인 경우를 대비
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll(); // 저장된 모든 멤버 리스트 반환
}
