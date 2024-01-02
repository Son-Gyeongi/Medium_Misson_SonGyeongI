package com.ll.medium.domain.post.comment.controller;

import com.ll.medium.domain.post.comment.entity.Comment;
import com.ll.medium.domain.post.comment.form.WriteCommentForm;
import com.ll.medium.domain.post.comment.service.CommentService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final Rq rq;

    // 댓글 작성 POST
    @PostMapping("/write/{postId}")
    @PreAuthorize("isAuthenticated()")
    public String writeComment(@Valid WriteCommentForm writeCommentForm, @PathVariable Long postId) {
        System.out.println("댓글 : "+writeCommentForm.getComment());
        RsData<Comment> write = commentService.write(rq.getMember(), writeCommentForm.getComment(), postId);

        // 해당 게시글로 이동
        return rq.redirect("/post/%d".formatted(write.getData().getPost().getId()), write.getMsg());
    }

    // 댓글 모두 보여주기
//    @GetMapping("/{postId}") // postId : 게시글ID
//    public String showComment(@PathVariable Long postId, Model model) {
//        List<Comment> comments = commentService.findAll(postId);
//
//        model.addAttribute("comments", comments);
//
//        return "domain/post/post/detail";
//    }
}
