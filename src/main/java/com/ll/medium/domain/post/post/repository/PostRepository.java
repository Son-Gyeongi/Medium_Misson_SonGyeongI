package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // isPublished가 TRUE이고, 주어진 id에 적합한 글 가져오기
    Optional<Post> findByIsPublishedTrueAndId(Long id);

    // 특정 회원(username)의 전체 글 리스트
    Page<Post> findByIsPublishedTrueAndAuthorId(Long authorId, Pageable pageable);


    // 게시글 목록, isPublished가 TRUE인 공개된 글만 가져오기
    List<Post> findAllByIsPublishedTrue();

    // 게시글 목록(페이징), isPublished가 TRUE인 공개된 글만 가져오기
    Page<Post> findAllByIsPublishedTrue(Pageable pageable);

    // 최신글 30개 가져오기
    List<Post> findTop30ByOrderByCreatedDateDesc(Pageable pageable);

    // isPublished가 TRUE이고, 최근 30개의 게시글을 날짜 기준으로 내림차순으로 가져오는 쿼리 메소드
    List<Post> findByIsPublishedTrueOrderByCreatedDateDesc(Pageable pageable);

    // 내 글 목록 조회
    Page<Post> findAllByAuthorIdOrderByCreatedDateDesc(Long authorId, Pageable pageable);
}
