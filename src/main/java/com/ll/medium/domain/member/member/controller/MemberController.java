package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.form.JoinForm;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final Rq rq;

    // 가입 폼
    @PreAuthorize("isAnonymous()") // 로그인 되어있지 않은 사용자 접근 가능
    @GetMapping("/join")
    String showJoin() {
        return "domain/member/member/join"; // domain/member/member/join.html
    }

    // 가입 폼 처리
    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm) {
        RsData<Member> joinRs = memberService.join(joinForm.getUsername(), joinForm.getPassword(), false);

        return rq.redirectOrBack("/member/login", joinRs);
    }

    // 로그인 폼
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "domain/member/member/login"; // domain/member/member/login.html
    }
}
