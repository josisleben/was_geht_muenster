package ms.wasgeht.user.rating.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public class UserRatingResponseDto {
    private UUID id;
    private UUID creatorId;
    private String comment;
    private double rating;
}
