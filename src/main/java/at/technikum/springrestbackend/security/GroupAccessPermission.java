package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entities.Group;
import at.technikum.springrestbackend.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GroupAccessPermission implements AccessPermission {

    private final GroupRepository groupRepository;

    @Override
    public boolean supports(Authentication authentication, String className) {
        // only for group entity
        return className.equals(Group.class.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserPrincipal userPrincipal)) {
            return false;
        }

        UUID currentUserId = userPrincipal.getId();

        if(userPrincipal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }

        // load group from db
        return groupRepository.findById(resourceId)
                .map(group -> {
                    // every member can update
                    if (group.getMembers() != null) {
                        return group.getMembers().stream()
                                .anyMatch(u -> u.getId().equals(currentUserId));
                    }

                    return false;
                })
                .orElse(false); // group not found -> access denied
    }
}
