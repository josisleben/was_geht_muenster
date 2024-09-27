package ms.wasgeht.user.dto;

import lombok.Builder;
import lombok.Data;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.util.AbstractResponse;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto extends AbstractResponse {
    private UUID id;
    private String username;
    private String avatar;
    private Date birthDate;
}
