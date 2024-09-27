package ms.wasgeht.exceptions.activity;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class ActivityNotFoundException extends AbstractMSHackException {
    public ActivityNotFoundException() {
        super("Activity not found", HttpStatus.NOT_FOUND);
    }
}
