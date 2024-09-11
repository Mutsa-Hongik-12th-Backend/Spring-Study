# 🏷️WEEK 1 프로젝트 생성
[스프링 입문 - 코드로 배우는 스프링 부트] 섹션 1-3을 수강하고 TIL을 작성하였습니다.
## ⚙️프로젝트 환경 설정
### 1️⃣ 프로젝트 생성
---------------------
1. 자바 17 세팅

2. 스프링 부트 스타터 사이트 사용해서 스프링 프로젝트 생성 (<https://start.spring.io>)

3. 프로젝트 선택
- Project : Gradle Project
- Language : Java
- Spring Boot : 2.3.1
- Project Metadata
    - Group : hello
    - Artifact : yunakang
    - Name : yunakang
    - Description : Demo project for Spring Boot
    - Package name : hello.yunakang
- **Dependencies**
    - **spring web, Thymeleaf** 2개 추가
![1  스프링 부트 스타터 이용](https://github.com/user-attachments/assets/c62dcdda-0737-4608-b007-4be6677792cb)

=> generate 다운 받아 압축 풀고, 인텔리제이에서 import함 => build.gradle 선택 후 open as project 해주면 됨

### 2️⃣ 라이브러리 살펴보기
-----------------------
Gradle은 의존관계가 있는라이브러리를 함께 다운로드 한다.

**<스프링 부트 라이브러리>**

**spring-boot-startweb**
	- spring-boot-starter-tomcat : 톰캣(웹서버)
	- spring-webmvc : 스프링 웹 MVC
	
**spring-boot-starter-thymeleaf**: 타임리프 템플릿 엔진(View)

**spring-boot-starter(공통)** : 스프링 부트 + 스프링 코어 + 로깅
 - spring-boot
	 - spring-core
 - spring-boot-starter-logging
	 - logback, slf4j
	 
**<테스트 라이브러리>**

**spring-boot-starter-test**
 - junit : 테스트 프레임워크
 - mockito : 목 라이브러리
 - assertj : 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
 - spring-test : 스프링 통합 테스트 지원

### 3️⃣ View 환경 설정
--------------------------
**welcome page 만들기(정적 사이트)**

/src/main/resources/static에 file 생성: `index.html`

![4  welcome page 정적 사이트 실행 결과](https://github.com/user-attachments/assets/a3c842bd-c5fa-4a19-9462-e5ddabf44e3d)

**"thymeleaf" 사용하면서 동적 사이트로 사용하기**
/src/main/java/hello.hellospring에서 package 만들기 : `.controller` → 여기서 class 형성 : `HelloController` 

/src/main/resources/static/templates에 가서 file 생성 : `hello.html`

**localhost:8080/hello로 확인 가능**

![5  thymeleaf 동적 사이트 실행 결과](https://github.com/user-attachments/assets/3570745f-33c3-42a8-877f-6bacb3f1209b)

**동작 원리 : 컨트롤러에서 리턴 값으로 문자를 반환하면, 뷰 리졸버(`viewResolver`)가 화면을 찾아서 처리함**

- 스프링 부트 템플릿엔진 기본 viewName 매핑
- `resources:templates/`+{ViewName}+`.html`

### 4️⃣ 빌드하고, 실행하기
./gradlew.bat부터 에러남

⇒ 터미널에 자바 설치부터 진행

cd yunakang **# gradlew.bat 있는 디렉토리로 가기**

./gradlew.bat

./gradlew build

**=> 빌드 이후**

cd build
cd libs

**자바 실행 명령어**

java -jar .\yunakang-0.0.1-SNAPSHOT.jar
![7-1 빌드](https://github.com/user-attachments/assets/2a9d47db-e09e-4c64-899f-5f3e4b3afc8b)
![7-2 실행](https://github.com/user-attachments/assets/e078204c-14e1-425b-8505-7030c1e7fedb)

**localhost:8080**, **localhost:8080/hello로 확인 가능**


## 🌱스프링 웹 개발 기초
### 1️⃣ 정적 컨텐츠
---------------------
파일을 그대로 웹 브라우저에 내려주는 것

/src/main/resources/static에 파일 생성: `hello-static.html`

**localhost:8080//hello-static.html** 으로 확인 가능, 그러나 프로그래밍(동적)으로는 할 수 x

동작 원리 : 컨트롤러가 우선순위를 가지면서 hello-static 컨트롤러 존재하지 않으면, resources에서 찾기 시작함

![9  정적 컨텐츠](https://github.com/user-attachments/assets/e4b8e5bf-7ef9-4c66-80c9-4607670c14eb)

### 2️⃣ MVC와 템플릿 엔진
---------------------------
동작 컨텐츠 : 서버에서 변형을 하여 내려주는 것

**MVC: Model, View, Controller**

view : 화면을 그리는 데 힘 써야 함, controller: 비즈니스 로직

/Hellocontroller에다가 @GetMapping code 추가 : `hello-mvc`

/templates에서 파일 추가 : `hello-template.html`

**localhost:8080/hello-mvc?name=spring!!** ← **파라미터** 넘겨줘야

![10  MVC, 템플릿 엔진](https://github.com/user-attachments/assets/e7e65dad-f2f5-4361-a1e9-30dd568b9552)

### 3️⃣ API
--------------------------
JSON 형식으로 client에게 데이터를 전달하는 방식, 서버끼리 통신할 때 사용하기도 함

**<ResponseBody 문자 반환>**

/hellocontroller에 getmapping, code 추가 : `hello-string`

❗❗ **@ResponseBody** 추가해줘야함 - 뷰 리졸버  없이 지정된 응답 형식 그대로 반환 가능

**localhost:8080/hello-string?name=spring!으로** 확인 가능 ;; 이 방법 많이 쓰지 x


![11  api 문자 반환](https://github.com/user-attachments/assets/b1f09ad7-263f-409f-b3be-4bb59750ed48)

**<ResponseBody 객체 반환>**

/hellocontroller에 getmapping, code 추가 : `hello-api` ; 객체 반환하면 객체가 JSON으로 변환됨

➕ **getter setter 단축키** : `alt+insert` 이후 선택

**localhost:8080/hello-api?name=spring!으로** 확인 가능

![12  객체 반환](https://github.com/user-attachments/assets/bce43c5d-c404-4d4b-8572-7220b36e6099)

**<@ResponseBody를 사용>**

- HTTP의 BODY에 문자 내용을 직접 반환
- viewResolver 대신에 HttpMessageConverter가 동작
- 기본 문자처리 : StringHttpMesseageConverter
- 기본 객체처리 : MappingJackson2HttpMesseageConverter
- byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음

## 🧡 정리 노션

<https://amber-humor-4f2.notion.site/WEEK-1-a03c3af4c301426c99a2c1b82d8a5c16?pvs=4>

---------------------
## 🧡 WEEK1 실습 실행 결과 노션
<https://amber-humor-4f2.notion.site/Week1-57821ee22aa6416d96d6fa68777befb3?pvs=4>






