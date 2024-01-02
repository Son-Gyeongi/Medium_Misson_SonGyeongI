package com.ll.medium.domain.post.comment.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.comment.entity.Comment;
import com.ll.medium.domain.post.comment.repository.CommentRepository;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    // 댓글 작성
    @Transactional
    public RsData<Comment> write(Member author, String comment, Long postId) {
        Post post = postService.getPost(postId);

        Comment cmt = Comment.builder()
                .author(author)
                .post(post)
                .comment(comment)
                .build();

        commentRepository.save(cmt);

        return new RsData<>(
                "S-2",
                "작성이 완료되었습니다.",
                cmt
        );
    }

    public List<Comment> findAll(Long postId) {
        Post post = postService.getPost(postId);

        return post.getComments();
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    public Comment getComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당하는 댓글이 없습니다"));
    }

    public boolean canDelete(Member author, Comment comment) {
        if (author == null) return false;

        return comment.getAuthor().equals(author);
    }
}
