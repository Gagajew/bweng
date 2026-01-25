package at.technikum.springrestbackend.mappers;

import at.technikum.springrestbackend.dtos.UserDto;
import at.technikum.springrestbackend.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(UserDto dto, @MappingTarget User entity);
}
