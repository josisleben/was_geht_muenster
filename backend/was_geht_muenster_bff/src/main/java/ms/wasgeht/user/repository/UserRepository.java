package ms.wasgeht.user.repository;

import ms.wasgeht.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserModel, UUID> {
    boolean existsBySessionTokenEquals(final String sessionToken);
    boolean existsByUsernameEqualsIgnoreCase(final String username);
    Optional<UserModel> findBySessionTokenEquals(final String sessionToken);
}
