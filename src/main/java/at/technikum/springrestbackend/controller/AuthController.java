package at.technikum.springrestbackend.controller;

import at.technikum.springrestbackend.dto.LoginRequestDto;
import at.technikum.springrestbackend.dto.LoginResponseDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request) {
        try {
            User user = userService.login(request);

            LoginResponseDto response = new LoginResponseDto(
                    "Login successful",
                    user.getId(),          // UUID
                    user.getUsername()
            );

            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            LoginResponseDto response =
                    new LoginResponseDto("Invalid email or password", null, null);
            return ResponseEntity.status(401).body(response);
        }
    }
}
