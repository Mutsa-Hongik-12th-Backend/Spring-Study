package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository {

    public static Map<Long, Member> members = new HashMap<>();
    public static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        members.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(members.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return members.values().stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    public void clear() {
        members.clear();
    }
}
