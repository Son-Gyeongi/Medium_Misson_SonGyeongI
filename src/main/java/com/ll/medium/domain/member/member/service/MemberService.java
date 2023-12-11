package com.ll.medium.domain.member.member.service;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.repository.MemberRepository;
import com.ll.medium.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RsData<Member> join(String username, String password) {
        if (findByUsername(username).isPresent()) { // null 검사
            return new RsData<>("F-1", "이미 사용중인 아이디입니다.");
        }

        password = passwordEncoder.encode(password);
        // 객체 생성
        Member member = Member.builder()
                .username(username)
                .password(password)
                .build();

        // 멤버 등록
        memberRepository.save(member);

        return new RsData<>(
                "S-1",
                "%s님 환영합니다".formatted(member.getUsername()),
                member
        );
    }

    // username으로 멤버 찾기
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    // 제일 마지막에 등록한 회원
    public Optional<Member> findLatest() {
        return memberRepository.findFirstByOrderByIdDesc();
    }

    public boolean count() {
        return memberRepository.count() > 0;
    }
}
