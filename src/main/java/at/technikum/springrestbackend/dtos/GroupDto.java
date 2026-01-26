package at.technikum.springrestbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class GroupDto {
    private UUID id;

    @NotBlank
    @Size(min = 5, max = 15, message = "Group name must be between 5 and 15 characters!")
    private String name;

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

}
