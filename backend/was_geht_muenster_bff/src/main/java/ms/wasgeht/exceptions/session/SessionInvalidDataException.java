package ms.wasgeht.exceptions.session;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class SessionInvalidDataException extends AbstractMSHackException {
    public SessionInvalidDataException(String status) {
        super(status, HttpStatus.BAD_REQUEST);
    }
}
