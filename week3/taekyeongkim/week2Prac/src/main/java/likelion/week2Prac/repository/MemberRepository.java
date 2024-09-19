package likelion.week2Prac.repository;

import likelion.week2Prac.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository     // repository 클래스임
public interface MemberRepository extends JpaRepository<Member, Long> {
}
