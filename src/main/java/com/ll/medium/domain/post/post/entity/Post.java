package com.ll.medium.domain.post.post.entity;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity // 해당 클래스가 엔티티임을 의미
@SuperBuilder // 빌더 패턴을 자동으로 생성해주는 기능, 부모 클래스인 BaseEntity에서도 빌더 패턴 사용 가능
@AllArgsConstructor(access = AccessLevel.PROTECTED) // 모든 필드를 인자로 받는 생성자를 자동으로 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter // 모든 필드에 대한 getter 메서드를 자동으로 생성
@Setter // 모든 필드에 대한 setter 메서드를 자동으로 생성
@ToString(callSuper = true) // callSuper = true를 설정하여 부모 클래스인 BaseEntity의 toString() 메서드도 포함
public class Post extends BaseEntity {
    private String title; // 게시글 제목
    private String body; // 게시글 내용
    @ManyToOne(fetch = FetchType.LAZY) // @ManyToOne(fetch = LAZY) 를 통해서 데이터가 필요한 시점에 DB를 조회한다.
    private Member author; // 작성자 / 실제로 DB에 저장되는 건 author_id 이다.
    private Boolean isPublished; // 체크 박스

    public Post(String title, String body, Boolean isPublished, Member author) {
        this.title = title;
        this.body = body;
        this.author = author;
        this.isPublished = isPublished;
    }
}
