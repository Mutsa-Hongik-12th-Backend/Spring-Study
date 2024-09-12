# 🏷️WEEK 2 회원 관리 예제, 스프링 빈과 의존 관계
[스프링 입문 - 코드로 배우는 스프링 부트] 섹션 4-5을 수강하고 TIL을 작성하였습니다.
## 🔎 회원 관리 예제 - 백엔드 개발
### 1️⃣ 비즈니스 요구사항 정리
---------------------
데이터: 회원ID, 이름 / 기능: 회원 등록, 조회
![일반적인 웹 애플리케이션 구조](https://github.com/user-attachments/assets/204e3b0c-62b5-4391-a4df-4207910669df)

![인터페이스, 데이터 저장소](https://github.com/user-attachments/assets/bd7fe7d6-7da1-41ba-94c1-980f5063ca27)

### 2️⃣ 회원 도메인과 리포지토리 만들기
-----------------------
src/main/java/hello.yunakang 에 `domain` 패키지 생성 → 그 안에 `Member` 클래스 생성


또한, hello.yunakang 안에 `repository` 패키지 생성 → 그 안에 class로 하는데 `MemberRepository` 인터페이스로 생성

`Optional` ; java 8에 들어감, null 처리 방식 다룸

repository 패키지 안에 `MemoryMemberRepository` 클래스 생성

### 3️⃣ 회원 리포지토리 테스트 케이스 작성
--------------------------
테스트 시, 자바의 main 메서드를 통해 실행 or 웹 애플리케이션의 컨트롤러를 통해 해당 기능을 실행. 

<= 준비하고 실행하는데 오래 걸리고, 반복 실행하기 어렵고 여러 테스트를 한번에 실행하기 어려움. 

⇒ 자바는 `JUnit`이라는 프레임워크로 테스트를 실행하며 문제 해결!!

test/hello.hellospring에 `repository` 패키지 생성 ⇒ 그 안에 `MemoryMemberRepositoryTest` 클래스 생성

`get()` : Optional 쓸 때 값 꺼내는 함수, 좋은 방법은 x

test별로 돌릴 수도 있고, 전체 다 test 돌릴 수 o

but, 모든 test는 순서가 정해져 있지 x ⇒ 순서에 의존적이면 x,  

`afterEach()` 실행 : test 끝날 때마다 실행, 여기서는 메모리 DB에 저장된 데이터를 삭제한다.

**정상 출력**
![정상 출력](https://github.com/user-attachments/assets/3dfcfdc8-bb2c-4323-bc5c-cfbff5d7b04e)

**코드 순서 의존적이어서 나타나는 error**
![코드 순서 의존적이어서 나타난 에러](https://github.com/user-attachments/assets/b3e60554-6041-4165-89a9-6c5938d4116b)

**error 수정 후 전체 test 돌리기**
![전체 test 돌리기](https://github.com/user-attachments/assets/54ba1345-931b-44b3-8426-dbd840d1af3d)

### 4️⃣ 회원 서비스 개발
main/java/hello.yunakang에서 `service`  패키지 생성 ⇒ 그 안에 `MemberService` 클래스 생성

ctrl + t로 따로 메서드 뽑아내기 `validateDuplicateMember` 함수 뽑아내기

**⇒ service는 용어가 비즈니스 방향, repository는 비교적 개발자 친화적인 용어**

### 5️⃣ 회원 서비스 테스트
class 가리키고, `alt+enter` ⇒ CREATE new test 

testing library : Junit5 밑에 함수 다 체크! 

test 함수명은 한글로 적어도 됨!

test 코드 작성 시 given, when, then(검증) 주석 많이 사용, test는 정상 flow보다 예외 flow가 더 중요함

중복_회원_예외 함수에서 try-catch문 잘 쓰지 x

`@BeforeEach`: 각 테스트 실행 전에 호출된다. 

테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어준다 + `@AfterEach` 도 추가해주기

**회원 서비스 테스트**
![회원 서비스 테스트](https://github.com/user-attachments/assets/1a47ce18-de91-4a82-a323-b22ffe932b03)


## 🌱스프링 빈과 의존 관계
### 1️⃣ 컴포넌트 스캔과 자동 의존 관계 설정
---------------------
main/java/hello.yunakang/controller에서 `MemberController` class 생성

`MemberService`에서 **@Service, @Autowired** 넣어주고, `MemoryMemberRepository`에서는 **@Repository** 넣어주기

**컴포넌트 스캔 코드 추가 이후 실행 확인**
![코드 추가 이후 실행 확인](https://github.com/user-attachments/assets/13e4a140-ef92-41da-9b97-151382fb12f5)


**<스프링 빈을 등록하는 2가지 방법>**

** 스프링 빈 등록 이미지**
![스프링 빈 등록 이미지](https://github.com/user-attachments/assets/ed7289d1-1683-4be0-8ec8-4f9a8fe17dac)

- 컴포넌트 스캔(위의 방법처럼 @ 사용하는 방법)⇒ 자동 의존관계 설정
- 자바 코드로 직접 스프링 빈 등록하기

  **<컴포넌트 스캔 원리>**
  - @Component 있으면 스프링 빈으로 자동 등록된다.
  - @Controller 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문이다.
  - @Component를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다.
    - @Controller
    - @Service
    - @Repository

  스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 **싱글톤으로 등록**(유일하게하나만 등록해서 공유)
  
  따라서 같은 스프링 빈이면 모두 같은 인스턴스! 설정으로 싱글톤이 아니게 설정할 수 있지만, 특별한 경우를 제외하면 대부분 싱글톤을 사용


### 2️⃣ 자바 코드로 직접 스프링 빈 등록하기
---------------------------
회원 서비스와 회원 리포지토리의 @Service, @Repository, @Autowired 애노테이션을 제거하고 진행

1. memberservice와 memorymemberrepository에서도 애노테이션 다 제거
2. main/java/hello.yunakang에서 `SpringConfig` 클래스 추가
3. 실행해보기

**실행 결과**
![실행 결과](https://github.com/user-attachments/assets/28776789-e27c-4777-993e-188bdd05cdb9)

DI(dependency Injection) 에는 필드 주입, setter 주입, 생성자 주입 이렇게 3가지 방법이 존재 의존 관계가 실행중에 동적으로 변하는 경우는 거의 없으므로 **생성자 주입을 권장!!**

← setter 주입으로 하는 경우, public으로 열려 있어야 하는데 훼손될 가능성 o

실무에서는 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드는 컴포넌트 스캔을 사용, 그리고 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 빈으로 등록; 직접 설정 파일을 가질 때의 장점!


## 🧡 정리 노션

<https://amber-humor-4f2.notion.site/WEEK-2-11fcde1e200f472ba42126a1b8446504?pvs=4>






