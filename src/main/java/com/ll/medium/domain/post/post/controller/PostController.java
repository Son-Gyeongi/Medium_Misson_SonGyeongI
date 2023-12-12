package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.form.ModifyForm;
import com.ll.medium.domain.post.post.form.WriteForm;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final Rq rq;

    // 공개된 글만 노출
    @GetMapping("/list")
    public String getPosts(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Post> paging = postService.findAll(page);

        model.addAttribute("paging", paging);

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
        Post post = postService.getDetailPost(id);

        model.addAttribute("post", post);

        return "domain/post/post/detail";
    }

    // 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@PathVariable Long id, Model model) {
        Post post = postService.getPost(id);

        model.addAttribute("post", post);

        return "domain/post/post/modify";
    }

    // 게시글 수정 POST
    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}/modify")
    public String modifyPost(@Valid ModifyForm modifyForm, @PathVariable Long id) {
        // 게시글 존재 여부
        Post post = postService.getPost(id);

        // 권한 여부 체크
        if (!postService.canModify(rq.getMember(), post)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 게시글 수정
        postService.modify(post, modifyForm.getTitle(), modifyForm.getBody(), modifyForm.getIsPublished(), rq.getMember());

        if (modifyForm.getIsPublished() == Boolean.TRUE) {
            return rq.redirect("/post/%d".formatted(id), "%d번 게시글이 수정되었습니다.".formatted(id));
        } else {
            return rq.redirect("/", "%d번 게시글이 수정되었습니다.".formatted(id));
        }
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
