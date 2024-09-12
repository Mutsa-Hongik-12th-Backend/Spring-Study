package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired // 스프링 컨테이너에서 컨트롤러를 가져오는 어노테이션
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


}
