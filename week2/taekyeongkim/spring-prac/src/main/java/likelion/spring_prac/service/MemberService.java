package likelion.spring_prac.service;

import likelion.spring_prac.domain.Member;
import likelion.spring_prac.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

//@Service
public class MemberService {

    // repository 생성
    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final MemberRepository memberRepository;
    // repository 를 직접 생성하는 것이 아니라 외부에서 넣어주도록 바꿈
    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     *  회원 가입
     */
    public Long join(Member member) {
        // 로직 ex : 같은 이름의 중복 회원 불가
        // Optional -> ifPresent로 로직 구현
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     *  회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
