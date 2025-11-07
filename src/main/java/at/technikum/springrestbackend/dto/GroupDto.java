package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.UUID;

public class GroupDto {

    @NotNull
    private UUID id;

    @NotNull
    private Timestamp created_at;

    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    public GroupDto(String name){
        this.id = UUID.randomUUID();
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.name = name;
    }

    public UUID getId() {
        return id;
    }


    public Timestamp getCreated_at() {
        return created_at;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
