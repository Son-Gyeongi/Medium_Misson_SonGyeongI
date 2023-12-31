package com.ll.medium.domain.post.post.controller;

import com.ll.medium.domain.member.member.entity.Member;
import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.form.ModifyForm;
import com.ll.medium.domain.post.post.form.WriteForm;
import com.ll.medium.domain.post.post.service.PostService;
import com.ll.medium.global.rq.Rq;
import com.ll.medium.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final Rq rq;

    // 공개된 글만 노출
    /*@GetMapping("/list")
    public String getPosts(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Page<Post> paging = postService.findAll(page);

        model.addAttribute("paging", paging);

        return "domain/post/post/list";
    }*/

    // 검색 조건 추가한 list
    @GetMapping("/list")
    public String list(@RequestParam(value = "kwTypes", defaultValue = "title, body") List<String> kwTypes,
                       @RequestParam(defaultValue = "") String kw,
                       @RequestParam(defaultValue = "1") int page, Model model) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(sorts));

        // kwTypes를 맵 형태로 바꿔서, 타임리프의 폼 안에서 쉽게 쓸 수 있도록 한다.
        Map<String, Boolean> kwTypesMap = kwTypes
                .stream()
                .collect(Collectors.toMap(
                        kwType -> kwType,
                        kwType -> true
                ));

        Page<Post> itemsPage = postService.search(kwTypes, kw, pageable);
        model.addAttribute("itemsPage", itemsPage);
        model.addAttribute("kwTypesMap", kwTypesMap);
        model.addAttribute("paging", itemsPage);

        return "domain/post/post/list";
    }

    // 내 글 목록 조회
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/myList")
    public String myList(Principal principal, Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        // 로그인한 회원 조회하는 회원 일치 여부
        // TODO 어차피 로그인 한 사용자만 볼 수 있어서 필요없을지도
        if (!rq.getMember().getUsername().equals(principal.getName()))
            throw new RuntimeException("권한이 없는 사용자입니다.");

        Page<Post> paging = postService.getMyList(rq.getMember(), page);

        model.addAttribute("paging", paging);

        return "domain/post/post/myPage";
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 회원만 글쓰기 접근 가능
    @GetMapping("/write")
    public String writePost() {

        return "domain/post/post/write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/write")
    public String writePost(@Valid WriteForm writeForm) {
        RsData<Post> post = postService.write(writeForm.getTitle(), writeForm.getBody(),
                writeForm.getIsPublished(), rq.getMember(), writeForm.getIsPaid());

        // 상세 페이지로 이동
        return rq.redirect("/post/%d".formatted(post.getData().getId()), post.getMsg());
    }

    // 게시글 상세내용
    @GetMapping("/{id}")
    public String detailPost(@PathVariable Long id, Model model) {
        Post post = postService.getDetailPost(id);

        model.addAttribute("post", post);

        return "domain/post/post/detail";
    }

    // 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@PathVariable Long id, Model model) {
        // 게시글 찾기
        Post post = postService.getPost(id);

        // 권한 여부 체크
        if (!postService.canModify(rq.getMember(), post)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        model.addAttribute("post", post);

        return "domain/post/post/modify";
    }

    // 게시글 수정 POST
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}/modify")
    public String modifyPost(@Valid ModifyForm modifyForm, @PathVariable Long id) {
        // 게시글 존재 여부
        Post post = postService.getPost(id);

        // 권한 여부 체크
        if (!postService.canModify(rq.getMember(), post)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        // 게시글 수정
        postService.modify(post, modifyForm.getTitle(), modifyForm.getBody(),
                modifyForm.getIsPublished(), modifyForm.getIsPaid(), rq.getMember());

        return rq.redirect("/post/%d".formatted(id), "%d번 게시글이 수정되었습니다.".formatted(id));
    }

    // 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        // 게시글이 존재 여부
        Post post = postService.getPost(id);

        // 권한 여부 체크
        if (!postService.canDelete(rq.getMember(), post))
            throw new RuntimeException("삭제 권한이 없습니다.");

        // 게시글 삭제
        postService.deletePost(post);

        return rq.redirect("/", "%d번 게시글이 삭제되었습니다.".formatted(id));
    }

    // 게시글 추천
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String postVote(@PathVariable Long id) {
        Post post = postService.getPost(id);
        Member voter = rq.getMember();

        postService.vote(post, voter);
        return rq.redirect("/post/%d".formatted(post.getId()));
    }
}
