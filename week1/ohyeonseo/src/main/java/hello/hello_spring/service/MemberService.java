package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

        // 회원가입
        public Long join(Member member) {
            validateDuplicateMember(member); //중복회원 검증
            memberRepository.save(member);
            return member.
                    etId();
        }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()) //optional
            .ifPresent(m -> {
                throw new IllegalStateException("already exists");
            });
    }

    //전체회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long id) {
            return memberRepository.findById(id);
    }


}
