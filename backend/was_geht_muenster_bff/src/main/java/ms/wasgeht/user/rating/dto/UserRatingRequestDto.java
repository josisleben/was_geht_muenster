package ms.wasgeht.user.rating.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserRatingRequestDto {
    private UUID targetId;
    private String comment;
    private long rating;
}
