package ms.wasgeht.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDto {
    private String sessionToken;
}
