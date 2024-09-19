# 🦁스프링 웹 개발 구조
## 이전 실습 깃허브 링크
***
(https://github.com/psb3707/Mutsa-Spring-Study-Post.git)

## 웹 어플리케이션 계층 구조
***
### 컨트롤러 (프레젠테이션 계층)
- HTTP 요청을 받고 이 요청을 비즈니스 계층(서비스)으로 전송하는 역할
### 비즈니스 계층(서비스)
- 핵심 비즈니스 로직이 구현되어 있는 부분
- 예를들어 주문 서비스라면 주문 개수, 가격 등의 데이터를 처리하기 위한 로직등을 맡아서 처리
### 퍼시스턴스 계층(리포지토리)
- 데이터베이스 관련 로직 처리
- 도메인 객체(DAO)를 참조하여 데이터베이스 계층과 상호작용

## 회원 도메인과 리포지토리 구현
***
- 회원 객체
```java
package hello.hello_spring.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // 롬복 라이브러리를 사용하여 get 메서드와 set메세드를 간단히 구현
public class Member {
    private Long id;
    private String name;
    // public Long getId(){
    // return id;
    // }
    // ...
}
```
- 회원 리포지토리 인터페이스
```java
package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository { // 구현체에 따라 구체적인 메서드들이 결정되는 인터페이스 정의
    Member save(Member member);

    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
```
현재 실습에서는 아직 데이터베이스가 선정되지 않았다고 가정했기에 리포지토리 인터페이스를
먼저 구현. 
데이터베이스가 정해지더라도 구현체만 변경하면되기에 매우 편함

- 회원 리포지토리 인터페이스 구현체(메모리 기반)
```java
package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository { // MemberRepository 인터페이스 구현

    public static Map<Long, Member> members = new HashMap<>(); // Map 자료구조를 통한 (기본 키, 이름) 쌍 형식의 메모리 기반 데이터베이스
    public static long sequence = 0L; // 기본키 설정을 위한 초기화 

    @Override // 인터페이스에서 선언된 메서드 오버라이드(구현)
    public Member save(Member member) { // 멤버 객체를 받아 데이터베이스에 저장 후 저장한 멤버 객체 반환
        member.setId(++sequence);
        members.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) { // 기본 키 값으로 데이터베이스에서 멤버 객체 조회
        return Optional.ofNullable(members.get(id)); 
    }

    @Override
    public Optional<Member> findByName(String name) { // 멤버의 이름을 통한 조회
        return members.values().stream()
                .filter(m -> m.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() { // 모든 멤버 객체 조회
        return new ArrayList<>(members.values());
    }

    public void clear() { // 데이터베이스 초기화
        members.clear();
    }
}
```
## 📌여기서 잠깐!
***
### 자바 문법에 관하여
1. Optional<>  
객체를 Optional로 감싸게 되면 지원되는 다양한 메서드들로 인해 null값 처리가 간편해진다.
2. ofNullable()  
Optional<>로 감싼 객체에 대해서 지원되는 메서드로 메서드의 인자가 존재하면
객체에 Optional<>을 감싼 형태로 반환하고, null이면 Optional.empty()를 반환하여
이에 따른 후속조치를 할 수 있다.
3. stream()문법  
stream() 컬렉션 자료구조 형태에서 순차적인 데이터를 처리할 수 있도록 도와주는
API로 다양한 메서드들을 지원한다.
4. filter()  
전체 스트림 내에서 람다 함수로 제공받은 조건이 참이면 다음 연산에 포함시키고
그렇지 않으면 넘긴다.
## 테스트 코드 작성
***
### 테스트 코드를 작성하는 이유
- 개발한 기능을 main메서드나 컨트롤러를 사용해서 테스트 할 경우 실행하는것이 번거롭고
오래걸린다.
- 반복 실행하기 힘들고 여러 테스트를 한번에 수행하기 어렵다.
- 자바에서는 JUnit이라는 프레임워크로 테스트를 실행하여 이러한 문제들을 해결한다.
```java
package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach // 각 테스트가 수행된 뒤 아래 메서드를 수행하도록 하는 어노테이션
    public void clearMembers() {
        memberRepository.clear(); // 데이터베이스 초기화
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        memberRepository.save(member);

        Member result = memberRepository.findById(member.getId()).get();

        assertThat(result).isEqualTo(member); // Assertj 라이브러리에서 제공되는 메서드로 전달한 값이 기대한 값과 같은지 비교
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring");
        memberRepository.save(member2);

        Member result = memberRepository.findByName(member1.getName()).get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring");
        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("spring");
        memberRepository.save(member2);

        List<Member> members = memberRepository.findAll();

        assertThat(members.size()).isEqualTo(2);
    }

}
```
## 회원 서비스 개발
***
```java
package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired //의존성 주입
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    // 회원 가입
    public Long join(Member member) {
        validateMember(member); // 중복 회원 검증 비즈니스 로직
        return memberRepository.save(member).getId();
    }

    private void validateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m ->{ // Optional<> 에서 지원하는 메서드 존재할 시 매개변수 m에 값이 들어오면 람다 함수 실행
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 전체 회원 조회
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
}
```
## 회원 서비스 테스트 코드 작성
***
```java
package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach // 각 테스트가 수행되기 전 아래 메서드를 먼저 수행하도록 하는 어노테이션
    public void beforeEach() { // 객체 생성 및 의존성 주입
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clear();
    }

    @Test
    void 회원가입() {
        Member member = new Member();
        member.setName("spring");

        Long result = memberService.join(member);
        Member findMember = memberService.findById(result).get();

        assertThat(findMember.getName()).isEqualTo("spring");

    }

    @Test
    void 중복_회원_예외() {
        Member member = new Member();
        member.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member);

//        assertThrows(IllegalStateException.class, () -> memberSerivice.join(member2));

        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
    }
}
```
## 컴포넌트 스캔과 자동 의존관계 설정
```java
package hello.hello_spring.controller;

import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService; // Controller는 MemberService를 의존하고 있음

    @Autowired // 생성자를 통한 의존성 주입이 가능하도록 하는 어노테이션
    public MemberController(MemberService memberService) { 
        this.memberService = memberService;
    }
}
```
위 코드를 살펴보면 MemberController는 MemberService를 의존하고 있다.
이 경우 스프링의 도움이 없다면 
```java
private final MemberService memberService = new MemberService();
```
이와 같은 형식으로 직접 객체를 생성하여야 한다.
그러나 스프링에서 지원하는 컴포넌트 스캔과 스프링 빈 컨테이너를 활용하면
의존성을 개발자가 직접 설정하는 것이 아닌 스프링이 필요한 곳에 직접 의존성을 주입해준다.
### 컴포넌트 스캔
스프링은 컴포넌트 스캔을 통해 @Component 어노테이션이 붙은 클래스를 확인하고 이들을 스프링 빈으로 등록하여 스프링 빈 컨테이너로
관리한다. 이를 통해 스프링은 각 클래스가 의존하고 있는 다른 클래스에 대하여 필요한 의존성을 주입해준다.
### 컴포넌트
컴포넌트는 컴포넌트 스캔의 대상이 되는 클래스로 @Component로 나타낸다. 우리가 만든 컨트롤러와 서비스, 레포지토리에도
@Component 어노테이션이 각 계층을 나타내는 어노테이션의 내부적으로 붙어있음을 확인할 수 있다.
```java
package org.springframework.stereotype;

@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
@org.springframework.stereotype.Component // 컨트롤러 어노테이션 내부적으로 붙어있음
public @interface Controller {
    @org.springframework.core.annotation.AliasFor(annotation = org.springframework.stereotype.Component.class)
    java.lang.String value() default "";
}
```
## 자바 코드로 직접 스프링 빈 등록하기
***
### 만약 계층을 나타내는 어노테이션을 쓰지 않는다면?
```java
package hello.hellospring;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링의 설정 클래스를 나타냄. 내부적으로 정의된 @Bean 메서드들을 스프링 빈으로 등록
public class SpringConfig { 
    
    @Bean // 메서드가 반환하는 객체를 스프링 컨테이너에 빈으로 등록   
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
 }
```
따라서 스프링 빈을 등록하는 방법은 두 가지 이다.
1. 컴포넌트 스캔을 통한 자동 빈 등록
2. @Configuration 과 @Bean 메서드를 통한 자바 코드로 등록
