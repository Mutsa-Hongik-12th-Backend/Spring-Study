## 프로젝트 환경설정

![image](https://github.com/user-attachments/assets/73c21a6b-a889-47bc-b229-41ecc66e46c3)


- [https://start.spring.io/](https://start.spring.io/) 에서 위와같이 작성 후 다운받기 .
- 한 후 intellij 에서 build.gradle을 열어야함. (처음에 heeminbyun열었다가 잘 작동하지 않아서 고생함..ㅠㅠ)

---

- resources:실제 자바 코드를 제외한 xml,html등 설정파일,
- src-test: test코드들과 관련된 소스들이 들어있음
- build.gradle: 중요! 스프링 부트가 나오면서, 설정 관련된 것들이 제공됨. 버전 설정하고 라이브러리 떙겨오는 역할을 함.
- 동작확인
    - 스프링부트 메인 실행 후 동작확인 (http://localhost:8080)

### 라이브러리


![image](https://github.com/user-attachments/assets/6043a98c-3c24-440b-8ea0-e3f7304fd235)


### view환경설정

<aside>
💡

스프링부트는 static/index.html을 올리면  welcome page기능을 함 

</aside>

- Controller : 웹 어플리케이션에서의 첫 번째 진입점

### thymeleaf 템플릿 엔진

```jsx
 @GetMapping("hello") #get메소드 같은 역할 
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello"; #resources에 있는 templates의 hello로 감. 
    }
```

[localhost](http://localhost:8080/hello):8080/hello로 이동 

![image](https://github.com/user-attachments/assets/bded56e2-2fe9-481c-9cc1-d45b32960b6e)


컨트롤러에서 리턴값으로 문자를 반환하면 viewResolver가 화면을 찾아서 처리함. 

### 빌드하고 실행하기

```jsx
./gradlew build
cd build/libs
java -jar heeminbyun-0.0.1-SNAPSHOT.jar
```

## 스프링 웹 개발 기초

<aside>
💡

템플릿엔진: html을 동적으로 바꿔서 내줌. 

model view controller(MVC) :모델, 템플릿엔진, 화면.

정적컨텐츠는 파일을 그대로 웹브라우저에게 전달해 주는게 정적컨텐츠 방식의 웹 개발이고, mvc와 템플릿 엔진은 서버에서 변형을해서 html을 바꿔서 내려주는 방식,

api는 json이라는 데이터 구조 포맷으로 클라이언트에게 데이터를 전달해주는 방식. 

</aside>

### 정적 컨텐츠

스프링 부트는 정적 컨텐츠를 자동으로 제공해 줌. 

![image](https://github.com/user-attachments/assets/d8bfae71-92de-4605-9416-a57833b91179)


[localhost:8080/hello-static.html로](http://localhost:8080/hello-static.html로) 들어가면 정적파일이 그대로 반환됨. 

![image](https://github.com/user-attachments/assets/a2e30497-882f-4eed-ac72-42bc304fea66)


[localhost:8080/hello-static.htm](http://localhost:8080/hello-static.html로)로 들어가면 제일 처음에 내장 톰캣 서버가 이 요청을 받고, 이 요청을 스프링에게 넘김. 스프링은 먼저 컨트롤러에서 hello-static.html이 있는지 찾아보고 없으면, 내부에 있는 resources에 있는 static의 hello-static.html을 반환해줌

### MVC,템플릿 엔진

- mvc: model,view,controller
- 뷰는 화면을 그리는 역할.
- 컨트롤러나 모델과 관련된 부분은 비즈니스 로직이나 내부적인 것을 처리하는데 집중.

![image](https://github.com/user-attachments/assets/62889922-ee76-4251-a3a7-88d76dc16c23)


[localhost:8080/hello-](http://localhost:8080/hello-static.html로)mvc?name=spring으로 하면 내장 톰캣 서버가 이 요청을 받고 이 요청을 스프링에 넘김, 컨트롤러에 helloController에 맵핑되는 메서드가 있어서 그 메서드를 호출해줌.  리턴을 해줄 떄 이름을 hello-template이라고 하고, 모델에 키는 네임이고 값은 spring으로 스프링에 넘겨줌. 그러면 viewResolver가 templates/hello-template이라는 return 의 스트링 네임과 똑같은 애를 찾아서 thymeleaf템플릿 엔진에게 처리해 달라고 넘기면 템플릿 엔진이 ‘변환’후 반환해줌. 

### API

객체를 반환하는 방식

`@ResponseBody`

`public String helloString(@RequestParam("name") String name) {`    

`return "hello " + name;`

`}`

`@ResponseBody`

: response body부에 `"hello " + name` 이 내용을 직접 넣어주겠다는 의미 

![image](https://github.com/user-attachments/assets/ece7b045-cf7e-4fd5-88a1-e5795bce5a38)


[localhost:8080/hello-a](http://localhost:8080/hello-aoi를)pi를 치면 톰켓 내장 서버에서 스프링으로 감. spring은 hello-api가 있지만,  @responsebody라고 붙어 있기 때문에 http 응답에 그대로 이 데이터를 넣어 동작함. 즉, 문자가 아니라 객체로 오기 때문에 디폴트로 json방식으로 데이터를 만들어서 http응답에 반환하는게 기본 정책임. 

객체가 오면 httpmessageconverter가 동작하는데 이때,  단순한 문자면 stringconverter가 , 객체면 jsonconverter가 동작함. 

이렇게 hello객체를 json으로 바꿔 응답하는 것이 responsebody를 이용한 api임.
