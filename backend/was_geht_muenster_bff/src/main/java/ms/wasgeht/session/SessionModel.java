package ms.wasgeht.session;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Document("col_session")
public class SessionModel {
    @Id
    private UUID id;
    private String name;
    private String description;
    private int minPerson;
    private int maxPerson;
    private List<String> tags = new ArrayList<String>();
    private UUID creator;
    private List<UUID> member = new ArrayList<UUID>();
}
