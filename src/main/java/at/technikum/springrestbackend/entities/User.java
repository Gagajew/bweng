package at.technikum.springrestbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank (message = "Username cannot be blank!")
    @Size (min = 4, max = 16, message = "Username should be between 4 and 16 characters!")
    @Column (unique = true, nullable = false)
    private String username;

    @NotBlank (message = "Email cannot be blank!")
    @Email
    @Column (unique = true, nullable = false)
    private String email;

    @NotBlank (message = "This field must be filled out!")
    @Size (max = 100, message = "Password can only have maximum of 100 characters!")
    private String password;

    @Column(nullable = false)
    private String role;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;


    //getters and setters

    public UUID getId(){

        return id;
    }

    public String getUsername(){

        return username;
    }

    public void setUsername(String username){

        this.username = username;
    }

    public String getEmail(){

        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){

        return password;
    }

    public void setPassword(String password){

        this.password = password;
    }

    public String getRole(){

        return role;
    }

    public void setRole(String role){

        this.role = role;
    }


}