# Java WAS

2024 우아한 테크캠프 프로젝트 WAS

## 구현 내용
- 정적인 html 파일 응답
  - http://localhost:8080/index.html 또는 http://localhost:8080 으로 접속시 정적 파일인 index.html 파일을 읽어 클라이언트에 응답.
  - threadPool 이용하여 멀티 스레드 요청 처리가능.
- 다양한 컨텐츠 타입 지원
  - 요청 url에서 확장자를 파싱하고 매핑된 정보를 이용하여 html, css, js, ico, png 등의 다양한 컨텐츠 타입 지원.
- 회원 가입 기능
  - /registration, /login 과 같이 디렉토리가 요청 url인 경우 자동으로 하위의 index.html 파일 응답.
  - 회원 가입 버튼 클릭시 사용자 입력 값을 서버에 전달하고 index.html로 리다이렉트
## 사용 방법
1. git clone 이후 java -jar ./build/libs/java-was-1.0-SNAPSHOT.jar 명령어로 빌드 파일 실행.
2. http://localhost:8080/index.html 또는 http://localhost:8080 로 접속.
  <img width="1900" alt="스크린샷 2024-07-05 오전 11 20 54" src="https://github.com/hek-git/java-was/assets/77234610/48b1a0e7-62b5-4bc6-9cee-ab86121e6041">   

3. 회원가입 페이지로 이동후 정보 입력한뒤 회원가입 버튼 클릭.
<img width="1917" alt="스크린샷 2024-07-05 오전 11 22 17" src="https://github.com/hek-git/java-was/assets/77234610/2ba8c546-ea88-41d3-87f3-b7dd06a55ed2">   

4. 메인 페이지로 리다이렉트됨을 확인.
