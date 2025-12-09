package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.PostDto;
import at.technikum.springrestbackend.entities.Post;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.mappers.PostMapper;
import at.technikum.springrestbackend.repositories.PostRepository;
import at.technikum.springrestbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::toPostDto).toList();
    }

    public PostDto getPostById(UUID id) {
         Post post = postRepository.findById(id).orElseThrow(() -> {
             LOG.warn("Could not find post with id {}", id);
            return new ResourceNotFoundException("Could not find post with id " + id);
        });
         return postMapper.toPostDto(post);
    }

    public List<PostDto> getPostsForUser(UUID userId) {
        List<Post> posts = postRepository.findByUserId(userId);

        return posts.stream().map(postMapper::toPostDto).collect(Collectors.toList());
    }

    @Transactional
    public PostDto createPost(PostDto postDto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            LOG.warn("Could not find user with id {}", userId);
            return new ResourceNotFoundException("Could not find user with id " + userId);
        });


        Post post = postMapper.toEntity(postDto);
        post.setUser(user);

        Post saved = postRepository.save(post);
        return postMapper.toPostDto(saved);
    }

    @Transactional
    public PostDto updatePost(UUID id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() ->{
            LOG.warn("Post not found with id {}", id);
            return new ResourceNotFoundException("Post not found with id: " + id);
        });

        postMapper.updateEntityFromDto(postDto, post);
        Post updated = postRepository.save(post);
        return postMapper.toPostDto(updated);
    }

    @Transactional
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found with id " + postId));

        postRepository.delete(post);
    }
}

