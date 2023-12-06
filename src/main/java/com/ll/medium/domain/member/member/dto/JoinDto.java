package com.ll.medium.domain.member.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class JoinDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String passwordConfirm;
}
