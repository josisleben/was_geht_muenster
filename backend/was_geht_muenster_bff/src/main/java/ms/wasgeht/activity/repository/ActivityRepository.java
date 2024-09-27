package ms.wasgeht.activity.repository;

import ms.wasgeht.activity.ActivityModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRepository extends MongoRepository<ActivityModel, UUID> {
    Optional<ActivityModel> findByNameEqualsIgnoreCase(final String name);
    boolean existsByNameEqualsIgnoreCase(final String name);
}
