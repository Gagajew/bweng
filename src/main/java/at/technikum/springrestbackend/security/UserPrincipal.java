package at.technikum.springrestbackend.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.UUID;

@Getter
public class UserPrincipal extends User {
    private final UUID id;

    public UserPrincipal(UUID id, String username, String password, String role){
        super(username, password, List.of(new SimpleGrantedAuthority(role)));
        this.id = id;
    }

    public UUID getId(){

        return id;
    }
}
