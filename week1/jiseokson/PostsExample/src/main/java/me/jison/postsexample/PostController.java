package me.jison.postsexample;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {

    private final List<Post> posts = List.of(
            new Post(1L, "첫번째 게시글 입니다!", LocalDateTime.of(2024, 9, 1, 9, 30), "Hello world!"),
            new Post(2L, "이것은 두번째 게시글", LocalDateTime.of(2024, 9, 2, 10, 30), "👋 Hi there")
    );

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> postList(@RequestParam(required = false, defaultValue = "latest") String order) {
        Comparator<Post> comparator = Comparator.comparing(Post::createdAt).reversed();
        if (order.equals("older"))
            comparator = Comparator.comparing(Post::createdAt);

        List<Post> result = this.posts.stream()
                .sorted(comparator)
                .toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> postDetail(@PathVariable Long id) {
        Optional<Post> post = this.posts.stream()
                .filter(p -> p.id().equals(id))
                .findFirst();

        return post.map(p -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(p))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null));
    }

}
