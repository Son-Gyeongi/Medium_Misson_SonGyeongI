package com.ll.medium.domain.member.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    // 가입 폼
    @GetMapping("/join")
    String showJoin() {
        return "domain/member/member/join"; // domain/member/member/join.html
    }
}
