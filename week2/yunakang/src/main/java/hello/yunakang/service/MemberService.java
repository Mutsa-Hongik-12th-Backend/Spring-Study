package hello.yunakang.service;
import hello.yunakang.domain.Member;
import hello.yunakang.repository.MemberRepository;
import hello.yunakang.repository.MemberRepository;
import hello.yunakang.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class MemberService {
  private final MemberRepository memberRepository;

  // MemberRepository를 주입받는 생성자 추가, test에서 error 나서 코드 수정함

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
  /**
   *
   회원가입
   */
  public Long join(Member member) {
    validateDuplicateMember(member); //중복 회원 검증
    memberRepository.save(member);
    return member.getId();
  }
  private void validateDuplicateMember(Member member) {
    memberRepository.findByName(member.getName())
        .ifPresent(m -> {
          throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
  }
  /**
   *
   전체 회원 조회
   */
  public List<Member> findMembers() {
    return memberRepository.findAll();
  }
  public Optional<Member> findOne(Long memberId) {
    return memberRepository.findById(memberId);
  }
}