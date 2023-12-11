package com.ll.medium.domain.post.post.entity;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.form.WriteForm;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    // TODO 메서드로 빠지는 걸까?
    private Boolean isPublished; // 체크 박스

    public Post(WriteForm writeForm, Member author) {
        this.title = writeForm.getTitle();
        this.body = writeForm.getBody();
        this.author = author;
        this.isPublished = writeForm.getIsPublished();
    }
}
