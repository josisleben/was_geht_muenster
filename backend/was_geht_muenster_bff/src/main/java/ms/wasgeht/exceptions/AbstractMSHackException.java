package ms.wasgeht.exceptions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ms.wasgeht.exceptions.result.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractMSHackException extends Exception{

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final HttpStatus httpStatus;
    private final String status;

    public AbstractMSHackException(final String status, final HttpStatus httpStatus){
        this.status = status;
        this.httpStatus = httpStatus;
    }

    public ResponseEntity<String> toResult(){
        return new ResponseEntity<>(GSON.toJson(ErrorResult.builder().status(this.status)), this.httpStatus);
    }

}
