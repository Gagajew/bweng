package at.technikum.springrestbackend.dtos;

import java.util.UUID;


public class AddGroupMemberDto {
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

}
