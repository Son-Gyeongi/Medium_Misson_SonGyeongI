package com.ll.medium.domain.member.member.repository;

import com.ll.medium.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // username으로 멤버 찾기
    Optional<Member> findByUsername(String username);

    // 제일 마지막에 등록한 회원
    Optional<Member> findFirstByOrderByIdDesc();
}
