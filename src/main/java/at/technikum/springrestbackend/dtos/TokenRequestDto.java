package at.technikum.springrestbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
