package at.technikum.springrestbackend.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {
    @Size(min = 4, max = 16, message = "Username should be between 4 and 16 characters!")
    private String username;

    // wenn du willst:
    // @Email(message="Invalid email format!")
    private String email;

    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters!")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,20}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character!"
    )
    private String password;


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
