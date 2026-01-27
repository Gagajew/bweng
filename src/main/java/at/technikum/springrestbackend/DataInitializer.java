package at.technikum.springrestbackend;

import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if(userRepository.findByEmail("admin@example.com").isEmpty()){
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("Admin!!1234"));
            admin.setUsername("admin");
            admin.setRole("ROLE_ADMIN");

            userRepository.save(admin);
        }
    }
}
