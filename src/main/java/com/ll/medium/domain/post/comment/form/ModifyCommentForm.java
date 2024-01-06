package com.ll.medium.domain.post.comment.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModifyCommentForm {
    @NotBlank
    private String comment; // 프론트와 백 데이터 주고 받을 때 데이터 이름(comment) 맞춰야 함
}
