package week2_practice.yunakang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import week2_practice.yunakang.domain.Member;
import week2_practice.yunakang.dto.MemberRequestDTO;
import week2_practice.yunakang.repository.MemberRepository;

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
