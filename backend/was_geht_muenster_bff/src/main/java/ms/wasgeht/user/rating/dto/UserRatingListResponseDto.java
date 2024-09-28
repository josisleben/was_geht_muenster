package ms.wasgeht.user.rating.dto;

import lombok.Builder;
import ms.wasgeht.util.AbstractResponse;

import java.util.List;

@Builder
public class UserRatingListResponseDto extends AbstractResponse {
    private List<UserRatingResponseDto> ratings;
}
