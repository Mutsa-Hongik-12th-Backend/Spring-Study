### 회원 관리 예제
- 데이터 : 회원 id, 이름
- 기능 : 회원 등록, 조회
- 데이터 저장소는 아직 선정되지 않음
    
    → MemberRepository(interface - 추후 RDB, JPA 등으로 변경하기 위함)와 “Memory” MemberRepository 생성(가벼운 메모리 기반의 데이터 저장소)

### 테스트 케이스 작성

테스트 케이스는 협업 시 필수적인 “프로그램의 특정 경로를 실험”을 위한 집합이다.

*정상 플로우도 중요하지만, 예외플로우를 다루는 게 훨씬 중요하다

class → cmd + shift + T : test 자동 생성

```java
    @Test
    void join_회원가입() {
        //given - 주어진 것

        //when - 실행할 것

        //then - 검증할 것 (결과)
    }
```

---

```java
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("already exists");
        }
```

→

```java
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("already exists");
```

### 스프링 빈과 의존관계

스프링 컨테이너에 등록을 하면 하나만 등록이 되고 여러번 객체를 생성할 필요가 없다

스프링은 스프링 빈을 등록할 때 기본적으로 **싱글톤**으로 등록한다.

**등록 방법**

- **컴포넌트** 스캔 (@Service → @Component) + 자동 의존성 주입
- 자바 코드로 직접 스프링빈 등록

```java
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
```

@controller - 외부 요청 받기

@service -비즈니스 로직

@repository -데이터 저장

- controller - service 연결 시 @Autowired : 멤버 컨트롤러 생성 시 스프링 빈에 등록되어있는 멤버서비스 객체를 넣어줌 [의존성 주입]

`package hello.hello_spring;`

→ hello-spring 을 포함한 하위 패키지들은 자동으로 스프링빈으로 등록. 아닌 애들은 컴포넌트 스캔의 대상이 아니다.
