package ms.wasgeht.exceptions.session;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class SessionAlreadyExistsException extends AbstractMSHackException {
    public SessionAlreadyExistsException() {
        super("Session already exists", HttpStatus.BAD_REQUEST);
    }
}
