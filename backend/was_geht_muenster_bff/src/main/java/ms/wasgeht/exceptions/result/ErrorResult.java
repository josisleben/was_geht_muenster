package ms.wasgeht.exceptions.result;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResult {
    private String status;
}
