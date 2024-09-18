package third.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import third.demo.dto.PostRequestDTO;
import third.demo.dto.PostResponseDTO;
import third.demo.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO createdPost = postService.createPost(postRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> posts = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByMemberId(@PathVariable Long memberId) {
        List<PostResponseDTO> posts = postService.getPostsByMemberId(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
}
