package ms.wasgeht.session.service;

import lombok.Setter;
import ms.wasgeht.session.SessionModel;
import ms.wasgeht.session.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;


    public SessionModel save(SessionModel sessionModel) {
        return sessionRepository.save(sessionModel);
    }

    public SessionModel findById(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    public List<SessionModel> findAll() {
        return sessionRepository.findAll();
    }


}
