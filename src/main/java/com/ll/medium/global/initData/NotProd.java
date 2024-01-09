package com.ll.medium.global.initData;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.member.member.service.MemberService;
import com.ll.medium.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

// 개발/테스트 샘플 데이터
@Profile("!prod")
@Configuration
@RequiredArgsConstructor
public class NotProd {

    @Autowired
    @Lazy // LAZY 안하면 본인이 보인 의존성 얻으려다가 뻗는다.
    private NotProd self; //자신에 대한 객체 호출, 많이 사용하는 패턴
    private final MemberService memberService;
    private final PostService postService;

    @Bean
    public ApplicationRunner initNotProdData() {
        return args -> {
            self.work1(); // self 외부에 있는 리모컨을 호출하는 거라서 @Transactional이 작동 된다.
        };
    }

    @Transactional
    public void work1() {
        if (memberService.count() > 0) return;

        // admin 사용자 생성
        Member admin = memberService.join("admin", "1234", true).getData();

        // 유료 멤버십 가입, 공개 글, 유료 글
        IntStream.rangeClosed(1, 20).forEach(
                i -> {
                    String username = "user" + i;
                    Member member = memberService.join(username, "1234", true).getData();

                    postService.write("제목" + i, "내용" + i, Boolean.TRUE, member, true);
                }
        );

        // 유료 멤버십 가입, 공개 글, 무료 글
        IntStream.rangeClosed(21, 40).forEach(
                i -> {
                    String username = "user" + i;
                    Member member = memberService.join(username, "1234", true).getData();

                    postService.write("제목" + i, "내용" + i, Boolean.TRUE, member, false);
                }
        );

        // 유료 멤버십 가입, 비공개 글, 유료 글
        IntStream.rangeClosed(41, 60).forEach(
                i -> {
                    String username = "user" + i;
                    Member member = memberService.join(username, "1234", true).getData();

                    postService.write("제목" + i, "내용" + i, Boolean.FALSE, member, true);
                }
        );

        // 유료 멤버십 가입, 비공개 글, 무료 글
        IntStream.rangeClosed(61, 80).forEach(
                i -> {
                    String username = "user" + i;
                    Member member = memberService.join(username, "1234", true).getData();

                    postService.write("제목" + i, "내용" + i, Boolean.FALSE, member, false);
                }
        );

        // 무료 멤버십 가입, 유료 글 작성 못함
        // 무료 멤버십 가입, 공개 글
        IntStream.rangeClosed(81, 100).forEach(
                i -> {
                    String username = "user" + i;
                    Member member = memberService.join(username, "1234", false).getData();

                    postService.write("제목" + i, "내용" + i, Boolean.TRUE, member, false);
                }
        );

        // 무료 멤버십 가입, 비공개 글
        IntStream.rangeClosed(101, 120).forEach(
                i -> {
                    String username = "user" + i;
                    Member member = memberService.join(username, "1234", false).getData();

                    postService.write("제목" + i, "내용" + i, Boolean.FALSE, member, false);
                }
        );
    }
}
