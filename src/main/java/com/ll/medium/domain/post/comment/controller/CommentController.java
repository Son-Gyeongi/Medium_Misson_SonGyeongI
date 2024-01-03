package com.ll.medium.domain.post.comment.controller;

import com.ll.medium.domain.post.comment.entity.Comment;
import com.ll.medium.domain.post.comment.form.ModifyCommentForm;
import com.ll.medium.domain.post.comment.form.WriteCommentForm;
import com.ll.medium.domain.post.comment.service.CommentService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        RsData<Comment> write = commentService.write(rq.getMember(), writeCommentForm.getComment(), postId);

        // 해당 게시글로 이동
        return rq.redirect("/post/%d".formatted(write.getData().getPost().getId()), write.getMsg());
    }

    // 댓글 모두 보여주기 - PostController에서 게시글 상세보기할 때 댓글 확인 가능

    // 댓글 수정 GET
    @GetMapping("/{id}/modify")
    @PreAuthorize("isAuthenticated()")
    public String showModifyComment(ModifyCommentForm form, @PathVariable Long id, Model model) {
        Comment comment = commentService.getComment(id);

        // 권한 여부 체크
        if (!commentService.canModify(rq.getMember(), comment)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        model.addAttribute("comment", comment);
        model.addAttribute("post", comment.getPost());

        return "domain/post/comment/modifyComment";
    }

    // 댓글 수정 POST
    @PutMapping("/{id}/modify")
    @PreAuthorize("isAuthenticated()")
    public String modifyComment(ModifyCommentForm form, @PathVariable Long id) {
        // 게시글 존재 여부
        Comment comment = commentService.getComment(id);

        // 권한 여부 체크
        if (!commentService.canModify(rq.getMember(), comment)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 게시글 수정
        commentService.modify(comment, form.getComment(), rq.getMember());

        return rq.redirect("/post/%d".formatted(comment.getPost().getId()), "댓글이 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteComment(@PathVariable Long id) {
        // 게시글이 존재 여부
        Comment comment = commentService.getComment(id);

        // 권한 여부 체크
        if (!commentService.canDelete(rq.getMember(), comment))
            throw new RuntimeException("삭제 권한이 없습니다.");

        commentService.delete(id);

        return rq.redirect("/post/%d".formatted(comment.getPost().getId()), "댓글이 삭제되었습니다.");
    }
}
