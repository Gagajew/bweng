package at.technikum.springrestbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name="groups")
public class Group {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Size (min = 5, max = 15, message = "Group name must be between 5 and 15 characters!")
    private String name;

    @OneToMany(mappedBy = "group")
    private List<GroupPost> groupPosts;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToMany(mappedBy = "groups")
    private Set<User> members = new HashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdBy;

    @Column(name = "created_by_username", nullable = false)
    private String createdByUsername;


    // getters and setters

    public UUID getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Set<User> getMembers(){

        return members;
    }

    public void setMembers(Set<User> members){

        this.members = members;
    }

    public User getCreatedBy(){
        return createdBy;
    }

    public void setCreatedBy(User createdBy){
        this.createdBy = createdBy;
    }

    public String getCreatedByUsername(){
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername){
        this.createdByUsername = createdByUsername;
    }
}
