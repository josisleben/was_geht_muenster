package ms.wasgeht.session.dto;

import lombok.Builder;
import ms.wasgeht.util.AbstractResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
public class SessionResponseDto extends AbstractResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID activityId;
    private int minPerson;
    private int maxPerson;
    private List<String> tags = new ArrayList<String>();
    private UUID creator;
    private List<UUID> member = new ArrayList<UUID>();
}
