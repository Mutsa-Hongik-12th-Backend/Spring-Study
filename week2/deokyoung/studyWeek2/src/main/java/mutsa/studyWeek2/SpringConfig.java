package mutsa.studyWeek2;

import mutsa.studyWeek2.repository.MemberRepository;
import mutsa.studyWeek2.repository.MemoryMemberRepository;
import mutsa.studyWeek2.service.MemberService;
import org.springframework.beans.factory.annotation.Configurable;
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
