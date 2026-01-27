package at.technikum.springrestbackend.dtos;

import java.util.UUID;

public class GroupPostResponseDto {
    private UUID id;
    private UUID groupId;
    private UUID postId;
    private UUID userId;
    private String username;

    public GroupPostResponseDto() {}

    public GroupPostResponseDto(UUID id, UUID groupId, UUID postId, UUID userId, String username) {
        this.id = id;
        this.groupId = groupId;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }

    public UUID getPostId() {
        return postId;
    }

    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
