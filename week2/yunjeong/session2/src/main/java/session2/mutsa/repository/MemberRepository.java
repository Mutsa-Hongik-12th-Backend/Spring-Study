package session2.mutsa.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import session2.mutsa.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
