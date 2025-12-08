package at.technikum.springrestbackend.service;

import at.technikum.springrestbackend.dto.UserDto;
import at.technikum.springrestbackend.entity.User;
import at.technikum.springrestbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exception.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found with username {}", username);
                    return new ResourceNotFoundException("User not found with username " + username);
                });
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User not found with id {}", id);
            return new ResourceNotFoundException("User not found with id " + id);
        });
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
        User user = getUserById(id);

        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with id {} was deleted.", id);
        } else {
            log.warn("Tried to delete user with id {}", id);
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }
}