package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entities.Group;
import at.technikum.springrestbackend.entities.Post;
import at.technikum.springrestbackend.repositories.GroupRepository;
import at.technikum.springrestbackend.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Permission;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostAccessPermission implements AccessPermission {

    private final PostRepository postRepository;

    @Override
    public boolean supports(Authentication authentication, String className) {
        return className.equals(Post.class.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        // Admin can do everything
        if (principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return true;
        }

        // load Post
        Post post = postRepository.findById(resourceId).orElse(null);
        if (post == null || post.getUser() == null) {
            return false;
        }

        // Owner can
        return post.getUser().getId().equals(principal.getId());
    }
}

