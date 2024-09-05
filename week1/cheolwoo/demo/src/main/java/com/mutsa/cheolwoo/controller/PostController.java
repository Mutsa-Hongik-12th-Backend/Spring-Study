package com.mutsa.cheolwoo.controller;

import com.mutsa.cheolwoo.domain.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private List<Post> posts = new ArrayList<>();
    public PostController() {
        posts.add(new Post(1L, "첫 번째 게시글", LocalDateTime.of(2024, 9, 1, 9, 30), "내용 1"));
        posts.add(new Post(2L, "두 번째 게시글", LocalDateTime.of(2024, 9, 2, 10, 30), "내용 2"));
        posts.add(new Post(3L, "세 번째 게시글", LocalDateTime.of(2024, 9, 3, 11, 30), "내용 3"));
    }

    // 전체 게시글 리스트 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(name = "order", required = false, defaultValue = "latest") String order) {
        List<Post> sortedPosts = new ArrayList<>(posts);
        if ("older".equalsIgnoreCase(order)) {
            sortedPosts.sort(Comparator.comparing(Post::getCreatedAt));
        } else {
            sortedPosts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
        }
        return new ResponseEntity<>(sortedPosts, HttpStatus.OK);
    }

    // 단건 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable(name = "id") Long id) {
        Optional<Post> post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}