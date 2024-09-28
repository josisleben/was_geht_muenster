package ms.wasgeht.exceptions.user;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class UserRatingAlreadyExistsException extends AbstractMSHackException {
    public UserRatingAlreadyExistsException() {
        super("User Rating already exists", HttpStatus.BAD_REQUEST);
    }
}
