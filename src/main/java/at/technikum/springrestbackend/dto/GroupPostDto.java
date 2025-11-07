package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class GroupPostDto {
    @NotNull
    private UUID id;

    @NotNull
    private UUID groupId;

    @NotNull
    private UUID postId;

    public GroupPostDto(UUID groupId, UUID postId) {
        this.id = UUID.randomUUID();
        this.groupId = groupId;
        this.postId = postId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public UUID getPostId() {
        return postId;
    }


    public void setGroupId(UUID groudId) {
        this.groupId = groudId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

}
