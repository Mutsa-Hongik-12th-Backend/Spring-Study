package mutsa.second.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.second.demo.domain.Member;
import mutsa.second.demo.dto.MemberRequestDTO;
import mutsa.second.demo.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
//@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(MemberRequestDTO memberRequestDTO){
        Member member = Member.builder()
                .name(memberRequestDTO.getName())
                .email(memberRequestDTO.getEmail())
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