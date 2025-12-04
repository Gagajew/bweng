package at.technikum.springrestbackend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="group_post")
public class GroupPost {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @CreationTimestamp
    private Timestamp createdAt;

    //getters and setters

    public UUID getId(){

        return id;
    }

    public Group getGroup(){

        return group;
    }

    public void setGroup(Group group){

        this.group = group;
    }

    public Post getPost(){

        return post;
    }

    public void setPost(Post post){

        this.post = post;
    }
}
