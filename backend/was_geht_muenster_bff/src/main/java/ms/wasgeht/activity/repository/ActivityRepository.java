package ms.wasgeht.activity.repository;

import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.category.ActivityCategoryType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRepository extends MongoRepository<ActivityModel, UUID> {
    Optional<ActivityModel> findByNameEqualsIgnoreCase(final String name);
    boolean existsByNameEqualsIgnoreCase(final String name);
    List<ActivityModel> findAllByCategory(final ActivityCategoryType category);
}
