package session2.mutsa.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import session2.mutsa.domain.Member;
import session2.mutsa.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    // 만약 BeforeEach를 사용한다면!
//    @BeforeEach
//    public void cleanup() {
//        memberRepository.deleteAll();
//    }

    @Test
    @Transactional
    @Rollback //데이터 베이스 반영을 막는다
    public void createMember() {
        //given
        Member member = Member.builder()
                .name("이윤정")
                .email("djdjjdjd@gmail.com")
                .build();
        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertThat(savedMember.getId()).isNotNull(); // ID가 자동 생성되었는지 확인
        assertThat(savedMember.getName()).isEqualTo("이윤정");
        assertThat(savedMember.getEmail()).isEqualTo("djdjjdjd@gmail.com");
        assertThat(savedMember.getCreatedAt()).isNotNull();
    }

    @Test
    void getMemberList() {
        //given
        Member member1 = Member.builder()
                .name("이윤정")
                .email("djdjjdjd@gmail.com")
                .build();
        Member member2 = Member.builder()
                .name("이멋사")
                .email("dkdkdkdkkd@gmail.com")
                .build();
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void getOneMember() {
        //given
        Member member1 = Member.builder()
                .name("이윤정")
                .email("djdjjdjd@gmail.com")
                .build();
        Member member2 = Member.builder()
                .name("이멋사")
                .email("dkdkdkdkkd@gmail.com")
                .build();
        //when
        memberRepository.save(member1);
        memberRepository.save(member2);
        Member result = memberRepository.findById(member1.getId()).get();

        //then
        assertThat(result.getName()).isEqualTo(member1.getName());
    }
}