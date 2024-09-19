package likelion.week2Prac.controller;

import likelion.week2Prac.domain.Member;
import likelion.week2Prac.dto.MemberRequestDTO;
import likelion.week2Prac.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController                // restcontroller 임
@RequiredArgsConstructor       // 생성자 의존성 주입 자동
@RequestMapping("/members") // 공통 엔드포인트
public class MemberController {

    private final MemberService memberService;

    // POST 요청 처리
    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member member = memberService.createMember(memberRequestDTO);
        return ResponseEntity.status(201).body(member);
    }

    // GET 요청 처리
    @GetMapping
    public ResponseEntity<?> getMemberList() {
        try{
            List<Member> response;
            response = memberService.getMemberList();
            if(response.isEmpty()){
                return ResponseEntity.status(200).body("사용자가 존재하지 않습니다."); //200, 빈 리스트 반환
            }
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(500).body("서버 내부 에러");
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getOneMember(@PathVariable Long memberId) {
        try{
            Optional<Member> response = memberService.getOneMember(memberId);
            if (response.isPresent()) {
                return ResponseEntity.ok(response.get());
            } else {
                return ResponseEntity.status(404).body("해당 사용자를 찾을 수 없습니다.");
            }
        }catch (Exception e){
            return ResponseEntity.status(500).body("서버 내부 에러");
        }
    }
}
