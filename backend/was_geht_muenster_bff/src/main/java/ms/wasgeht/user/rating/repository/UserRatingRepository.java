package ms.wasgeht.user.rating.repository;

import ms.wasgeht.user.rating.UserRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRatingRepository extends MongoRepository<UserRating, UUID> {
    boolean existsByTargetIdEqualsAndCreatorIdEquals(final UUID targetId, final UUID creatorId);
    Page<UserRating> findAllByTargetIdEquals(final UUID targetId, final Pageable pageable);
    List<UserRating> findAllByTargetIdEquals(final UUID targetId);
}
