package ms.wasgeht.session.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SessionRequestDto {
    private String name;
    private String description;
    private Long start;
    private Long end;
    private UUID activityId;
    private int minPerson;
    private int maxPerson;
    private List<String> tags = new ArrayList<String>();
}
