package Spring_Study.mutsa_study.service;

import Spring_Study.mutsa_study.domain.Member;
import Spring_Study.mutsa_study.dto.MemberRequestDTO;
import Spring_Study.mutsa_study.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Member createMember(MemberRequestDTO requestDTO) {
        Member member = memberRepository.save(requestDTO.toEntity());
        return member;
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }
}
