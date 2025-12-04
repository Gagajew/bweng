package at.technikum.springrestbackend.dtos;


import java.util.UUID;

public class GroupPostDto {
    private UUID id;

    private UUID groupId;

    private UUID postId;

    public GroupPostDto(){}

    public GroupPostDto(UUID groupId, UUID postId) {
        this.groupId = groupId;
        this.postId = postId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {this.id = id;}

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
