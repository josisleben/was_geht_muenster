package ms.wasgeht.user.rating;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Builder
@Getter
@Document("col_user-rating")
public class UserRating {
    @Id
    private UUID id;
    private UUID creatorId;
    private UUID targetId;
    private String comment;
    private double rating;
}
