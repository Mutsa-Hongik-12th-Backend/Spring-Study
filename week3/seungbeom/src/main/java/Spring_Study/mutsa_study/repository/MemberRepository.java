package Spring_Study.mutsa_study.repository;

import Spring_Study.mutsa_study.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
