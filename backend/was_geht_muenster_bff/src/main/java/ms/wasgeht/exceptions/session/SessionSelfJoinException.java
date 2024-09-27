package ms.wasgeht.exceptions.session;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class SessionSelfJoinException extends AbstractMSHackException {
    public SessionSelfJoinException() {
        super("You can not join your own session", HttpStatus.BAD_REQUEST);
    }
}
