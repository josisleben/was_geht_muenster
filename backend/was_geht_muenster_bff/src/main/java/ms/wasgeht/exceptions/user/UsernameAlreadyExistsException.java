package ms.wasgeht.exceptions.user;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends AbstractMSHackException {
    public UsernameAlreadyExistsException() {
        super("Username already exists", HttpStatus.BAD_REQUEST);
    }
}
