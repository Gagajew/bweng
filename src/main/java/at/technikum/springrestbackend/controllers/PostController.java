package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.PostDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        return postService.getPostsForUser(principal.getId());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<PostDto> getAllPosts() {

        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto getPostById(@PathVariable UUID id) {

        return postService.getPostById(id);
    }

    @PostMapping
    public PostDto createPost(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody PostDto postDto) {
        if (userPrincipal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        return postService.createPost(postDto, userPrincipal.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.entities.Post', 'update')")
    public PostDto updatePost(@PathVariable UUID id, @Valid @RequestBody PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.entities.Post', 'delete')")
    public void deletePost(@PathVariable UUID id) {

        postService.deletePost(id);
    }
}
