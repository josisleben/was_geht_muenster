package ms.wasgeht.session.service;

import jakarta.websocket.Session;
import lombok.Setter;
import ms.wasgeht.activity.service.ActivityService;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.MissingFieldException;
import ms.wasgeht.exceptions.session.SessionInvalidDataException;
import ms.wasgeht.exceptions.session.SessionNotFoundException;
import ms.wasgeht.session.SessionModel;
import ms.wasgeht.session.dto.SessionRequestDto;
import ms.wasgeht.session.dto.SessionResponseDto;
import ms.wasgeht.session.repository.SessionRepository;
import ms.wasgeht.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ActivityService activityService;

    public SessionModel save(SessionModel sessionModel) {
        return sessionRepository.save(sessionModel);
    }

    public SessionModel findById(UUID id) throws SessionNotFoundException {
        return sessionRepository.findById(id).orElseThrow(SessionNotFoundException::new);
    }

    public List<SessionModel> findAll() {
        return sessionRepository.findAll();
    }

    private boolean exists(final UUID id){
        return this.sessionRepository.existsById(id);
    }

    public SessionResponseDto getSession(final UUID id) throws SessionNotFoundException {
        return this.convertSession(this.findById(id));
    }

    public void createSession(UserModel creator, SessionRequestDto requestDto) throws AbstractMSHackException {
        if(requestDto.getName() == null || requestDto.getName().isEmpty() || requestDto.getName().isBlank())
            throw new MissingFieldException("name");
        if(requestDto.getDescription() == null)
            requestDto.setDescription("");
        if(requestDto.getMinPerson() <= 1)
            throw new SessionInvalidDataException("Min Person must be greater than 1");
        if(requestDto.getMaxPerson() < requestDto.getMinPerson())
            throw new SessionInvalidDataException("Max Person must be greater than min person");
        if(requestDto.getActivityId() == null)
            throw new MissingFieldException("activityId");
        this.activityService.getActivity(requestDto.getActivityId());

        SessionModel sessionModel = SessionModel.builder()
                .id(this.generateFreeId())
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .creator(creator.getId())
                .activityId(requestDto.getActivityId())
                .maxPerson(requestDto.getMaxPerson())
                .minPerson(requestDto.getMinPerson())
                .tags(requestDto.getTags())
                .build();
        this.save(sessionModel);
    }

    private UUID generateFreeId(){
        UUID id;
        do{
            id = UUID.randomUUID();
        }while(this.exists(id));
        return id;
    }

    private SessionResponseDto convertSession(SessionModel session){
        return SessionResponseDto.builder()
                .id(session.getId())
                .name(session.getName())
                .description(session.getDescription())
                .member(session.getMember())
                .maxPerson(session.getMaxPerson())
                .minPerson(session.getMinPerson())
                .activityId(session.getActivityId())
                .tags(session.getTags())
                .creator(session.getCreator())
                .build();
    }

}
