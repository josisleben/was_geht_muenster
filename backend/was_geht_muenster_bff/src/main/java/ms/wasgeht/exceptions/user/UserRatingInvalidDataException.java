package ms.wasgeht.exceptions.user;

import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.http.HttpStatus;

public class UserRatingInvalidDataException extends AbstractMSHackException {

    public UserRatingInvalidDataException(String status) {
        super(status, HttpStatus.BAD_REQUEST);
    }
}
