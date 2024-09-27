package ms.wasgeht.exceptions.session;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends AbstractMSHackException {
    public SessionNotFoundException() {
        super("Session not found", HttpStatus.NOT_FOUND);
    }
}
