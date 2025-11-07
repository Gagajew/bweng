package at.technikum.springrestbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
public class Group {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private UUID id = UUID.randomUUID();

    @NotNull
    @Size (min = 5, max = 15, message = "Group name must be between 5 and 15 characters!")
    private String name;

    @ManyToMany (mappedBy = "groups")
    private List<User> members;

    @OneToMany(mappedBy = "group")
    private List<GroupPost> groupPosts;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    // getters and setters

    public UUID getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public List<User> getMembers(){
        return members;
    }

    public void setMembers(List<User> members){
        this.members = members;
    }
}
