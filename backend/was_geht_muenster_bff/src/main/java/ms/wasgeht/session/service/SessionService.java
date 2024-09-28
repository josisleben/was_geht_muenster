package ms.wasgeht.session.service;

import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.activity.service.ActivityService;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.MissingFieldException;
import ms.wasgeht.exceptions.session.*;
import ms.wasgeht.session.SessionModel;
import ms.wasgeht.session.dto.SessionListResponseDto;
import ms.wasgeht.session.dto.SessionRequestDto;
import ms.wasgeht.session.dto.SessionResponseDto;
import ms.wasgeht.session.repository.SessionRepository;
import ms.wasgeht.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
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
        if(requestDto.getStart() == null)
            throw new MissingFieldException("start");
        if(requestDto.getEnd() == null)
            throw new MissingFieldException("end");
        if(requestDto.getStart() < Instant.now().toEpochMilli())
            throw new SessionInvalidDataException("Start Date must be in future");
        if(requestDto.getStart() > requestDto.getEnd())
            throw new SessionInvalidDataException("End Date must be greater than Start Date");
        this.activityService.getActivity(requestDto.getActivityId());
        SessionModel sessionModel = SessionModel.builder()
                .id(this.generateFreeId())
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .creator(creator.getId())
                .activityId(requestDto.getActivityId())
                .maxPerson(requestDto.getMaxPerson())
                .minPerson(requestDto.getMinPerson())
                .tags(requestDto.getTags() == null ? new ArrayList<>() : requestDto.getTags())
                .start(requestDto.getStart())
                .end(requestDto.getEnd())
                .member(new ArrayList<>())
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
                .start(session.getStart())
                .end(session.getEnd())
                .creator(session.getCreator())
                .build();
    }

    private List<SessionResponseDto> convertToSession(List<SessionModel> session){
        return session.stream().map(this::convertSession).toList();
    }

    public SessionListResponseDto getSessions(Long start, Long end, ActivityCategoryType category, int page, int size) {
        page--;
        if(start == null)
            start = Instant.now().toEpochMilli();
        if(end != null)
            return SessionListResponseDto.builder().sessions(this.convertToSession(this.sessionRepository.findAllByStartIsGreaterThanEqualAndEndLessThanEqualAndActivityIdIsIn(start, end, this.activityService.getActivitiesByCategory(category).stream().map(ActivityModel::getId).toList(), PageRequest.of(page, size)).getContent())).build();
        if(category == null)
            return SessionListResponseDto.builder().sessions(this.convertToSession(this.sessionRepository.findAllByStartIsGreaterThanEqual(start, PageRequest.of(page, size)).getContent())).build();
        return SessionListResponseDto.builder().sessions(this.convertToSession(this.sessionRepository.findAllByStartIsGreaterThanEqualAndActivityIdIsIn(start, this.activityService.getActivitiesByCategory(category).stream().map(ActivityModel::getId).toList(), PageRequest.of(page, size)).getContent())).build();
    }

    public void joinSession(UserModel requestedBy, UUID id) throws AbstractMSHackException {
        final SessionModel sessionModel = this.findById(id);
        if(sessionModel.getCreator().equals(requestedBy.getId()))
            throw new SessionSelfJoinException();
        if(sessionModel.getMember() == null)
            sessionModel.setMember(new ArrayList<>());
        if(sessionModel.getMember().contains(requestedBy.getId()))
            throw new SessionAlreadyInException();
        if(sessionModel.getMember().size() >= sessionModel.getMaxPerson())
            throw new SessionIsFullException();
        sessionModel.getMember().add(requestedBy.getId());
        this.save(sessionModel);
    }
}
