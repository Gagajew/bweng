package at.technikum.springrestbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="post")
public class Post {

    public enum Visibility {
        PUBLIC, PRIVATE
    }

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.PUBLIC;

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

@Column(name = "attachment_id")
private String attachmentId;

@Column(name = "attachment_content_type")
private String attachmentContentType;

@Column(name = "attachment_type")
private String attachmentType;



    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

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

    public Visibility getVisibility() {
        return visibility;
    }
    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

public String getAttachmentId() {
    return attachmentId;
}

public void setAttachmentId(String attachmentId) {
    this.attachmentId = attachmentId;
}

public String getAttachmentContentType() {
    return attachmentContentType;
}

public void setAttachmentContentType(String attachmentContentType) {
    this.attachmentContentType = attachmentContentType;
}

public String getAttachmentType() {
    return attachmentType;
}

public void setAttachmentType(String attachmentType) {
    this.attachmentType = attachmentType;
}


}
