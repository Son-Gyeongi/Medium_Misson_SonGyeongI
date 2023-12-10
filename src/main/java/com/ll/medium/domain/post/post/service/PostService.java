package com.ll.medium.domain.post.post.service;

import com.ll.medium.domain.post.post.entity.Post;
import com.ll.medium.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();

        return posts;

//        return posts.stream().map(PostsForm::new).toList();
    }
}
