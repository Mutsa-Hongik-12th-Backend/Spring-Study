package hello.hello_spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MyRestController {

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> postList() {
        List<Post> posts = new ArrayList<>();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(posts);
    }

}