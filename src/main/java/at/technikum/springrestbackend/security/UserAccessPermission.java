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
    public boolean hasPermission(Authentication authentication, UUID resourceId){
        Object principal = authentication.getPrincipal();

        if(principal instanceof UserPrincipal userPrincipal){
            //only logged in users can update
            return userPrincipal.getId().equals(resourceId);
        }
        return false;
    }

}
