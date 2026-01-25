package at.technikum.springrestbackend.mappers;

import at.technikum.springrestbackend.dtos.PostDto;
import at.technikum.springrestbackend.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toPostDto(Post post);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "attachmentId", ignore = true)
    @Mapping(target = "attachmentType", ignore = true)
    @Mapping(target = "attachmentContentType", ignore = true)
    Post toEntity(PostDto postDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "attachmentId", ignore = true)
    @Mapping(target = "attachmentType", ignore = true)
    @Mapping(target = "attachmentContentType", ignore = true)
    void updateEntityFromDto(PostDto dto, @MappingTarget Post entity);
}
