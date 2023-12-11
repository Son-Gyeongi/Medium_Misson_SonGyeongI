package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();

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

    // 최신글 30개 가져오기
    public List<Post> getLatest30Posts() {
        Pageable pageable = PageRequest.of(0, 30); // 0은 페이지 번호, 30은 페이지 크기
        return postRepository.findTop30ByOrderByCreatedDateDesc(pageable);
    }
}
