package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class GroupPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

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
