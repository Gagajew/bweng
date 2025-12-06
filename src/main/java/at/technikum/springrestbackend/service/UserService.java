package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import at.technikum.springrestbackend.dto.LoginRequestDto;


import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            System.out.println("Kein user mit id " + id);
            return null;
        }
        return user;
    }

    @Transactional
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            System.out.println("Kein user mit id " + id);
            return null;
        }

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            System.out.println("Kein user mit id  " + id);
        }
    }

    public User login(LoginRequestDto loginRequestDto) {
        // 1) User per E-Mail suchen
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2) Passwort vergleichen (aktuell im Klartext gespeichert)
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 3) Bei Erfolg: User zurückgeben
        return user;
    }

}