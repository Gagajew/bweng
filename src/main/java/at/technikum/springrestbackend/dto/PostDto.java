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
    private String text;

    @NotBlank
    @Size(min = 10, max = 30)
    private String title;

    @NotNull
    private Timestamp createdAt;

    public PostDto(String text){
        this.text = text;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
