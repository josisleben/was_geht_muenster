package ms.wasgeht.util;

import ms.wasgeht.WasGehtMuensterBffApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponse {

    public ResponseEntity<String> toResponse(HttpStatus httpStatus){
        return new ResponseEntity<>(WasGehtMuensterBffApplication.GSON.toJson(this), httpStatus);
    }
}
