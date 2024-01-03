package com.ll.medium.domain.post.comment.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ModifyCommentForm {
    @NotBlank
    private String comment;
}
