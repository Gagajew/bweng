package at.technikum.springrestbackend.it;

import at.technikum.springrestbackend.entities.Post;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.repositories.PostRepository;
import at.technikum.springrestbackend.repositories.UserRepository;
import at.technikum.springrestbackend.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostAttachmentIT extends AbstractIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired PostRepository postRepository;

    private UUID postId;
    private UserPrincipal userPrincipal;
    private UserPrincipal adminPrincipal;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();
        userRepository.deleteAll();

        User u = new User();
        u.setUsername("testuser");
        u.setEmail("test@example.com");
        u.setPassword("pw");
        u.setRole("ROLE_USER");
        u.setCountry("AT");
        u = userRepository.save(u);

        Post p = new Post();
        p.setTitle("Hello");
        p.setBody("Body");
        p.setUser(u);
        p = postRepository.save(p);

        postId = p.getId();

        userPrincipal = new UserPrincipal(u.getId(), u.getUsername(), u.getPassword(), "ROLE_USER");
        adminPrincipal = new UserPrincipal(u.getId(), u.getUsername(), u.getPassword(), "ROLE_ADMIN");
    }

    @Test
    void uploadAttachment_unauthorized_returns401() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "a.pdf", "application/pdf",
                "abc".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/api/posts/{id}/attachment", postId).file(file))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    void uploadAttachment_pdf_setsFieldsAndPersists() throws Exception {
        when(fileStorage.upload(any())).thenReturn("ext-123");

        MockMultipartFile file = new MockMultipartFile(
                "file", "doc.pdf", "application/pdf",
                "pdfbytes".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
                        multipart("/api/posts/{id}/attachment", postId)
                                .file(file)
                                .with(user(userPrincipal))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postId.toString())))
                .andExpect(jsonPath("$.attachmentId").value("ext-123"))
                .andExpect(jsonPath("$.attachmentType").value("PDF"))
                .andExpect(jsonPath("$.attachmentContentType").value("application/pdf"));

        Post saved = postRepository.findById(postId).orElseThrow();
        assertThat(saved.getAttachmentId()).isEqualTo("ext-123");
        assertThat(saved.getAttachmentType()).isEqualTo("PDF");
    }

    @Test
    void uploadAttachment_invalidType_returns400() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "a.txt", "text/plain",
                "abc".getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
                        multipart("/api/posts/{id}/attachment", postId)
                                .file(file)
                                .with(user(userPrincipal))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", containsString("Only JPG/PNG/WEBP images or PDF")));
    }

    @Test
    void downloadAttachment_returnsFileStreamAndHeaders() throws Exception {
        Post p = postRepository.findById(postId).orElseThrow();
        p.setAttachmentId("ext-123");
        p.setAttachmentContentType("application/pdf");
        p.setAttachmentType("PDF");
        postRepository.save(p);

        byte[] bytes = "pdfbytes".getBytes(StandardCharsets.UTF_8);
        when(fileStorage.load("ext-123")).thenReturn(new ByteArrayInputStream(bytes));

        mockMvc.perform(get("/api/posts/{id}/attachment", postId).with(user(userPrincipal)))
                .andExpect(status().isOk())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_DISPOSITION,
                        containsString("post-" + postId + ".pdf")
                ))
                .andExpect(content().contentType("application/pdf"))
                .andExpect(content().bytes(bytes));
    }

    @Test
    void adminEndpoint_withUser_returns403() throws Exception {
        mockMvc.perform(get("/api/posts/{id}", postId).with(user(userPrincipal)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Forbidden"));
    }

    @Test
    void adminEndpoint_withAdmin_returns200() throws Exception {
        mockMvc.perform(get("/api/posts/{id}", postId).with(user(adminPrincipal)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postId.toString())));
    }
}
