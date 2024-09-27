package ms.wasgeht.exceptions.session;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class SessionAlreadyInException extends AbstractMSHackException {
    public SessionAlreadyInException() {
        super("Already in the session", HttpStatus.BAD_REQUEST);
    }
}
