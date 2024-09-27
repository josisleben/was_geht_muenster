package ms.wasgeht.user.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserLoginRequestDto {
    private String username;
    private Long birthDate;
    private String avatar;
}
