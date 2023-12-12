package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.form.WriteForm;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final Rq rq;

    // 공개된 글만 노출
    @GetMapping("/list")
    public String getPosts(Model model) {
        List<Post> posts = postService.findAll();

        model.addAttribute("posts", posts);

        return "domain/post/post/list";
    }

    @GetMapping("/write")
    public String writePost() {

        return "domain/post/post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writePost(@Valid WriteForm writeForm) {
        RsData<Post> post = postService.write(writeForm.getTitle(), writeForm.getBody(), writeForm.getIsPublished(), rq.getMember());

        // 상세 페이지로 이동
        return rq.redirect("/post/%d".formatted(post.getData().getId()), post.getMsg());
    }

    // 게시글 상세내용
    @GetMapping("/{id}")
    public String detailPost(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id);

        model.addAttribute("post", post);

        return "domain/post/post/detail";
    }

    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        // 게시글이 존재 여부
        Post post = postService.getPost(id);

        // 권한 여부 체크
        if (!postService.canDelete(rq.getMember(), post))
            throw new RuntimeException("삭제 권한이 없습니다.");

        // 게시글 삭제
        postService.deletePost(post);

        return rq.redirect("/", "%d번 게시글이 삭제되었습니다.".formatted(id));
    }
}
