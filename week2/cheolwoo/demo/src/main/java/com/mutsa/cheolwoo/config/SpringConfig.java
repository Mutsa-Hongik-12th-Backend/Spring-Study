package com.mutsa.cheolwoo.config;

import com.mutsa.cheolwoo.repository.MemberRepository;
import com.mutsa.cheolwoo.repository.MemoryMemberRepository;
import com.mutsa.cheolwoo.service.MemberService;
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