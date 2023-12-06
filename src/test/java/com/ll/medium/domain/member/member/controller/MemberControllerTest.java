package com.ll.medium.domain.member.member.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class MemberControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET /member/join
    @DisplayName("회원가입 페이지를 보여준다.")
    @Test
    @WithAnonymousUser // 로그인 되지 않은 사용자
    void t1() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/member/join"))
                .andDo(print());

        // THEN
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("domain/member/member/join"))
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("showJoin"));
    }

    // POST /member/join
    @DisplayName("회원가입 처리")
    @Test
    @WithAnonymousUser
    void t2() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(
                        post("/member/join")
                                .with(csrf())
                                .param("username", "user1")
                                .param("password", "1234")
                                .param("passwordConfirm", "1234")
                )
                .andDo(print());

        // THEN
        resultActions.andExpect(status().isOk())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("join"));

        // 검증
        // 제일 마지막에 등록한 회원
        Member member = memberService.findLatest().get();

        assertThat(member.getUsername()).isEqualTo("user1");
        // 암호화해서 member에 가져온 password랑 비교해야한다.
        assertThat(passwordEncoder.matches("1234", member.getPassword())).isTrue();
    }
}