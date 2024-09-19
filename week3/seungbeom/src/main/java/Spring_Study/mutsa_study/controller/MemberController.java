package Spring_Study.mutsa_study.controller;

import Spring_Study.mutsa_study.domain.Member;
import Spring_Study.mutsa_study.dto.MemberRequestDTO;
import Spring_Study.mutsa_study.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDTO requestDTO) {
        Member member = memberService.createMember(requestDTO);
        return ResponseEntity.status(201).body(member);
    }

    @GetMapping
    public ResponseEntity<?> getMemberList() {
        try {
            List<Member> members = memberService.findAllMembers();
            if (members.isEmpty()) {
                return ResponseEntity.status(200).body("사용자가 존재하지 않습니다.");
            }
            return ResponseEntity.status(200).body(members);
        }catch (Exception e) {
            return ResponseEntity.status(500).body("서버 내부 에러");
        }

    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?>getOneMember(@PathVariable("memberId") Long memberId) {
        try{
            Optional<Member> response = memberService.findMemberById(memberId);
            if (response.isPresent()) {
                return ResponseEntity.ok(response.get());
            } else {
                return ResponseEntity.status(404).body("해당 사용자를 찾을 수 없습니다.");
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body("서버 내부 에러");
        }
    }
}
