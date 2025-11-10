package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="group_post")
public class GroupPost {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "groupd_id", nullable = false)
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

    public Timestamp getCreatedAt(){
        return createdAt;
    }
}
