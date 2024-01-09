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

- [X] GET / : 홈
- [X] 최신글 30개 노출
    - [X] 테스트용 더미 데이터 추가
    - [X] 공개된 글만 노출

글 목록 조회

- [X] GET /post/list : 전체 글 리스트
    - [X] 페이징 하기 (10개씩 끊어서)
    - [ ] 프론트 - 페이지에서 첫 페이지, 마지막 페이지 전후에 이전, 다음 비활성화 하기
    - [ ] 프론트 - 페이지 위치 가로로 만들기
- [X] 공개된 글만 노출

내 글 목록 조회

- [X] GET /post/myList : 내 글 리스트
    - 내 글 조회는 공개, 비공개 다 볼 수 있어야 한다.
    - [ ] 프론트 - 공개여부 컬럼에서 영어로 true, false가 아닌 한글로 공개, 비공개로 만들기
    - [X] /post/myList에서 상세보기할 때 비공개 글도 보이게 하기

글 상세내용 조회

- [X] GET /post/1 : 1번 글 상세보기
    - [ ]  없는 글 가져오면 에러나기
    - [ ]  비공개 글 가져오면 에러나기

글 작성

- [X] GET /post/write : 글 작성 폼
- [X] POST /post/write : 글 작성 처리
    - [X] 작성자 save 안되는 현상

글 수정

- [X] GET /post/1/modify : 1번 글 수정 폼
- [X] PUT /post/1/modify : 1번 글 수정 폼 처리

글 삭제

- [X] DELETE /post/1/delete : 1번 글 삭제

특정 회원의 글 모아보기 -> 개인블로그 모드라고 생각하면 된다.

- [X] GET /b/user1 : 회원 user1 의 전체 글 리스트
    - userPosts.html
- [X] GET /b/user1/3 : 회원 user1 의 글 중에서 3번글 상세보기
    - 글 상세보기로 들어가지는데 왜 필요한지 생각해보기 -> 개인블로그 모드

> 폼

글 쓰기 폼

- [X] title, body, isPublished(체크박스, value="true")

글 수정 폼

- [X] title, body, isPublished(체크박스, value="true")

<hr>

- [ ] 필수미션 1,2 끝나면 따라 썼던 html 조금씩 변경해보기 (다른 ui로)

<hr>

### first 미션 요약

[접근 방법]

- 일단 구현을해서 기능이 동작을 하게하자는 목표를 했습니다.
- 세세하게 예외 처리, 프론트, 깔끔한 코드 등 많이 신경 못 썼습니다.

[특이사항]

- 내 글 목록 조회 경우 로그인 후에 nav 우측 상단에 'welcome user1'을 누르면 나타난다.
- 특정 회원의 글 모아보기 경우 게시글 목록에서 작성자 이름을 누르면 나타난다.
