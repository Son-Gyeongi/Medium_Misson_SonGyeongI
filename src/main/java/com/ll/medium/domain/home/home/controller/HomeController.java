package com.ll.medium.domain.home.home.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<Post> posts = postService.getLatest30Posts();

        model.addAttribute("posts", posts);

        return "domain/home/home/main";
    }

    // 스프링 시큐리티의 세션 구조 확인
    @GetMapping("/home/session")
    @ResponseBody
    public Map<String, Object> showSession(HttpSession session) {
        return Collections.list(session.getAttributeNames()).stream()
                .collect(
                        Collectors.toMap(
                                key -> key,
                                key -> session.getAttribute(key)
                        )
                );
    }
}
