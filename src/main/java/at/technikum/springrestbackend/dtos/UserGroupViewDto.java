package at.technikum.springrestbackend.dtos;

import java.util.UUID;

public class UserGroupViewDto {
    private UUID userId;
    private String username;
    private String email;
    private UUID groupId;
    private String groupName;

    public UserGroupViewDto(UUID userId, String username, String email, UUID groupId, String groupName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public UUID getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public UUID getGroupId() {
        return groupId;
    }
    public String getGroupName() {
        return groupName;
    }
}
