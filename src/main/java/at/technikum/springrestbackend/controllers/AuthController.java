package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.LoginRequestDto;
import at.technikum.springrestbackend.dtos.LoginResponseDto;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.services.UserService;
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
                    user.getId(),
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
