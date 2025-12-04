package at.technikum.springrestbackend.mappers;

import at.technikum.springrestbackend.dtos.GroupDto;
import at.technikum.springrestbackend.entities.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group group);

    @Mapping(target = "id", ignore = true)
    Group toEntity(GroupDto groupDto);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(GroupDto dto, @MappingTarget Group entity);
}
