package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.PostDto;
import at.technikum.springrestbackend.entities.Post;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.entities.Role;
import at.technikum.springrestbackend.mappers.PostMapper;
import at.technikum.springrestbackend.repositories.PostRepository;
import at.technikum.springrestbackend.repositories.UserRepository;
import at.technikum.springrestbackend.exceptions.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);


    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private UserRepository userRepository;

    private void checkEditPermission(User currentUser, Post post) {
        // Admin darf immer
        if (currentUser.getRole() == Role.ADMIN) {
            return;
        }

        // Normaler User darf nur seinen eigene Posts bearbeiten/löschen
        if (!post.getUser().getId().equals(currentUser.getId())) {
            LOG.warn("Permission denied: user {} tried to modify post {}",
                    currentUser.getId(), post.getId());
            throw new PermissionDeniedException("You are not allowed to modify this post.");
        }
    }


    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository){
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }

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
    public PostDto updatePost(UUID id, PostDto postDto, UUID userId) {
        Post post = postRepository.findById(id).orElseThrow(() ->{
            LOG.warn("Post not found with id {}", id);
            return new ResourceNotFoundException("Post not found with id: " + id);
        });

        User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
            LOG.warn("Could not find user with id {}", currentUserId);
            return new ResourceNotFoundException("Could not find user with id " + currentUserId);
        });

        // Berechtigung prüfen
        checkEditPermission(currentUser, post);

        postMapper.updateEntityFromDto(postDto, post);
        Post updated = postRepository.save(post);
        return postMapper.toPostDto(updated);
    }

    @Transactional
    public void deletePost(UUID id, UUID currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(() -> {
            LOG.warn("Post with id {} could not be found", id);
            return new ResourceNotFoundException("Could not find post with id " + id);
        });

        User currentUser = userRepository.findById(currentUserId).orElseThrow(() -> {
            LOG.warn("Could not find user with id {}", currentUserId);
            return new ResourceNotFoundException("Could not find user with id " + currentUserId);
        });

        // Berechtigungen prüfen
        checkEditPermission(currentUser, post);

        postRepository.delete(post);
        LOG.info("Post with id {} was deleted by user {}", id, currentUserId);
    }

}

