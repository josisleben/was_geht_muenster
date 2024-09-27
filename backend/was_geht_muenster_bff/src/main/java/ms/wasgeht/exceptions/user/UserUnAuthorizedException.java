package ms.wasgeht.exceptions.user;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class UserUnAuthorizedException extends AbstractMSHackException {
    public UserUnAuthorizedException() {
        super("You are not authorized", HttpStatus.UNAUTHORIZED);
    }
}
