package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.PostDto;
import at.technikum.springrestbackend.services.PostService;
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
    public List<PostDto> getAllPosts() {

        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable UUID id) {

        return postService.getPostById(id);
    }

    @PostMapping
    public PostDto createPost(@RequestParam UUID userId, @Valid @RequestBody PostDto postDto) {
        return postService.createPost(postDto, userId);
    }

    @PutMapping("/{id}")
    public PostDto updatePost(@PathVariable UUID id, @Valid @RequestBody PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
    }
}
