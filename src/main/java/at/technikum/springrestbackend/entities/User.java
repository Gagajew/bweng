package at.technikum.springrestbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.*;

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

    // NEW: Country (ISO-2 code like AT, DE, ...)
    @NotBlank(message = "Country cannot be blank!")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country must be a valid ISO-2 country code (e.g., AT, DE).")
    @Column(nullable = false, length = 2)
    private String country;

    // NEW: Profile picture reference (e.g., MinIO object key / externalId)
    // If null -> frontend/backend can use placeholder
    @Column(name = "profile_picture_id")
    private String profilePictureId;

    @ManyToMany
    @JoinTable(
            name = "user_groups",
            joinColumns = @JoinColumn(name = "user_id"),       // FK auf user.id
            inverseJoinColumns = @JoinColumn(name = "group_id")// FK auf groups.id
    )
    private List<Group> groups = new ArrayList<>();

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(String profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public List<Group> getGroups(){
        return groups;
    }

    public void setGroups(List<Group> groups){
        this.groups = groups;
    }
}
