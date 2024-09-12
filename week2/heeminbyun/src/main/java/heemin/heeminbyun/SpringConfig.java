package heemin.heeminbyun;

import heemin.heeminbyun.Service.MemberService;
import heemin.heeminbyun.repository.MemberRepository;
import heemin.heeminbyun.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean  //스프링 빈에 등록하라는 의미
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
