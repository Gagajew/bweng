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
import at.technikum.springrestbackend.storage.FileStorage;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;


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
    private final FileStorage fileStorage;
    public record AttachmentDownload(InputStream stream, String contentType, String filename) {}



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
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id " + postId));

        String oldId = post.getAttachmentId();

        postRepository.delete(post);

        // Cleanup: Attachment in MinIO löschen
        if (oldId != null && !oldId.isBlank()) {
            try {
                fileStorage.delete(oldId);
            } catch (Exception e) {
                LOG.warn("Could not delete attachment {} for deleted post {}", oldId, postId, e);
            }
        }
    }

    @Transactional
    public PostDto uploadAttachment(UUID postId, MultipartFile file) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            LOG.warn("Post not found with id {}", postId);
            return new ResourceNotFoundException("Post not found with id " + postId);
        });

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();

        if (isImage(contentType)) {
            post.setAttachmentType("IMAGE");
        } else if (isPdf(contentType)) {
            post.setAttachmentType("PDF");
        } else {
            throw new IllegalArgumentException("Only JPG/PNG/WEBP images or PDF files are allowed");
        }
        // Altes Attachment merken (für Cleanup)
        String oldId = post.getAttachmentId();

        // Upload to MinIO (via FileStorage)
        String externalId = fileStorage.upload(file);

        post.setAttachmentId(externalId);
        post.setAttachmentContentType(contentType);

        Post saved = postRepository.save(post);

        // Cleanup: altes Attachment löschen (falls vorhanden)
        if (oldId != null && !oldId.isBlank()) {
            try {
                fileStorage.delete(oldId);
            } catch (Exception e) {
                LOG.warn("Could not delete old attachment {} for post {}", oldId, postId, e);
            }
        }

        return postMapper.toPostDto(saved);
    }


    private boolean isPdf(String ct) {
        return ct != null && ct.equalsIgnoreCase("application/pdf");
    }

    private boolean isImage(String ct) {
        return ct != null && (
                ct.equalsIgnoreCase("image/jpeg")
                        || ct.equalsIgnoreCase("image/png")
                        || ct.equalsIgnoreCase("image/webp")
        );
    }

    public AttachmentDownload downloadAttachment(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            LOG.warn("Post not found with id {}", postId);
            return new ResourceNotFoundException("Post not found with id " + postId);
        });

        if (post.getAttachmentId() == null || post.getAttachmentId().isBlank()) {
            throw new ResourceNotFoundException("Post has no attachment");
        }

        InputStream stream;
        try {
            stream = fileStorage.load(post.getAttachmentId());
        } catch (Exception e) {
            throw new ResourceNotFoundException("Attachment not found");
        }


        String ct = post.getAttachmentContentType();
        if (ct == null || ct.isBlank()) ct = "application/octet-stream";

        String ext = "bin";
        if ("application/pdf".equalsIgnoreCase(ct)) ext = "pdf";
        else if ("image/jpeg".equalsIgnoreCase(ct)) ext = "jpg";
        else if ("image/png".equalsIgnoreCase(ct)) ext = "png";
        else if ("image/webp".equalsIgnoreCase(ct)) ext = "webp";

        String filename = "post-" + postId + "." + ext;

        return new AttachmentDownload(stream, ct, filename);
    }


}

