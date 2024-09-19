package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    public void clearMembers() {
        memberRepository.clear();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        memberRepository.save(member);

        Member result = memberRepository.findById(member.getId()).get();

        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring");
        memberRepository.save(member2);

        Member result = memberRepository.findByName(member1.getName()).get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring");
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(2);
    }

}