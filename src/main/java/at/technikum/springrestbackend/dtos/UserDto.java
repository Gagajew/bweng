package at.technikum.springrestbackend.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UserDto {
    private UUID id;

    @NotBlank
    @Size(min = 4, max = 16, message = "Username should be between 4 and 16 characters!")
    private String username;

    @NotBlank
    private String email;

    @NotBlank (message = "This field must be filled out!")
    @Size (min = 8, max = 15, message = "Password must be between 8 and 15 characters!")
    @Column(nullable = false)
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=" +
            ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$",
            message = "Password must contain at least one uppercase letter, " +
                    "one lowercase letter, one number, and one special character!")
    private String password;
    

    // getters and setters

    public UUID getId(){
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}