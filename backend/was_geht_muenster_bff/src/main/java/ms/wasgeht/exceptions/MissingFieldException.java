package ms.wasgeht.exceptions;

import org.springframework.http.HttpStatus;

public class MissingFieldException extends AbstractMSHackException{
    public MissingFieldException(String fieldName) {
        super("The Field " + fieldName + " is not filled!", HttpStatus.BAD_REQUEST);
    }
}
