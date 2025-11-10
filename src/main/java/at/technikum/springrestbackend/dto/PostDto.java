package at.technikum.springrestbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.UUID;

public class PostDto {
    @NotNull
    private UUID id;

    @NotBlank
    @Size(min = 10, max = 100)
    private String body;

    @NotBlank
    @Size(min = 10, max = 30)
    private String title;

    @NotNull
    private Timestamp createdAt;

    public PostDto(String body){
        this.body = body;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
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


}
