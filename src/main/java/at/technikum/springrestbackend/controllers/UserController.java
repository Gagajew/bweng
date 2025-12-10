package at.technikum.springrestbackend.controllers;

import at.technikum.springrestbackend.dtos.UserDto;
import at.technikum.springrestbackend.security.UserPrincipal;
import at.technikum.springrestbackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){

        this.userService = userService;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        if(userPrincipal==null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.UNAUTHORIZED,
                    "No authenticated user found"
            );
        }

        return userService.getUserById(userPrincipal.getId());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.entities.User', 'update')")
    public UserDto getUserById(@PathVariable UUID id) {

        return userService.getUserById(id);
    }


    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {

        return userService.createUser(userDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'at.technikum.springrestbackend.entities.User', 'update')")
    public UserDto updateUser(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {

        userService.deleteUser(id);
    }
}