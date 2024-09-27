package ms.wasgeht.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@Document("col_user")
public class UserModel {
    @Id
    private UUID id;
    private String username;
    private Date birthDate;
    private String sessionToken;
    private byte[] avatar;
}
