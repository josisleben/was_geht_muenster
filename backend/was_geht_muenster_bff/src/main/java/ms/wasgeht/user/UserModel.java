package ms.wasgeht.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
public class UserModel {
    private UUID uuid;
    private String username;
    private Date birthDate;
    private String sessionToken;
}
