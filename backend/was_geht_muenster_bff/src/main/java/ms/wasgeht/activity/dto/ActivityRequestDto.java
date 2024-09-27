package ms.wasgeht.activity.dto;

import lombok.Data;
import ms.wasgeht.activity.category.ActivityCategoryType;

@Data
public class ActivityRequestDto {
    private String name;
    private ActivityCategoryType category;
}
