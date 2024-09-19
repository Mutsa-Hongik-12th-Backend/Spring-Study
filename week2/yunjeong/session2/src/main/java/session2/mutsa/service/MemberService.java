package session2.mutsa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import session2.mutsa.domain.Member;
import session2.mutsa.dto.MemberRequestDTO;
import session2.mutsa.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    public Member createMember(MemberRequestDTO memberRequestDTO){
        Member member = Member.builder()
                .name(memberRequestDTO.name())
                .email(memberRequestDTO.email())
                .build();

        Member savedMember = memberRepository.save(member);
        return savedMember;
    }
    public List<Member> getMemberList() { return memberRepository.findAll(); }
    public Optional<Member> getOneMember(Long memberId) { return  memberRepository.findById(memberId);}
}
