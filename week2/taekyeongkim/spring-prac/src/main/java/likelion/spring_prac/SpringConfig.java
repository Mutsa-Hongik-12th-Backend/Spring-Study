package likelion.spring_prac;

import likelion.spring_prac.repository.MemberRepository;
import likelion.spring_prac.repository.MemoryMemberRepository;
import likelion.spring_prac.service.MemberService;
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
