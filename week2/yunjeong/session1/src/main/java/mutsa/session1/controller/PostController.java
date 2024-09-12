package mutsa.session1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    public record Post(Long id, String title, LocalDateTime createdAt, String content) {
    }

    // 절대 해당 값을 바꾸지 않겠다는 뜻 -> private final 사용
    private final List<Post> posts = new ArrayList<>();

    // 생성자에서 더미 데이터 추가
    public PostController() {
        posts.add(new Post(1L, "First Post", LocalDateTime.of(2024, 9, 1, 9, 30), "first post."));
        posts.add(new Post(2L, "Second Post", LocalDateTime.of(2024, 9, 2, 9, 30), "second post."));
        posts.add(new Post(3L, "Third Post", LocalDateTime.of(2024, 9, 4, 9, 30), "third post."));
    }


    // 전체 게시글 리스트 조회
    // `latest`: 작성 시점을 기준으로 내림차순
    // `older`: 작성 시점을 기준으로 오름차순
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPostsByOrder(@RequestParam(name = "order", defaultValue = "latest") String order) {
        List<Post> sortedPosts = new ArrayList<>(posts);
        if (order.equals("latest")) {
            sortedPosts.sort(Comparator.comparing(Post::createdAt).reversed());
        }
        else if (order.equals("older")) {
            sortedPosts.sort(Comparator.comparing(Post::createdAt));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sortedPosts);
    }

    // 게시글의 id로 단건 조회
    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = posts.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();

        // post 값이 존재하면, 이를 ResponseEntity.ok(post)로 변환
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
