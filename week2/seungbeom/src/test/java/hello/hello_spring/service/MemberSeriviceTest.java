package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberSeriviceTest {
    MemoryMemberRepository memberRepository;
    MemberSerivice memberSerivice;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberSerivice = new MemberSerivice(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clear();
    }

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("spring");

        Long result = memberSerivice.join(member);
        Member findMember = memberSerivice.findById(result).get();

        assertThat(findMember.getName()).isEqualTo("spring");

    }

    @Test
    void 중복_회원_예외() {
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberSerivice.join(member);

//        assertThrows(IllegalStateException.class, () -> memberSerivice.join(member2));

        try {
            memberSerivice.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    }
}