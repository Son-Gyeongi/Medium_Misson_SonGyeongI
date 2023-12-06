package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.dto.JoinDto;
import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    // 가입 폼
    @PreAuthorize("isAnonymous()") // 로그인 되어있지 않은 사용자 접근 가능
    @GetMapping("/join")
    String showJoin() {
        return "domain/member/member/join"; // domain/member/member/join.html
    }

    // 가입 폼 처리
    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    @ResponseBody
    public String join(@Valid JoinDto joinDto, BindingResult bindingResult) {
        // TODO 프론트에서 막아주는 걸로 변경하기
        if (bindingResult.hasErrors()) {
            return "domain/member/member/join"; // domain/member/member/join.html
        }

        // 비밀번호 일치 검사 TODO 프론트에서 막아주는 걸로 변경하기
        if (!joinDto.getPassword().equals(joinDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect",
                    "패스워드가 일치하지 않습니다.");
            return "domain/member/member/join"; // domain/member/member/join.html
        }

        RsData<Member> joinRs = memberService.join(joinDto.getUsername(), joinDto.getPassword());

        // TODO 반환하는 방법 바꿔야함
        return joinRs.getMsg();
    }

    // 로그인 폼
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "domain/member/member/login"; // domain/member/member/login.html
    }
}
