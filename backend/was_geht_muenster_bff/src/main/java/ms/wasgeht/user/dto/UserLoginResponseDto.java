package ms.wasgeht.user.dto;

import lombok.Builder;
import lombok.Data;
import ms.wasgeht.util.AbstractResponse;

import java.util.UUID;

@Data
@Builder
public class UserLoginResponseDto extends AbstractResponse {
    private UUID id;
    private String sessionToken;
}
