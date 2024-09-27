package ms.wasgeht.user.dto;

import lombok.Builder;
import lombok.Data;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.util.AbstractResponse;

import java.util.Date;

@Data
@Builder
public class UserResponseDto extends AbstractResponse {
    private String username;
    private String avatar;
    private Date birthDate;
}
