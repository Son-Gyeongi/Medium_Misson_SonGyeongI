package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/b")
@RequiredArgsConstructor
public class BlogController {
    private final PostService postService;

    // 특정 회원(username)의 전체 글 리스트
    @GetMapping("/{username}")
    public String getUserPosts(@PathVariable String username, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Post> paging = postService.getUserPosts(username, page);

        model.addAttribute("paging", paging);
        model.addAttribute("username", username);

        return "domain/post/post/userPosts";
    }

    // 특정 회원(username)의 게시글 상세보기
    @GetMapping("/{username}/{id}")
    public String getUserPost(@PathVariable String username, @PathVariable Long id, Model model) {
        Post post = postService.getDetailPost(id);

        model.addAttribute("post", post);

        return "domain/post/post/detail";
    }
}
