package ms.wasgeht.user.dto;

import lombok.Builder;
import lombok.Data;
import ms.wasgeht.util.AbstractResponse;

@Data
@Builder
public class UserLoginResponseDto extends AbstractResponse {
    private String sessionToken;
}
