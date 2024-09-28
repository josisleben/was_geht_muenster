package ms.wasgeht.activity.dto;

import lombok.Builder;
import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.util.AbstractResponse;

import java.util.UUID;

@Builder
public class ActivityResponseDto extends AbstractResponse {
    private UUID id;
    private String name;
    private ActivityCategoryType category;
    private String avatar;
}
