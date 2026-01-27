package at.technikum.springrestbackend.mappers;

import at.technikum.springrestbackend.dtos.UserDto;
import at.technikum.springrestbackend.dtos.UserUpdateDto;
import at.technikum.springrestbackend.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto userDto);


    //for PUT only not-null fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDto(UserUpdateDto dto, @MappingTarget User user);
}
