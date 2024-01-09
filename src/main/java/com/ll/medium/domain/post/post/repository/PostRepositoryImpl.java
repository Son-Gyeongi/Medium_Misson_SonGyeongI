package com.ll.medium.domain.post.post.repository;

import com.ll.medium.domain.member.member.entity.QMember;
import com.ll.medium.domain.post.comment.entity.QComment;
import com.ll.medium.domain.post.post.entity.Post;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.ll.medium.domain.post.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Post> search(List<String> kwTypes, String kw, Pageable pageable) {
        // 한 페이지에 게시물 `10`개까지 보여줄 수 있고, 현재 `2` 페이지 이고, 검색어가 `1` 이라고 가정
        // kw 검색어

        BooleanBuilder builder = new BooleanBuilder();

        // 조인할 테이블들
        QMember author = new QMember("postAuthor");
        QComment postComment = new QComment("postComment");
        QMember postCommentAuthor = new QMember("postCommentAuthor");

        if (!kw.isBlank()) {
            // 기존의 조건을 리스트에 담는다.
            List<BooleanExpression> conditions = new ArrayList<>();

            if (kwTypes.contains("authorUsername")) {
                // containsIgnoreCase 대소문자 구분하지 않는다.
                conditions.add(author.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("title")) {
                conditions.add(post.title.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("body")) {
                conditions.add(post.body.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("commentAuthorUsername")) {
                conditions.add(postCommentAuthor.username.containsIgnoreCase(kw));
            }

            if (kwTypes.contains("commentBody")) {
                conditions.add(postComment.comment.containsIgnoreCase(kw));
            }

            // 조건 리스트를 'or 조건'으로 결합한다.
            BooleanExpression combinedCondition = conditions.stream()
                    .reduce(BooleanExpression::or)
                    .orElse(null);

            // 최종적으로 생성된 조건을 쿼리에 적용한다.
            if (combinedCondition != null) {
                builder.and(combinedCondition);
            }
        }

        // 여기서 isPublished가 TRUE인 조건을 추가
        builder.and(post.isPublished.isTrue());

        JPAQuery<Post> postQuery = jpaQueryFactory
                .selectDistinct(post) // SELECT Distinct A.*
                .from(post)
                .leftJoin(post.author, author)
                .leftJoin(post.comments, postComment)
                .leftJoin(postComment.author, postCommentAuthor)
                // 각 조인들의 on은 생략 JPA가 알아서 해준다.
                .where(builder);

        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(post.getType(), post.getMetadata());
            postQuery.orderBy(new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty())));
        }

        postQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());

        // 페이지 계산하기 위해서 총 게시물 수 쿼리를 한번 더 보낸다.
        JPAQuery<Long> totalQuery = jpaQueryFactory
                .select(post.countDistinct()) // SELECT COUNT(distinct A.id)
                .from(post)
                .leftJoin(post.author, author)
                .leftJoin(post.comments, postComment)
                .leftJoin(postComment.author, postCommentAuthor)
                // 각 조인들의 on은 생략 JPA가 알아서 해준다.
                .where(builder);

        return PageableExecutionUtils.getPage(postQuery.fetch(), pageable, totalQuery::fetchOne);
    }
}
