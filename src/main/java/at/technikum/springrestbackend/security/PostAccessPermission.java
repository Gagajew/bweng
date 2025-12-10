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
    private final GroupRepository groupRepository;

    @Override
    public boolean supports(Authentication authentication, String className) {
        //Signals Evaluator that it's responsible for the posts
        return className.equals(Post.class.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId){
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        if(principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }

        Group group = groupRepository.findById(resourceId).orElse(null);
        if(group == null){
            return false;
        }

        if(group.getMembers() != null){
            boolean isMember = group.getMembers().stream()
                    .anyMatch(u -> u.getId().equals(principal.getId()));
            if(isMember){
                return true;
            }
        }

        return principal.getId().equals(resourceId);


    }
}
