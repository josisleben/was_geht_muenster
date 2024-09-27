package ms.wasgeht.session.dto;


import lombok.Builder;
import ms.wasgeht.util.AbstractResponse;

import java.util.List;

@Builder
public class SessionListResponseDto extends AbstractResponse {
    private List<SessionResponseDto> sessions;
}
