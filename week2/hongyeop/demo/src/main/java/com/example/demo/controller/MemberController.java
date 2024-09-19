package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import com.example.demo.domain.Member;
import com.example.demo.dto.MemberRequestDTO;
import com.example.demo.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member member = memberService.createMember(memberRequestDTO);
        return ResponseEntity.status(201).body(member);

    }

    @GetMapping
    public ResponseEntity<?> getMemberList() {
        try {
            List<Member> response;
            response = memberService.getMemberList();
            if (response.isEmpty()) {
                return ResponseEntity.status(200).body("사용자가 존재하지 않습니다.");
            }
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("서버 내부 에러");
        }
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<?> getOneMember(@PathVariable("memberId") Long memberId) {
        try {
            Optional<Member> response = memberService.getOneMember(memberId);
            if (response.isPresent()) {
                return ResponseEntity.ok(response.get());
            } else {
                return ResponseEntity.status(404).body("해당 사용자를 찾을 수 없습니다.");
            }
        } catch(Exception e) {
            return ResponseEntity.status(500).body("서버 내부 에러");
        }
    }
}
