package at.technikum.springrestbackend.dto;

import java.util.UUID;

public class LoginResponseDto {

    private String message;
    private UUID userId;      // statt Long
    private String username;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String message, UUID userId, String username) {
        this.message = message;
        this.userId = userId;
        this.username = username;
    }

    public String getMessage() { return message; }
    public UUID getUserId() { return userId; }
    public String getUsername() { return username; }
}
