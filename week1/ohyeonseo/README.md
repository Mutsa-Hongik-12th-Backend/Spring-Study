### section 1 : 강의 소개

스프링 공부 목록

- 스프링 프로젝트 생성
- 스프링 부트로 웹 서버 실행
- 회원 도메인 개발
- 웹 MVC 개발
- DB 연동 - JDBC, JPA, 스프링 데이터 JPA
- 테스트 케이스 작성

### section 2 : 프로젝트 환경설정

1. 프로젝트 생성
    - Java 11
    - IDE: IntelliJ
    - 요즘에는 Maven보다 **Gradle** 씀. 우린 Groovy로 하면 됨
    - dependencies : Spring web, Thymeleaf
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/954c7204-6fd5-4e90-ab47-ab6006a44746/e2192a98-1c9d-4620-85bb-00e31516a57c/image.png)
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/954c7204-6fd5-4e90-ab47-ab6006a44746/973d3db8-87f7-4779-81f9-6d99e5573466/image.png)
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/954c7204-6fd5-4e90-ab47-ab6006a44746/99820941-0d70-49d4-9331-7b7fe8e44134/image.png)
    

3. 실행

```bash
cd /Users/ohyeonseo/Downloads/hello-spring
./gradlew build
cd build
cd libs
ls -arlth
java -jar hello-spring-0.0.1-SNAPSHOT.jar

# ./gradlew clean 하면 빌드 폴더가 없어짐. 그러니깐 ./gradlew clean build 해주면 됨
```

### section 3 : 스프링 웹 개발 기초

1. 정적 컨텐츠
    
    스프링부트는 정적 컨텐츠 기능을 제공함
    
    ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/954c7204-6fd5-4e90-ab47-ab6006a44746/0cbba539-0f7c-422f-b47f-1e054c60932a/image.png)
    
2. MVC와 템플릿 엔진(동적)
    
    ```java
        @GetMapping("hello-mvc")
        public String helloMvc(@RequestParam("name") String name, Model model) {
            model.addAttribute("name", name);
            return "hello-template";
        }
        
        // 접속 주소 http://localhost:8080/hello-mvc?name=hi
    ```
    
3. API
