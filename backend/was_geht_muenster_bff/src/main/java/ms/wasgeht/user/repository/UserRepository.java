package ms.wasgeht.user.repository;

import ms.wasgeht.user.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UserModel, UUID> {
}
