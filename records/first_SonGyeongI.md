## Title: [first] 7. 손경이

### 미션 요구사항 분석 & 체크리스트

***

#### 필수 미션 1 : 회원기능

> 엔드 포인트

가입

- [X] GET /member/join : 가입 폼
- [X] POST /member/join : 가입 폼 처리
    - [X] TODO 확인해서 프론트엔드 코딩하기
    - [X] 반환하는 타입 바꾸기(메시지 또는 에러가 생기는 경우로 바꾸기)
- [X] join.html 만들기

로그인

- [X] GET /member/login : 로그인 폼
- [X] POST /member/login : 로그인 폼 처리
  - [X] 로그인 할 때 회원가입하지 않은 사용자의 경우 에러 메시지 띄워주기
  - [X] 로그인 할 때 username, password 틀릴 경우 팝업 창 띄워주기
- [X] login.html 만들기

로그아웃

- [X] POST /member/logout : 로그아웃

> 폼

회원가입 폼

- [X] username, password, passwordConfirm

로그인 폼

- [X] username, password (스프링 시큐리티에 로그인 맡길거라서 필요없다.)

***

#### 필수 미션 2 : 글 CRUD

> 엔드 포인트

홈

- [ ] GET / : 홈
- [ ] 최신글 30개 노출

글 목록 조회

- [ ] GET /post/list : 전체 글 리스트
- [ ] 공개된 글만 노출

내 글 목록 조회

- [ ] GET /post/myList : 내 글 리스트

글 상세내용 조회

- [ ] GET /post/1 : 1번 글 상세보기

글 작성

- [ ] GET /post/write : 글 작성 폼
- [ ] POST /post/write : 글 작성 처리

글 수정

- [ ] GET /post/1/modify : 1번 글 수정 폼
- [ ] PUT /post/1/modify : 1번 글 수정 폼 처리

글 삭제

- [ ] DELETE /post/1/delete : 1번 글 삭제

특정 회원의 글 모아보기

- [ ] GET /b/user1 : 회원 user1 의 전체 글 리스트
- [ ] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기

> 폼

글 쓰기 폼

- [ ] title, body, isPublished(체크박스, value="true")

글 수정 폼

- [ ] title, body, isPublished(체크박스, value="true")

<hr>

- [ ] 필수미션 1,2 끝나면 따라 썼던 html 조금씩 변경해보기 (다른 ui로)

<hr>

### first 미션 요약


[접근 방법]

[특이사항]
