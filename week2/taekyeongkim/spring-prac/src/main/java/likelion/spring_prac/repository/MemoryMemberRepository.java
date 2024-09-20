package likelion.spring_prac.repository;

import likelion.spring_prac.domain.Member;

import java.util.*;

//@Repository
public class MemoryMemberRepository implements MemberRepository{

    // 저장할 곳이 필요
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // 키 값 생성
    
    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 단순히 store.get(id) 를 반환하지 않고 Optional로 감싸서 반환
        // -> Null인 경우에 클라이언트 측에서 조치 가능
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // store의 member들 반환
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
