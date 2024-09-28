package ms.wasgeht.user.rating.dto;

import lombok.Builder;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.util.AbstractResponse;

@Builder
public class UserAverageRatingResponseDto extends AbstractResponse {
    private double avgRating;
}
