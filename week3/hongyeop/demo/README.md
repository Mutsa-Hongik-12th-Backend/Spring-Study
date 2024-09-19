# 🌱 SPRING BOOT 세션2 실습

## 1. 코드 파헤치기

### 1.1 Entity 클래스에서 빌더 패턴 사용하기

#### domain에서 Member 클래스 정의하기
```java
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;

    private String email;

    @CreatedDate
    private LocalDateTime createdAt;
}
```

#### 빌더 패턴 사용하여 service에 객체 생성 옵션 제공하기
```java
public Member createMember(MemberRequestDTO memberRequestDTO) {
    Member member = Member.builder()
          .name(memberRequestDTO.name())
          .email(memberRequestDTO.email())
          .build();

    Member savedMember = memberRepository.save(member);
    return savedMember;
}
```

#### 빌더 패턴을 사용하는 이유
- __불변성__ : setter를 사용하는 대신에, 빌더 패턴을 사용하여 객체를 불변으로 만들 수 있다. 상태가 변하지 않기 때문에 안전성을 보장한다.
- __유연성__ : 필드의 종류와 순서를 선택적으로 유연하게 설정할 수 있다.

---

### 1.2 Controller로 클라이언트의 요청(url, 메소드) 매핑하고 처리하기

#### @RequiredArgsConstructor 어노테이션 사용하기
- Lombok 라이브러리에서 제공하며, 클래스 내의 final 필드를 초기화할 수 있는 생성자를 자동으로 생성한다.
- final 필드로 선언된 의존성을 생성자를 통해 주입할 수 있게 된다.
  - 스프링에서는 의존성 주입 시 생성자 주입을 권장한다!

#### @RestController 어노테이션 사용하기
- 스프링 프레임워크에서 주로 __RESTFUL__ 한 웹 서비스를 만들기 위해 사용한다.
- 기본적으로 __Controller__ 역할을 하며 __JSON__ 이나 __XML__ 등의 데이터 형식을 반환하는 데 최적화된 기능을 제공한다.
- 뷰를 반환하지 않고, JSON이나 XML 같은 데이터만을 반환한다.

#### @RequestMapping("url명") 사용하여 url 매핑하기 + 
#### @PostMapping, @GetMapping 사용하여 메소드 매핑하기
```java
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> createMember(@RequestBody MemberRequestDTO memberRequestDTO) {
        Member member = memberService.createMember(memberRequestDTO);
        return ResponseEntity.status(201).body(member);

    }

    @GetMapping
    public ResponseEntity<?> getMemberList() {
        try {
            List<Member> response;
            response = memberService.getMemberList();
            if (response.isEmpty()) {
                return ResponseEntity.status(200).body("사용자가 존재하지 않습니다.");
            }
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            return ResponseEntity.status(500).body("서버 내부 에러");
        }
    }
}
```

---

### 1.3 JpaRepository 사용하기

#### JPA는 뭘까?
- __JPA__ 는 자바 객체와 데이터베이스 테이블 사이의 매핑을 정의하고, 데이터베이스의 데이터를 객체로 다루는 방법을 제공하는 ORM 표준
- SQL 없이도 데이터베이스의 데이터를 다룰 수 있게 해준다.

#### JpaRepository는 뭘까?
- JPA에서 제공하는 기본적인 CRUD 작업과 데이터베이스 쿼리를 처리할 수 있는 Repository 인터페이스
- 기본 CRUD 작업 기능
  - save(), findById(), deleteById(), ...
- 페이징 및 정렬
  - findAll(Pageable pageable)
- 사용자 정의 쿼리 메소드
  - 사용자가 선언한 메소드에 대해 자동으로 해당 조건에 맞는 SQL 쿼리를 생성한다.

#### JpaRepository 상속받기
```java
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```

- 이 레포지토리가 관리하는 entity 클래스인 Member의 PK 타입이 Long으로 설정한다는 의미이다.
- __JpaRepository__ 를 상속받았으므로 별도의 구현 없이 기본적인 CRUD 작업 사용 가능하다.

---

### 1.4 DTO 사용하기

#### DTO는 뭘까?
- Data Transfer Object의 약자로, 데이터를 전송하고 관리하는 역할을 한다.
- 주로 데이터 계층과 애플리케이션의 다른 계층 간에 데이터를 전송할 때 사용된다.
- 데이터 유효성 검사와 변환을 중앙 집중화하여 일관된 방식으로 데이터를 처리할 수 있게 한다.

#### Record를 사용하여 DTO 정의하기
```java
public record MemberRequestDTO(String name, String email) {
}
```

- name과 email 필드를 가진 Record를 생성하며, Record 클래스를 통해 DTO를 정의하고 있다.
- DTO 객체를 정의하면서 자동으로 필요한 메소드들을 생성한다. 

---

## 2. 코드 구현 도중 있었던 이슈

### 2.1 h2 데이터베이스 실행
- 윈도우 h2 실행 커맨드는 __h2.bat__
- H2\bin\ 디렉토리 들어가서 h2.bat 입력

### 2.2 ⚠️Response Body의 createdAt 필드가 null로 반환되는 문제⚠️
- __LocalDateTime__ 사용할 때는 __main__ 클래스에서 __@EnableJpaAuditing__ 어노테이션 추가해줘야 한다!
  ```java
  public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;

    private String email;

    @CreatedDate
    private LocalDateTime createdAt;
  }
  ```

  ```java
  import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

  @EnableJpaAuditing
  public class DemoApplication {

	  public static void main(String[] args) {
		  SpringApplication.run(DemoApplication.class, args);
	  }
  }
  ```

---

## 3. 포스트맨 화면 캡쳐

### 3.1 POST 메소드로 member 등록
![Member 등록](https://github.com/user-attachments/assets/43ef7e3b-9239-4d6c-9334-a3b4f9206424)

### 3.2 GET 메소드로 member 전체 조회
![Member 전체 조회](https://github.com/user-attachments/assets/b1312f54-e685-4fad-9f96-e5328ac4cf71)

### 3.3 GET 메소드로 member 한명 세부 조회
![Member 세부 조회](https://github.com/user-attachments/assets/b1e8bdee-c36c-439d-ad41-0a17b27c5e8c)
