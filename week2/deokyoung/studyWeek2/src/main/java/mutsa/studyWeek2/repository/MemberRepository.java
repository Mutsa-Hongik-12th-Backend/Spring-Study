package mutsa.studyWeek2.repository;

import mutsa.studyWeek2.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);   // optinal은 null값의 경우 null을 반환하는 대신 optional로 반환
    List<Member> findAll();     // 지금까지 저장된 회원 리스트 전체 반환
}
