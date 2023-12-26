## Title: [second] 7. 손경이

### 미션 요구사항 분석 & 체크리스트

***

#### 필수미션 1 : Member 클래스에 private boolean isPaid 필드를 추가

- [X] Member 클래스에 private boolean isPaid 필드를 추가
    - 해당 필드가 true 인 사람이 로그인할 때, ROLE_PAID 권한도 가지도록(스프링 시큐리티)
    - 해당 필드가 true 이면 유료 멤버십 회원 입니다.
- [ ] 가입할 때 isPaid 체크하는 부분이 필요할까?

***

#### 필수미션 2 : Post 클래스에 private boolean isPaid 필드를 추가

- [ ] Post 클래스에 private boolean isPaid 필드를 추가
    - 해당 필드가 true 인 글은 유료회원이 아닌사람에게는 상세보기(GET /post/1)에서 본문(content)이 나올 자리에 이 글은 유료멤버십전용 입니다. 라는 문구가 나온다.

> 엔드 포인트

- [ ] GET /post/list
    - 멤버십 회원이 아니라도 글 리스트에서는 멤버십 전용글을 볼 수 있습니다.
- [ ] GET /post/1
    - 유료 멤버십 회원이고 1번 글이 멤버십전용글 이라면 볼 수 있다.
    - 그 외에는 이 글은 유료멤버십전용 입니다. 노출

***

#### 필수미션 3 : NotProd 에서 유료멤버십 회원(샘플 데이터)과 유료글(샘플 데이터)을 각각 100개 이상 생성

- [ ] NotProd 에서 유료멤버십 회원(샘플 데이터)과 유료글(샘플 데이터)을 각각 100개 이상 생성

<hr>

- [ ] 필수미션 끝나면 따라 썼던 html 조금씩 변경해보기 (다른 ui로)

<hr>

### second 미션 요약

[접근 방법]

[특이사항]
