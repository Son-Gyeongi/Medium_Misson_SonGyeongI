package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 공개된 글만 노출
    public List<Post> findAll() {
        List<Post> posts = postRepository.findAllByIsPublishedTrue();

        return posts;
    }

    public RsData<Post> write(String title, String body, Boolean isPublished, Member author) {
        Post post = new Post(title, body, isPublished, author);

        postRepository.save(post);

        return new RsData<>(
                "S-2",
                "작성이 완료되었습니다.",
                post
        );
    }

    // 공개된 글만 노출
    // 최신글 30개 가져오기
    public List<Post> getLatest30Posts() {
        Pageable pageable = PageRequest.of(0, 30); // 0은 페이지 번호, 30은 페이지 크기
        return postRepository.findByIsPublishedTrueOrderByCreatedDateDesc(pageable);
    }

    // 상세 게시글 가져오기
    public Post detailPost(Long id) {
        return postRepository.findByIsPublishedTrueAndId(id).orElseThrow();
    }
}
