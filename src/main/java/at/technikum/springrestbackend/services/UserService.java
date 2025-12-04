package at.technikum.springrestbackend.services;

import at.technikum.springrestbackend.dtos.UserDto;
import at.technikum.springrestbackend.entities.User;
import at.technikum.springrestbackend.mappers.UserMapper;
import at.technikum.springrestbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.technikum.springrestbackend.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService (UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        return userMapper.toUserDto(
                userRepository.save(
                        userMapper.toEntity(userDto)));
    }

    @Transactional
    public UserDto updateUser(UUID id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() ->{
            LOG.warn("User with id {} not found ", id);
            return new ResourceNotFoundException("User not found with id " + id);
        });
        userMapper.updateEntityFromDto(userDto, user);

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
}