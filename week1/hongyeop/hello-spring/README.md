# 🌱 SPRING BOOT 입문

## 1. 프로젝트 설정

### 1.1 프로젝트 생성하기
[스프링 부트 스타터 사이트](https://start.spring.io)로 이동 후, 스프링 프로젝트 생성.

![스프링 부트 프로젝트 생성](https://github.com/user-attachments/assets/93cad170-6347-452e-a0ba-c78f8c2dc30c)

#### ⚠️ 주의해야 할 점 - JAVA 버전 올바르게 선택하기
- `main` 함수 실행 버튼이 보이지 않아서 JAVA 버전을 17로 선택했을 때, 문제가 발생.
- `cmd`에서 `java -version` 명령어로 설치된 버전을 꼭 확인하기.

#### IntelliJ 설정
- Gradle로 실행하면 속도가 느리므로 자바로 바로 실행하기.

![IntelliJ 설정](https://github.com/user-attachments/assets/f7dfd318-4a6c-430d-ab66-6084ae67c29f)

- `Shift + Ctrl + Alt + S` 키로 프로젝트 JDK 설정 확인!

![JDK 설정](https://github.com/user-attachments/assets/808a826b-fd6d-41d5-b75d-e5760624c986)

---

### 1.2 라이브러리 살펴보기

#### 스프링 부트 라이브러리
- **spring-boot-starter-web**
  - spring-boot-starter-tomcat: 톰캣(웹서버)
  - spring-webmvc: 스프링 웹 MVC
- **spring-boot-starter-thymeleaf**: 타임리프 템플릿 엔진(View)
- **spring-boot-starter (공통)**: 스프링 부트 + 스프링 코어 + 로깅
  - spring-boot
  - spring-boot-starter-logging

#### 테스트 라이브러리
- **spring-boot-starter-test**

---

### 1.3 View 환경설정

#### 스프링 부트가 제공하는 Welcome Page 만들기
- `src/main/resources/static/index.html` 파일 생성 시, 정적 페이지 기능 제공.

#### 타임리프 템플릿 엔진으로 동적 페이지 만들기
- Controller에서 리턴 값으로 문자열 반환 시, `viewResolver`가 화면을 찾아 처리.
- 기본 경로: `resources:templates/ + {ViewName} + .html`

![Thymeleaf 사용](https://github.com/user-attachments/assets/ce0d180f-f44f-47c3-82ac-eb755411d7ae)

---

### 1.4 빌드하고 실행하기

1. 프로젝트 디렉토리에서 `gradlew build` 명령어 입력 → `build` 디렉토리 생성.
2. `cd build/libs/` 이동.
3. `java -jar ./hello-spring-0.0.1-SNAPSHOT.jar` 입력 후 서버 실행.
   - `localhost:8080` 및 `localhost:8080/hello` 접속하여 확인 가능.

---

## 2. 스프링 웹 개발 기초

### 2.1 정적 컨텐츠
- 파일을 그대로 웹 브라우저에 전송.
- `src/main/resources/static/hello-static.html` 생성 후, `localhost:8080/hello-static.html`에서 확인 가능.

![정적 컨텐츠](https://github.com/user-attachments/assets/dfd678e4-c5a5-4227-b42e-244c337b159b)

---

### 2.2 MVC와 템플릿 엔진
- 파일 변형 후, 웹 브라우저에 전송.
  
  ```java
  @GetMapping("hello-mvc")
  public String helloMvc(@RequestParam("name") String name, Model model) {
      model.addAttribute("name", name);
      return "hello-template";
  }

* RequestParam 사용할 때에는 url에 query parameter를 넣어줘야 Warn이 뜨지 않음!
  + 안 넣어주면 WARN ... Required request parameter 'name' for method parameter type String is not present] 오류 창 뜨니 주의하기
  + @RequestParam(value="name", required=false) 대신 입력하면 오류 발생 x

* 실행 후, localhost:8080/hello-mvc?name=spring! 접속하여 확인 가능

---

### 2.3 API

* `@ResponseBody`를 사용하면 `viewResolver`를 사용하지 않고, HTTP Response Body에 직접 내용을 반환

* `localhost:8080/hello-api?name=spring`에 접속하여 확인 가능

#### `@ResponseBody` 동작 원리
![ResponseBody 동작 원리](https://github.com/user-attachments/assets/ac5eea02-3c53-4b7d-b115-1c522fc6474e)
- **HTTP BODY**에 문자열 내용을 직접 반환
- `viewResolver` 대신 **HttpMessageConverter**가 동작
  - 기본 문자 처리: `StringHttpMessageConverter`
  - 기본 객체 처리: `MappingJackson2HttpMessageConverter` (객체를 JSON 형식으로 변환)
  - byte 처리 등 다양한 **HttpMessageConverter**가 기본으로 등록되어 있음.

#### 객체 처리 시
- **getter**와 **setter** 방식을 사용함.
  - 이 방식은 private 프로퍼티에 접근할 수 있게 해줌.
