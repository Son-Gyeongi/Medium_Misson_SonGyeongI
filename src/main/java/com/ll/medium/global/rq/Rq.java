package com.ll.medium.global.rq;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.global.rsData.RsData;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequestScope
@Component
@Getter
@RequiredArgsConstructor
@Slf4j
public class Rq { // 회원과 관련된 로직
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    private User user;
    private Member member; // 멤버는 장기기억 했으면 좋겠다. final 안 쓰는 이유 member 생성된 후에 값을 넣어야 하므로

    // 생성자가 실행된 후에 @PostConstructor가 실행됨
    @PostConstruct
    public void init() {
        // 현재 로그인한 회원의 인증정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        }
    }

    public boolean isLogined() {
        return user != null; // 로그인된 사용자가 없다면 false
    }

    // 로그인 된 사용자 username 가져오기
    public String getMemberUsername() {
        return user.getUsername();
    }

    // 로그인한 사용자 찾기
    public Member getMember() {
        // 로그인된 사용자가 없다면
        if (!isLogined()) return null;

        if (member == null)
            member = memberService.findByUsername(getMemberUsername()).get();

        return member;
    }

    public String redirect(String path, String msg) {
        if (msg == null) return "redirect:" + path;

        msg = URLEncoder.encode(msg, StandardCharsets.UTF_8);

        return "redirect:" + path + "?msg=" + msg;
    }

    public String redirect(String path, RsData<?> rs) {
        return redirect(path, rs.getMsg());
    }

    // 회원가입 중복시 js.html을 갔다가 뒤로가기로 다시 회원가입 폼을 보여준다.
    public String historyBack(String msg) {
        resp.setStatus(400);
        req.setAttribute("msg", msg); // model.addAttribute("msg", msg)하는 거랑 같다.

        return "global/js";
    }

    public String historyBack(RsData<?> rs) {
        return historyBack(rs.getMsg());
    }

    public String redirectOrBack(String url, RsData<?> rs) {
        if (rs.isFail()) return historyBack(rs);
        return redirect(url, rs);
    }
}
