package at.technikum.springrestbackend.entity;

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
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy = "post")
    private List<GroupPost> groupPosts;


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


}
