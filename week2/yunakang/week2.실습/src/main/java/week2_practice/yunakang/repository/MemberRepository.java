package week2_practice.yunakang.repository;
import week2_practice.yunakang.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
