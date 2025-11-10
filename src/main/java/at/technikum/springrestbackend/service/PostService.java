package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.PostDto;
import at.technikum.springrestbackend.entity.Post;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.PostRepository;
import at.technikum.springrestbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(UUID id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            System.out.println("Error: Post not found with id: " + id);
            return null;
        }
        return post;
    }

    @Transactional
    public Post createPost(PostDto postDto, UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("Error: User not found with id: " + userId);
            return null;
        }

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());
        post.setUser(user);

        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(UUID id, PostDto postDto) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            System.out.println("Error: Post not found with id: " + id);
            return null;
        }

        post.setTitle(postDto.getTitle());
        post.setBody(postDto.getBody());

        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(UUID id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            System.out.println("Error: Post not found with id: " + id);
        }
    }
}

