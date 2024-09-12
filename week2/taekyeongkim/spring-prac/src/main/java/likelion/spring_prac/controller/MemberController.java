package likelion.spring_prac.controller;

import likelion.spring_prac.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    // 생성자
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    @Autowired
//    public void setmemberService(MemberService memberService) {
//        this.memberService = memberService;
//    }

}
