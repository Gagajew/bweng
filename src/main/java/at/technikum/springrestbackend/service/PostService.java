package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.PostDto;
import at.technikum.springrestbackend.entity.Post;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.PostRepository;
import at.technikum.springrestbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(UUID id) {
        return postRepository.findById(id).orElseThrow(() -> {
            log.warn("Could not find post with id {}", id);
            return new ResourceNotFoundException("Could not find post with id " + id);
        });
    }

    @Transactional
    public Post createPost(PostDto postDto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Could not find user with id {}", userId);
            return new ResourceNotFoundException("Could not find user with id " + userId);
        });


        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setUser(user);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(UUID id, PostDto postDto) {
        Post post = getPostById(id);

        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(UUID id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            log.info("Deleted post with id {}", id);
        } else {
            log.warn("Post with id {} could not be found", id);
            throw new ResourceNotFoundException("Could not find post with id " + id);
        }
    }
}

