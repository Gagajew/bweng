package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.UserDto;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.mappers.UserMapper;
import at.technikum.springrestbackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import at.technikum.springrestbackend.dtos.LoginRequestDto;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    LOG.warn("User not found with username {}", username);
                    return new UsernameNotFoundException("User not found with username " + username);
                });
    }

    public List<UserDto> getAllUsers() {

        return userRepository.findAll().stream().map(userMapper::toUserDto).toList();
    }

    public UserDto getUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            LOG.warn("User not found with id {}", id);
            return new ResourceNotFoundException("User not found with id " + id);
        });
        return userMapper.toUserDto(user);
    }


    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(user.getRole() == null){
            user.setRole("ROLE_USER");
        }
        User saved = userRepository.save(user);
        return userMapper.toUserDto(saved);
    }

    @Transactional
    public UserDto updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() ->{
            LOG.warn("User with id {} not found ", id);
            return new ResourceNotFoundException("User not found with id " + id);
        });
        userMapper.updateEntityFromDto(userDto, user);

        if(userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        User updated = userRepository.save(user);
        return userMapper.toUserDto(updated);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            LOG.info("User with id {} was deleted.", id);
        } else {
            LOG.warn("Tried to delete user with id {}", id);
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    public User login(LoginRequestDto loginRequestDto) {
        // 1) User per E-Mail suchen
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> {
                    LOG.warn("Login failed: user not found for email {}", loginRequestDto.getEmail());
                    return new ResourceNotFoundException("User not found");
                });

        // 2) Passwort vergleichen (noch im Klartext – später mit Hash ersetzen)
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            LOG.warn("Login failed: invalid password for email {}", loginRequestDto.getEmail());
            throw new RuntimeException("Invalid password");
        }

        // 3) Bei Erfolg: User-Entity zurückgeben
        return user;
    }
}