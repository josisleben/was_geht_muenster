package ms.wasgeht.activity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ms.wasgeht.activity.category.ActivityCategoryType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document("col_activity")
public class ActivityModel {
    @Id
    private UUID id;
    private String name;
    private ActivityCategoryType category;
    private byte[] avatar;
}
