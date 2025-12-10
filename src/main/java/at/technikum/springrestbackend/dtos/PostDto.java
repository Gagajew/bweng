package at.technikum.springrestbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;

public class PostDto {
    private UUID id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String body;

    @NotBlank
    @Size(min = 3, max = 30)
    private String title;

    private Timestamp createdAt;

    public PostDto(){}

    public PostDto(String body, String title){
        this.body = body;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt){
        this.createdAt = createdAt;
    }
}
