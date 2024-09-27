package ms.wasgeht.session.repository;

import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.session.SessionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SessionRepository extends MongoRepository<SessionModel, UUID> {
    Page<SessionModel> findAllByStartIsGreaterThanEqualAndEndLessThanEqualAndActivityIdIsIn(final long start, final long end, final List<UUID> activities, Pageable pageable);
    Page<SessionModel> findAllByStartIsGreaterThanEqualAndActivityIdIsIn(final long start, final List<UUID> activities, Pageable pageable);
    Page<SessionModel> findAllByStartIsGreaterThanEqual(final long start, Pageable pageable);
}
