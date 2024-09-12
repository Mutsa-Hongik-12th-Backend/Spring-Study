# 🌱 SPRING BOOT 입문 섹션 4-5

## 1. 회원 관리 예제 - 백엔드 개발

### 1.1 비즈니스 요구사항 정리
- 데이터: 회원 ID, 이름
- 기능: 회원 등록, 조회
- 아직 데이터 저장소가 선정되지 않았다고 가정함.

#### 일반적인 웹 애플리케이션 계층 구조
![웹 애플리케이션 계층 구조](https://github.com/user-attachments/assets/ff71e3af-e0eb-43bd-a085-aedb0a9674a6)

#### 클래스 의존 관계
![클래스 의존 관계](https://github.com/user-attachments/assets/0d1328a3-7753-4710-9ac0-f37834b90c90)

- 아직 데이터 저장소가 선정되지 않았으므로, 인터페이스로 구현 클래스를 변경할 수 있도록 설계함.
- 데이터 저장소는 RDB, NoSQL 등 다양한 저장소를 고려하고 있다고 가정함.
- 개발 진행을 위해 초기 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소를 사용함.

---

### 1.2 회원 도메인과 리포지토리 만들기

#### 회원 객체
- 회원 id, 이름을 private으로 선언하고, getter와 setter 메소드 작성함. 

#### 회원 리포지토리 인터페이스
- 회원 관련 데이터 처리를 담당하는 리포지토리 역할을 구현함.
- 회원 정보 저장, 조회 기능을 정의함.

#### 회원 리포지토리 메모리 구현체
- 메모리 상에서 회원 정보 저장 및 관리 기능을 제공함.
- Optional을 사용하여 데이터 조회 결과를 안전하게 처리함.
  - return Optional.ofNullable(store.get(id)); 문에서 값이 Null이어도 Optional로 감싸주어 클라이언트에서 무언가를 할 수 있게 해줌.

---

### 1.3 회원 리포지토리 테스트 케이스 작성
- 개발한 기능을 실행해서 테스트할 때
  - 자바의 main 메서드를 통해서 실행
  - 웹 애플리케이션의 컨트롤러를 통해서 실행
  - 단점
    - 준비하고 실행하는 데 오래 걸림.
    - 반복 실행이 어려움.
    - 여러 테스트를 한번에 실행하기 어려움.

    => junit으로 테스트를 실행하여 해결

- get() 으로 Optional 값을 꺼내올 수 있지만, 권장하는 방법은 아님.
- 한번에 여러 테스트를 실행하면 메모리 DB에 이전 테스트 결과가 남아 다음 테스트를 실패할 수 있음.
  - @AfterEach 를 사용하여 각 테스트가 종료될 때마다 메모리 DB에 저장된 데이터를 삭제함!
- 테스트는 각각 독립적으로 실행되어야 하며, 테스트 순서에 의존 관계가 있는 것은 바람직하지 않음.

---

### 1.4 회원 서비스 개발
- 리포지토리는 기계적으로 설계함.
- 반면, 서비스는 비즈니스에 의존적으로 설계함.
- 회원가입 서비스 개발 시, 중복 회원 검증하는 validateDuplicateMember 함수 따로 빼주기

---

### 1.5 회원 서비스 테스트
- 단위 테스트 코드를 생성할 클래스명에 커서를 두고, Alt + Enter를 입력하여 서비스 테스트를 생성함.
- given, when, then 주석을 나눠 테스트 설계하는 것을 권장함.
- 예외를 발생시켜보는 테스트도 매우 중요함.
- @BeforeEach: 각 테스트 실행 전에 호출되고, 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존 관계도 새로 맺어줌.

---

## 2. 스프링 빈과 의존관계

### 2.1 컴포넌트 스캔과 자동 의존 관계 설정
- 회원 컨트롤러가 회원 서비스와 회원 리포지토리를 사용할 수 있게 의존 관계를 준비해야 함.
  ```java
  @Controller
  public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
      this.memberService = memberService;
    }
  }
  ```
  - **DI(Dependency Injection, 의존성 주입)**: 객체 의존 관계를 외부에서 넣어주는 것 
    - 생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌.

#### ⚠️ 오류 발생 - memberService가 스프링 빈으로 등록되어 있지 않음!
![memberService가 스프링 빈으로 등록되어 있지 않음](https://github.com/user-attachments/assets/d0433929-7f3c-479e-b6f9-7b0cec78b5f6)

```java
Consider defining a bean of type 'hello.hellospring.service.MemberService' in
your configuration.
```

- @Controller 가 있으면 스프링 빈으로 자동 등록됨.

#### 스프링 빈 등록 방법
1. 컴포넌트 스캔과 자동 의존 관계 설정하기
2. 자바 코드로 직접 스프링 빈 등록하기

---

### 2.2 컴포넌트 스캔과 자동 의존 관계 설정하기

#### 컴포넌트 스캔 원리
- @Component annotation이 있으면 스프링 빈으로 자동 등록됨.
- 컴포넌트 스캔 덕분에 @Controller 컨트롤러가 스프링 빈으로 자동 등록됨.
- @Component를 포함하는 다음 annotation도 스프링 빈으로 자동 등록됨.
  - @Controller
  - Service
  - Repository

#### 회원 서비스 스프링 빈 등록
```java
@Service
public class MemberService {
  private final MemberRepository memberRepository;
  
  @Autowired
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
}
```
- 생성자에 @Autowired 를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서 주입함.
- 생성자가 1개만 있으면 @Autowired는 생략할 수 있음.

#### 회원 리포지토리 스프링 빈 등록
```java
@Repository
public class MemoryMemberRepository implements MemberRepository {}
```

![스프링 빈 등록 이미지](https://github.com/user-attachments/assets/9b8bf2bd-7fa6-4abf-a6a5-9fbb61090d95)
- memberService와 memberRepository가 스프링 컨테이너에 스프링 빈으로 등록됨.

---

### 2.3 자바 코드로 직접 스프링 빈 등록하기
- 회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired annotation을 제거하고 진행!
- DI에는 필드 주입, setter 주입, 생성자 주입 3가지 방법이 있음.
  - 필드 주입: 바꿀 수 있는 방법이 없기 때문에 좋지 않음.
  - setter 주입: public으로 설정해야 하기 때문에 외부 호출의 위험이 있음.
  - 생성자 주입: 생성자를 통해서 서비스가 컨트롤러에 주입되는 것으로, 의존 관계가 실행 중에 동적으로 변하는 경우는 거의 없기 때문에 생성자 주입을 권장함.
- 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록하여 다른 코드를 손대지 않아도 됨.
- @Service 통한 서비스 등록 없이 @Autowired 사용하면, 스프링에 등록되어 있지 않은 상태이기 때문에 연결되지 않아 동작하지 않음. 
