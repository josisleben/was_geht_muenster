package ms.wasgeht.exceptions.activity;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class ActivityAlreadyExistsException extends AbstractMSHackException {
    public ActivityAlreadyExistsException() {
        super("Activity already exists with the name", HttpStatus.BAD_REQUEST);
    }
}
