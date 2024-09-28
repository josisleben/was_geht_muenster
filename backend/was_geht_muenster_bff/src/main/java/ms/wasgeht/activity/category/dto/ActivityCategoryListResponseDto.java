package ms.wasgeht.activity.category.dto;

import lombok.Builder;
import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.activity.dto.ActivityResponseDto;
import ms.wasgeht.util.AbstractResponse;

import java.util.List;

@Builder
public class ActivityCategoryListResponseDto extends AbstractResponse {
    private List<ActivityCategoryResponseDto> categories;
}
