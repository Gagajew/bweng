package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.PostDto;
import at.technikum.springrestbackend.entity.Post;
import at.technikum.springrestbackend.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return postService.getPostById(id);
    }

    @PostMapping
    public Post createPost(@RequestParam UUID userId, @Valid @RequestBody PostDto postDto) {
        return postService.createPost(postDto, userId);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable UUID id, @Valid @RequestBody PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
    }
}
