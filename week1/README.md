## section1.

  

### 기본 폴더 구조

  

```

hello-spring
│
├── src
│ ├── main
│ │ ├── java
│ │ └── resources
│ └── test
└──build.gradle
│

```

  

-  **src/main/java**: 로직을 담고 있는 자바코드 파일들이 위치하는 곳

-  **src/main/resources**: 애플리케이션 설정 파일(HTML, Thymeleaf 등)이 위치

-  **src/test/java**: 테스트 코드 위치하는 곳

  

### 라이브러리 살펴보기

  

<img width="795" alt="image" src="https://github.com/user-attachments/assets/8dd7e879-f96b-4ca3-b2c9-203c44f15f3d">

 
dependencies ⇒ 의존 파일들

  
<img width="622" alt="1-1" src="https://github.com/user-attachments/assets/d43a1472-4d66-4721-9625-fcdcb1c3eb01">

<img width="627" alt="1-2" src="https://github.com/user-attachments/assets/a5be10f4-3fec-4f07-855b-1ebe4ab6b246">

  
<img width="720" alt="1-3" src="https://github.com/user-attachments/assets/2388f1b7-2099-4a32-b58f-ff36aead2592">

logging ⇒ 현업에서 많이 사용/ logback, slf4j 인터페이스 찾아보면 좋다

  
test : junit 라이브러리 사용


⇒ 핵심 라이브러리

  

```

[스프링 부트 라이브러리]
spring-boot-starter-web
- spring-boot-starter-tomcat: 톰캣 (웹서버)
- spring-webmvc: 스프링 웹 MVC
spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
- spring-boot
- spring-core
spring-boot-starter-logging
- logback, slf4j


[테스트 라이브러리]
spring-boot-starter-test
- junit: 테스트 프레임워크
- mockito: 목 라이브러리
- assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리 spring-test: 스프링 통합 테스트 지원


```

  

### view 환경설정

  

welcome 페이지 만들기 (정적 페이지)


정적 페이지 만들어보기 : **resources/static/** 에 .html파일 만들기
동적 페이지 만들어보기 : **thymeleaf 템플릿 엔진** 사용

  
**resources/templates/ ~.html** : ‘static/index.html’ 을 올려두면 Welcome page 기능을 스프링부터에서 제공

  

<img width="699" alt="1-4" src="https://github.com/user-attachments/assets/0ae0b368-96bd-4680-bc81-46fec04da4cb">

정적 컨텐츠 - 아래에서 자세히 볼 것

  

---

  

***src/main/java/ . . /controller :** controller코드 구현*


```java

@Controller
public  class  HelloController {
@GetMapping("hello")
public  String  hello(Model  model) {
model.addAttribute("data", "hello!!");
return  "hello";
}
}

```

  

**resources/templates/ ~.html :**

  

`<p th:text="'hello ' + ${data}">hello! empty</p>` 에서 `{data}` ← controller에 있던 값

<img width="627" alt="1-2" src="https://github.com/user-attachments/assets/cb451b5b-6422-4f09-aedc-429b060cf08e">


동적 컨텐츠 / 아래 섹션에서 자세히 볼 것

  

### 빌드하고 실행하기

  

1. 터미널 실행

  

2.  `./gradlew build` ⇒ 결과물 중 .jar파일

  

1.  `java -jar (자바 파일 실행시키기) + .jar파일명`

  

-  `Tomcat started on port 8080 (http) with context path '/’` : 8080포트에서 서버 실행 O

  

## section2. 스프링 웹 개발 기초

  

정적 컨텐츠 : 파일 자체를 브라우저로 전달

  

MVC와 템플릿 엔진 : html를 서버에서 조작

  

API : json데이터 포멧으로 클라이언트에게 정보전달 / 서버끼리 통신할때

  

### 정적 컨텐츠 static content

  

스프링 부트 기본 제공 : 공식 페이지에서 검색 가능

  

src/main/resources/static에 `{파일명}.html`생성

  

[localhost:8080/{파일명.html}](http://localhost:8080/{파일명.html}) 접속

<img width="699" alt="1-4" src="https://github.com/user-attachments/assets/b6c4cf50-5138-4720-a9a8-e56bf8ef3f46">

  

### MVC와 템플릿 엔진 model view controller

  

view : 화면에 보이는 것

  

controller, model : 내부적 기능

  

- 파라미터 옵션 넣기 : command+P

-  `public String helloMvc(@RequestParam(name = "name", **required = false**)` 라면 파라미터에 있는 값을 무조건 넘길 필요는 없게 된다

- [localhost:8080/**hello-mvc?name=spring1**](http://localhost:8080/hello-mvc?name=spring1) 파라미터 넘겨주기 방식

<img width="577" alt="image" src="https://github.com/user-attachments/assets/6e10c7dc-51c6-44ac-94d1-275bac8da1f7">


정적과 달리 HTML을 변환해줌(랜더링)

  

### API

  

1. 특정 문자열을 반환하고자 할때

  

```java
@Controller
public  class  HelloController {
@GetMapping("hello-string")
@ResponseBody
public  String  helloString(@RequestParam("name") String  name) {
return  "hello " + name;
}}

```

  

`@GetMapping("hello-string")` : `8080/hello-string`

  

`@ResponseBody` http의 body에 데이터 직접 넣겠다 . ⇒ hello + {name} 이 그대로 반

<img width="445" alt="image" src="https://github.com/user-attachments/assets/6d83f8f3-e55a-47b4-885f-5f6c2cac183e">


→ 값을 그대로 http로 넣어줌

  

ii. 객체를 반환할때

<img width="526" alt="image 3" src="https://github.com/user-attachments/assets/85348798-01c7-4dbe-98b8-d5bebc3e9efc">


  

hello 객체 반환!

  

command+shitft+enter : 완성해줌

<img width="562" alt="image" src="https://github.com/user-attachments/assets/b65df402-4ca9-423c-8130-caebd70c02b8">


JSON 형식

<img width="703" alt="1-8" src="https://github.com/user-attachments/assets/b16d612e-9ac8-4307-af54-f2b795e8fdd6">

  

ResponseBody사용

  

형태가 일반 문자열인 경우 → string converter

  

형태가 객체인 경우 → json converter

  

- HTTP의 BODY에 문자 내용을 직접 반환

-  `viewResolver` 대신에 `HttpMessageConverter` 가 동작

- 기본 문자처리: `StringHttpMessageConverter`

- 기본 객체처리: `Mapping**Jackson**2HttpMessageConverter`

(객체, json 변경 라이브러리)

- byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음
