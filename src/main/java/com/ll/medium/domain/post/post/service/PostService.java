package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import com.ll.medium.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 공개된 글만 노출
    public Page<Post> findAll(int page) {
        // 페이징 조건
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts)); // 조회할 page, 한 페이지에 보여줄 게시물 갯수
        return postRepository.findAllByIsPublishedTrue(pageable);
    }

    // 내 글 목록 조회
    public Page<Post> getMyList(Member author, int page) {
        // 페이징 조건
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts)); // 조회할 page, 한 페이지에 보여줄 게시물 갯수
        return postRepository.findAllByAuthorIdOrderByCreatedDateDesc(author.getId(), pageable);
    }

    // 특정 회원(username)의 전체 글 리스트
    public Page<Post> getUserPosts(String username, int page) {
        // 페이징 조건
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdDate"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        // 유저 찾기
        Member user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        return postRepository.findByIsPublishedTrueAndAuthorId(user.getId(), pageable);
    }

    @Transactional
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

    // 게시글 가져오기
    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 하는 게시글이 없습니다.")
        );
    }

    // 게시글 상세 조회
    public Post getDetailPost(Long id) {
        return postRepository.findByIsPublishedTrueAndId(id).orElseThrow(
                () -> new IllegalArgumentException("해당 하는 게시글이 없습니다.")
        );
    }

    // 게시글 수정
    @Transactional
    public void modify(Post post, String title, String body, Boolean isPublished, Member author) {
        post.setTitle(title);
        post.setBody(body);
        post.setAuthor(author);
        post.setIsPublished(isPublished);
    }

    // 게시글 삭제
    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    // 권한 여부 체크는 서비스에서 해야함
    public boolean canModify(Member author, Post post) {
        if (author == null) return false;

        return post.getAuthor().equals(author);
    }

    public boolean canDelete(Member author, Post post) {
        if (author == null) return false;

        return post.getAuthor().equals(author);
    }
}
