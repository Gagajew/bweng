package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserAccessPermission implements AccessPermission{

    @Override
    public boolean supports(Authentication authentication, String className){
        //check if permission is for user entities
        return className.equals(User.class.getName());
    }

    @Override
    public boolean hasPermission(Authentication authentication, UUID resourceId, String action){

        //guest
        if(authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)){
            return false;
        }

        //admin
        boolean isAdmin = principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        if(isAdmin) return true;

        //user
        if("read".equalsIgnoreCase(action) ||
        "update".equalsIgnoreCase(action)
        ||"delete".equalsIgnoreCase(action)){
            return principal.getId().equals(resourceId);
        }
        return false;
    }

}
