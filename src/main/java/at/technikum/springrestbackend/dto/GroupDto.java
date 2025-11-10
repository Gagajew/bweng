package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class GroupDto {

    @NotBlank
    @Size(min = 5, max = 15, message = "Group name must be between 5 and 15 characters!")
    private String name;

    // getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
