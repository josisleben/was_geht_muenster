package ms.wasgeht.exceptions.session;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class SessionIsFullException extends AbstractMSHackException {
    public SessionIsFullException() {
        super("Session is full", HttpStatus.BAD_REQUEST);
    }
}
