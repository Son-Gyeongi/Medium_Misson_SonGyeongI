package com.ll.medium.domain.post.post.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WriteForm {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    private Boolean isPublished; // 체크 박스, true 공개 / false 비공개
}
