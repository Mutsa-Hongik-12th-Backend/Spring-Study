package com.mutsa.cheolwoo.controller;

import com.mutsa.cheolwoo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
}
