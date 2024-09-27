package ms.wasgeht.session.repository;

import ms.wasgeht.session.SessionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SessionRepository extends MongoRepository<SessionModel, UUID> {
}
