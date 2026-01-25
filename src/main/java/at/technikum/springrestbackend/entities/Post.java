package at.technikum.springrestbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "post")
    private List<GroupPost> groupPosts;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    // NEW: File reference in MinIO (object key / externalId)
    @Column(name = "attachment_id")
    private String attachmentId;

    // NEW: What kind of file (IMAGE or PDF)
    @Column(name = "attachment_type", length = 10)
    private String attachmentType;

    // NEW: Store content type for correct download response header (optional but clean)
    @Column(name = "attachment_content_type")
    private String attachmentContentType;

    //getters and setters

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getAttachmentId() { return attachmentId; }

    public void setAttachmentId(String attachmentId) { this.attachmentId = attachmentId; }

    public String getAttachmentType() { return attachmentType; }

    public void setAttachmentType(String attachmentType) { this.attachmentType = attachmentType; }

    public String getAttachmentContentType() { return attachmentContentType; }

    public void setAttachmentContentType(String attachmentContentType) { this.attachmentContentType = attachmentContentType; }



}
