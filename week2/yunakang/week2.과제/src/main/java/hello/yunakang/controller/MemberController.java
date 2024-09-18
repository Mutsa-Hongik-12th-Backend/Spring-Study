package hello.yunakang.controller;
import hello.yunakang.service.MemberService;
import hello.yunakang.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
@Controller
public class MemberController {
  private final MemberService memberService;
  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }
}
