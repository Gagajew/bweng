package at.technikum.springrestbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class GroupDto {
    private UUID id;

    @NotBlank
    @Size(min = 5, max = 15, message = "Group name must be between 5 and 15 characters!")
    private String name;

    @Size(max = 10, message = "Emoji must be at most 10 characters!")
    private String emoji;

    @Size(max = 500, message = "Description must be at most 500 characters!")
    private String description;

    private UUID createdById;
    private String createdByUsername;

    public GroupDto(){}

    // getters and setters
    public UUID getId(){
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public void setCreatedById(UUID createdById) {
        this.createdById = createdById;
    }
    public String getCreatedByUsername() {
        return createdByUsername;
    }
    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
