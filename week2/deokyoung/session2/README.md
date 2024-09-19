# Spring 스터디 session2

작성일시: 2024년 9월 19일 오후 4:42
복습: No

**Java 작동 원리 - (정확한 개념보다는 이해하기 쉽게 비유적으로 작성했습니다.)**

**클라이언트 요청 → 컨트롤러(일종의 라우트 역할..? 장고로 치면 url pattern을 통해 view로 전달하는 과정과 비슷하다고 생각함) → service(view함수와 같이 url이 매핑되었을 때 요청 method에 맞게 수행되는 기능들..) → repository(DB로에 저장 혹은 DB로부터의 조회 등을 관리) → DB → … → 클라이언트**

**그럼 코드에서 DTO와 domain은 무엇일까?**

1. **domain** - 엔티티 클래스를 정의 (서비스 로직에서 필요한 데이터 모델, 예를 들어 쇼핑몰을 운영하고자 한다면 사용자, 상품 등록, 주문하기 등이 domain의 클래스 대상들)
2. **DTO** - 데이터 반환 시 모델 전체 필드를 반환할 필요가 없는 경우, 혹은 데이터의 노출을 방지하고 싶을 때 DTO로 데이터를 변환하여 반환

코드 작성 후 main 클래스 실행으로 로컬 서버 실행

![image.png](image.png)

![image.png](image%201.png)

POSTMAN으로 코드 테스트(url 매핑 방식은 컨트롤러 파일에 작성되어 있음)

1. 멤버 생성

   ![image.png](image%202.png)

2. 멤버 전체 조회

   ![image.png](image%203.png)

3. 특정 멤버 조회

   ![image.png](image%204.png)


H2 데이터베이스 연결이 잘 되어있는지 확인

1. h2 디비 최초 연결

![image.png](image%205.png)

1. SELECT문으로 포스트맨으로 생성한 멤버들 조회

![image.png](image%206.png)

1. 특정 멤버 조회

![image.png](image%207.png)