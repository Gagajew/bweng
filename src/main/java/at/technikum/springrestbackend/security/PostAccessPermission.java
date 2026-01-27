package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entities.Group;
import at.technikum.springrestbackend.entities.Post;
import at.technikum.springrestbackend.repositories.GroupPostRepository;
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
    private final GroupPostRepository groupPostRepository;

    @Override
    public boolean supports(Authentication authentication, String className) {
        return className.equals(Post.class.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId, String action) {
        Post post = postRepository.findById(resourceId).orElse(null);
        if (post == null) return false;

        //guest
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)){
            return "read".equalsIgnoreCase(action) && post.getVisibility() == Post.Visibility.PUBLIC;
        }

        //admin
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin) return true;

        //user
        if("read".equalsIgnoreCase(action)){
            return groupPostRepository.existsByPost_IdAndGroup_Members_Id(post.getId(), principal.getId());
        }

        //update/delete only owner
        if("update".equalsIgnoreCase(action) || "delete".equalsIgnoreCase(action)){
            return post.getUser() != null && post.getUser().getId().equals(principal.getId());
        }
        return false;

    }
}

