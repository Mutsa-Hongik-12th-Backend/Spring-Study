package mutsa.session2.service;

import lombok.RequiredArgsConstructor;
import mutsa.session2.domain.Member;
import mutsa.session2.dto.MemberRequestDTO;
import mutsa.session2.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
//@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(MemberRequestDTO memberRequestDTO){
        Member member = Member.builder()
                .name(memberRequestDTO.name())
                .email(memberRequestDTO.email())
                .build();

        Member savedMember = memberRepository.save(member);
        return savedMember;

    }
    public List<Member> getMemberList(){
        return memberRepository.findAll();
    }

    public Optional<Member> getOneMember(Long memberId){
        return memberRepository.findById(memberId);
    }
}