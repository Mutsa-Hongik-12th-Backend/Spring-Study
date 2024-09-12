package hello.yunakang.controller;

import hello.yunakang.domain.Post; // Post 클래스 import
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  // 게시글 리스트를 저장할 필드 (더미 데이터)
  private List<Post> posts = new ArrayList<>();

  // 더미 데이터 초기화
  public PostController() {
    posts.add(new Post(1, "첫 번째 게시글", LocalDateTime.of(2024, 9, 1, 9, 30), "첫 번째 게시글 내용"));
    posts.add(new Post(2, "두 번째 게시글", LocalDateTime.of(2024, 9, 2, 10, 0), "두 번째 게시글 내용"));
    posts.add(new Post(3, "세 번째 게시글", LocalDateTime.of(2024, 9, 3, 11, 30), "세 번째 게시글 내용"));
  }

  // 1. 전체 게시글 리스트 조회
  @GetMapping
  public ResponseEntity<List<Post>> getAllPosts(@RequestParam(value = "order", defaultValue = "latest") String order) {
    // 최신순 정렬
    if (order.equals("latest")) {
      posts.sort(Comparator.comparing(Post::getCreatedAt).reversed());
    }
    // 오래된 순 정렬
    else if (order.equals("older")) {
      posts.sort(Comparator.comparing(Post::getCreatedAt));
    }

    return ResponseEntity.ok(posts);
  }

  // 2. ID로 게시글 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable int id) {
    return posts.stream()
        .filter(post -> post.getId() == id)
        .findFirst()
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
