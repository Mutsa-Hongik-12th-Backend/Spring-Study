package hello.yunakang;

import hello.yunakang.repository.MemberRepository;
import hello.yunakang.repository.MemoryMemberRepository;
import hello.yunakang.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SpringConfig {
  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository());
  }
  @Bean
  public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
}