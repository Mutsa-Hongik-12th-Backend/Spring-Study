package third.demo.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import third.demo.domain.Post;
import third.demo.dto.PostRequestDTO;
import third.demo.dto.PostResponseDTO;
import third.demo.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        Post newPost = Post.builder()
                        .title(postRequestDTO.getTitle())
                        .content(postRequestDTO.getContent())
                        .memberId(postRequestDTO.getMemberId())
                        .build();

        Post savedPost = postRepository.save(newPost);

        return PostResponseDTO.builder()
                .postId(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .memberId(savedPost.getMemberId())
                .createdAt(savedPost.getCreatedAt())
                .build();
    }

    public List<PostResponseDTO> getPosts() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(post -> PostResponseDTO.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .memberId(post.getMemberId())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostsByMemberId(Long memberId) {
        List<Post> posts = postRepository.findAllByMemberId(memberId);

        return posts.stream()
                .map(post -> PostResponseDTO.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .memberId(post.getMemberId())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

    }
}
