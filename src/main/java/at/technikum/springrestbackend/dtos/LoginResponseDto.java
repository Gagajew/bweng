package at.technikum.springrestbackend.dtos;

import java.util.UUID;

    public class LoginResponseDto {

        private String message;
        private UUID userId;      // statt Long
        private String username;
        private String token;

        public LoginResponseDto() {
        }

        public LoginResponseDto(String message, UUID userId, String username, String token) {
            this.message = message;
            this.userId = userId;
            this.username = username;
            this.token = token;
        }

        public String getMessage() { return message; }
        public UUID getUserId() { return userId; }
        public String getUsername() { return username; }
        public String getToken(){ return token;}
    }
