package ms.wasgeht.activity.dto;

import lombok.Builder;
import ms.wasgeht.util.AbstractResponse;

import java.util.List;

@Builder
public class ActivityListResponseDto extends AbstractResponse {
    private List<ActivityResponseDto> activities;
}
