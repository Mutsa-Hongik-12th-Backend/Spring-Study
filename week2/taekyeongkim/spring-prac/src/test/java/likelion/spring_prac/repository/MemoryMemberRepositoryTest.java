package likelion.spring_prac.repository;

import likelion.spring_prac.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// public 일 필요 없어서 public 없앰
class MemoryMemberRepositoryTest {

    // MemberRepository repository = new MemoryMemberRepository();
    // |-> MemoryMemberRepository만 test 하는 것이므로 interface가 아닌 MemoryMemberRepository로 변경
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    // save 기능이 동작하는지 test 해보고자 함
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // optional -> get으로 꺼내와서 result라는 Member 객체에 받아 봄
        Member result = repository.findById(member.getId()).get();

        // 직접 글자 찍기
        // : System.out.println("result = " + (result == member));

        // 직접 출력은 아니지만 돌렸을 때 아무것도 안 나오고 초록색 체크
        // Assertions.assertEquals(member, result);
        // 만약 실패하면 (ex) (Assertions.assertEquals(member, actual:null);) -> error 뜸

        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
