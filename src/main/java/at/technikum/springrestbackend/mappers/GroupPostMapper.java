package at.technikum.springrestbackend.mappers;

import at.technikum.springrestbackend.dtos.GroupPostDto;
import at.technikum.springrestbackend.entities.GroupPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper (componentModel = "spring")
public interface GroupPostMapper {
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "postId", source = "post.id")
    GroupPostDto toDto(GroupPost groupPost);

    @Mapping(target = "group", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "id", ignore = true)
    GroupPost toEntity(GroupPostDto groupPostDto);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "post", ignore = true)
    void updateEntityFromDto(GroupPostDto dto, @MappingTarget GroupPost entity);
}
