package heemin.heeminbyun.Service;

import heemin.heeminbyun.domain.Member;
import heemin.heeminbyun.repository.MemberRepository;
import heemin.heeminbyun.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member) {
        //같은 이름이 있는 중복회원 X
        //null 일 가능성이 있기 때문에 optional로 감싸서..
        //Optional<Member> result = memberRepository.findByName(member.getName());
        //result.ifPresent(m -> {
        //    throw new IllegalStateException("이미 존재하는 회원입니다.");
        //});

        validateDuplicateMember(member); //중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m->{
                    throw new IllegalStateException(("이미 존재하는 회원입니다."));
                });
    }
    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

}

