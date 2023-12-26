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
        if (memberService.count()) return;

        // 사용자 생성
        Member admin = memberService.join("admin", "1234", true).getData();
        Member member1 = memberService.join("user1", "1234", true).getData();
        Member member2 = memberService.join("user2", "1234", false).getData();
        Member member3 = memberService.join("user3", "1234", false).getData();

        // 게시글 생성
        postService.write("제목1", "내용1", Boolean.TRUE, member1, true); // 공개글
        postService.write("제목2", "내용2", Boolean.FALSE, member1, false); // 비공개글
        postService.write("제목3", "내용3", Boolean.TRUE, member1, false);
        postService.write("제목4", "내용4", Boolean.TRUE, member2, true);
        postService.write("제목5", "내용5", Boolean.FALSE, member2, false);

        IntStream.rangeClosed(6, 35).forEach(
                i -> {
                    String title = "제목" + i;
                    String body = "내용" + i;

                    postService.write(title, body, Boolean.TRUE, member3, true);
                }
        );

        IntStream.rangeClosed(36, 40).forEach(
                i -> {
                    String title = "제목" + i;
                    String body = "내용" + i;

                    postService.write(title, body, Boolean.FALSE, member3, false);
                }
        );

        IntStream.rangeClosed(40, 50).forEach(
                i -> {
                    String title = "제목" + i;
                    String body = "내용" + i;

                    postService.write(title, body, Boolean.TRUE, member3, false);
                }
        );
    }
}
