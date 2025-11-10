package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotBlank
    @Size(min = 4, max = 16, message = "Username should be between 4 and 16 characters!")
    private String username;

    @NotBlank
    private String email;

    @NotBlank (message = "This field must be filled out!")
    @Size (min = 8, max = 15, message = "Password must be between 8 and 15 characters!")
    private String password;


    // getters and setters

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