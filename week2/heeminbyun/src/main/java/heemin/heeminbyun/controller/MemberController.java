package heemin.heeminbyun.controller;

import heemin.heeminbyun.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired //spring 컨테이너에 있는 멤버 서비스를 가져다 연결해줌
    //멤버 컨트롤러가 생성될 때 스프링빈에 등록되어있는 멤버 서비스 객체를 가져다가 넣어줌. (dependency injection)
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
