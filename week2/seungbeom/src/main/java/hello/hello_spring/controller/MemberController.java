package hello.hello_spring.controller;

import hello.hello_spring.service.MemberSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberSerivice memberSerivice;

    @Autowired
    public MemberController(MemberSerivice memberSerivice) {
        this.memberSerivice = memberSerivice;
    }
}
