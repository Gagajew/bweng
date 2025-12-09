package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.PostDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping("/my-posts")
    public List<PostDto> getMyPosts(@AuthenticationPrincipal UserPrincipal principal){
        return postService.getPostsForUser(principal.getId());
    }

    @GetMapping
    public List<PostDto> getAllPosts() {

        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto getPostById(@PathVariable UUID id) {

        return postService.getPostById(id);
    }

    @PostMapping
    public PostDto createPost(@RequestParam UUID userId, @Valid @RequestBody PostDto postDto) {
        return postService.createPost(postDto, userId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto updatePost(@PathVariable UUID id, @Valid @RequestBody PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
    }
}
