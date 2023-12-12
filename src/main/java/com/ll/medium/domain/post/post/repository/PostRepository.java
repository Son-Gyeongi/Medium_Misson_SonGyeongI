package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.post.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // isPublished가 TRUE인 공개된 글만 가져오기
    List<Post> findAllByIsPublishedTrue();

    // 최신글 30개 가져오기
    List<Post> findTop30ByOrderByCreatedDateDesc(Pageable pageable);

    // isPublished가 TRUE이고, 최근 30개의 게시글을 날짜 기준으로 내림차순으로 가져오는 쿼리 메소드
    List<Post> findByIsPublishedTrueOrderByCreatedDateDesc(Pageable pageable);
}
