
## 1️⃣ 회원관리 예제

### 1. 비즈니스 요구사항 정리

- 데이터 : 회원ID, 이름
- 기능 : 화원등록, 조회

일반적인 웹 애플리케이션 계층 구조

![image.png](README/image.png)

**컨트롤러**: 웹 MVC의 컨트롤러 역할
**서비스**: 핵심 비즈니스 로직 구현
**리포지토리**: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
**도메인**: 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등등 주로 데이터베이스에 저장하고 관리됨

![image.png](README/image%201.png)

데이터 저장소가 선정되지 않아서 우선 메모리 기반의 데이터 저장소 사용

### 2. 회원 도메인과 리포지토리 만들기

```java
public class MemoryMemberRepository implements MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    // null값 때문에 optional로 감싸야 한다
    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                    .filter(member -> member.getName().equals(name))
                    .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }
}
```

`@Override` 어노테이션

### 3. 회원 리포지토리 테스트 케이스 작성

개발한 기능을 테스트할 때 자바의 main 메서드를 실행하기

- 방법을 준비하는 데 시간이 많이 걸림
- 반복적인 테스트를 행하기 어려움

⇒ 테스트 케이스로 해결!

`Assertions.assertEquals(a, b)` : 테스트에서 제공함 (터미널에 값이 나오진 않지만 실행이 성공적으로 됐는지 확인가능하다)

`Assertions.assertThat(a).isEqualTo(b)` : a가 b랑 동일한지 비교

*shift + F6 - 변수명 한번에 바꾸기

- 테스트는 순서를 보장하지 않음
    - 테스트가 끝날 때마다 메모리가 지워지도록 만들어야함
    - ⇒ 서로 의존관계가 되지 않게 설계해야 함
    - `store.clear()` 추가해야함

```java
 @Test
    public void save() {
        //given
        Member member = new Member();
        member.setName("spring");
        //when
        repository.save(member);
        //then
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }
```

`Member result = repository.findById(member.getId()).get();` 에서 '`isPresent()' 검사가 없는 'Optional. get()'` 라는 경고창

: optional이 빈값일 때의 가능성을 고려하지 않아서 생기는 경고창

```java
Member result = repository.findById(member.getId()).isPresent() ? 
                        repository.findById(member.getId()).get() : null;
```

`isPresent()` 을 포함하여 빈값일 때의 코드도 추가함

![image.png](README/image%202.png)

테스트를 실행했을 때 다 통과하는 것을 볼 수 있음

테스트를 먼저 만들고 그 다음에 설계를 하는 것 ⇒ 테스트 주도 개발(TDD)

### 4. 회원 서비스 개발

```java
//같은 이름이 있는 중복 회원x
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
```

`memberRepository.findByName` 가 차피 optional이여서 바로 ifPresent로 사용가능

`validateDuplicateMember(member);` 로 메서드로 뽑아서 리펙토링

*control + t - 리펙토링 관련

서비스에서는 비즈니스에 의존하여 설계함 → 용어 선택도 비즈니스적이게 작성

```java
public class MemberService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    /**
     *  회원가입
     */
    public Long join(Member member) {

        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     *  전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     *   회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
```

### 5. 회원 서비스 테스트

*command+shift+t - 새로운 테스트 생성 단축기

```java
@Test
    void join() {
        //given
        
        //when
        
        //then
    }
```

```java
//외부에서 memberRepository를 바꾸도록 변경함
    public MemberService(MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }
```

```java
 		MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
```

memberService에서 직접 넣지 않고 외부에서 접근할 수 있도록 함

<aside>
🌱 DI (**Dependency Injection) : 의존성 주입**

- 한 객체가 어떤 객체(구체 클래스)에 의존할 것인지는 별도의 관심사이다. Spring은 의존성 주입을 도와주는 DI 컨테이너로써, 강하게 결합된 클래스들을 분리하고, 애플리케이션 실행 시점에 객체 간의 관계를 결정해 줌으로써 결합도를 낮추고 유연성을 확보해준다
- 외부에서 객체 간의 관계(의존성)를 결정해 주는데 즉, 객체를 직접 생성하는 것이 아니라 외부에서 생성 후 주입시켜 주는 방식

</aside>

1.  필드 주입
2. setter 주입
    - public하게 노출되어 있어 바뀔 위험이 있음
3. 생성자 주입
    - 스프링 컨테이너에 올라가고 조립시점에 딱 올라가고 더이상 변경못하게 막는다
    
    ⇒ 결론 : 생성자 주입이 좋다~
    
    의존관계가 실행중에 동적으 로 변하는 경우는 거의 없으므로 생성자 주입을 권장
    

---

## 2️⃣ 스프링 빈과 의존관계

### 1. 컴포넌트 스캔과 자동 의존관계 설정

멤버 컨트롤러가 멤버 서비스에 의존하도록 만들기

컨트롤러 → 스프링 컨테이너가 관리

```java
@Autowired // 스프링 컨테이너에서 컨트롤러를 가져오는 애노테이션
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
```

member 클래스는 순수한 자바 클래스이기 때문에 스프링에서 알 수 없음

@Service 와 @Repository 를 추가하여 해결

<aside>
🌱 **스프링 빈을 등록하는 2가지 방법**

```
빈(Bean)은 스프링 컨테이너에 의해 관리되는 재사용 가능한 소프트웨어 컴포넌트이다.즉, 스프링 컨테이너가 관리하는 자바 객체를 뜻하며, 하나 이상의 빈(Bean)을 관리한다.
```

- 컴포넌트 스캔과 자동 의존관계 설정
    - @Service 도 컴포넌트 스캔임
        
        → 안에 열어보면 @Component 붙어있음
        
- 자바 코드로 직접 스프링 빈 등록하기
</aside>

- `@Component` 애노테이션이 있으면 스프링 빈으로 자동 등록된다.
- `@Controller` 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
- `@Component` 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
    - `@Controller`
    - `@Service`
    - `@Repository`
- `@Autowired` 는 각 컨트롤러 서비스, 레포지토리를 연결하는 선(?)
- 패키지 내에 있는, 또는 포함한 파일들만 스프링이 보고 컴포넌트 스캔이 적용된다 `package hello.hello_spring.*;`

![image.png](README/image%203.png)

`스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다`

싱글톤 : 유일하게 하나만 등록하여 공유한다 ⇒. 즉, 같은 스프링 빈이면 같은 인스턴스

### 2. 자바코드로 직접 스프링 빈 등록하기

```java
package hello.hellospring;
 import hello.hellospring.repository.MemberRepository;
 import hello.hellospring.repository.MemoryMemberRepository;
 import hello.hellospring.service.MemberService;
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

```

configuration을 스프링이 읽고 아래 bean에 있는 내용을 등록한다

memberservice와 memberrepository를 스프링 빈에 올리고

컨트롤러에 있는 해당 스프링 빈에 넣어준다

|  | 장점 | 단점 |
| --- | --- | --- |
| **컴포넌트 스캔** | 동적인 빈 등록을 자동적으로 등록할 수 있게 하기 위함 | 레포지토리 변경이 있을 때 여러 파일의 애노테이션을 바꿔야 함, 등록할 스프링 빈 개수가 증가할 수록 설정 정보는 커지게 되고 설정 정보를 누락하는 문제 |
| **자바 코드** | 설정 파일만 바꾸면 된다 |  |