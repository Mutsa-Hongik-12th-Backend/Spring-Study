# Week1 세션과제

# RestController

- Spring Framework에서 RESTful API를 쉽게 개발할 수 있도록 지원하는 어노테이션
- JSON/XML 자동 직렬화 ⇒ RESTful API 개발에 최적화
- `@ResponseBody`  어노테이션 추가할 필요없음
- View Resolver 사용하지 않음 ⇒ html이 아닌 데이터만을 응답

# Record 문법

- 일반 class와는 다르게 생성자, `getter`, `hashCode()`, `equals()` ,`toString()`를 제공
1. DTO(data transfer object)나 domain model class에 사용하면 좋음
2. 일시적으로 데이터를 저장할 때 사용
3. 데이터를 저장하고싶지만 긴 코드를 작성하고 싶지는 않을 때 사용하면 좋음
4. 불변적인 데이터를 저장할 때 적합

```java
public record Person(String name, Integer age) {
	
  // 여기서 생성자 파라미터를 검증하거나, 변환할 수 있다.
	public Person {
    	// this, this.name, this.age 사용 x
    	if (name == null) name = "noname";
      if (age == null) age = 0;
      name = name.trim();
    }
    
}
```

# ResponseEntity<T>

- Spring Framework에서 HTTP 응답을 나타내는 제너릭 타입.
    
    T는 바디의 타입. 상태코드, 응답 헤더, 본문을 포함한 HTTP 응답을 자유롭게 만들수 있다.
    
- `HttpEntity 클래스를 상속`받아 구현한 클래스가 **RequestEntity**, **ResponseEntity** 클래스
    
    ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 **`HttpStatus`, `HttpHeaders`, `HttpBody`**를 포함한다. 
    

# Optional<T>

- NullPointerException을 방지해주는. 즉, `null인 값을 참조해도 NullPointerException이 발생하지 않도록` 값을 래퍼로 감싸주는 타입
- **Stream처럼 Optional객체에도 filter(), map(), flatMap()을 사용할 수 있다.**

```java
Member member = memberRepository.findById(memberId)
								.orElseThrow(MemberNotFoundException::new)
```