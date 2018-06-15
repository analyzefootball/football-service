package football.analyze.system;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Hassan Mushtaq
 * @since 6/4/18
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByUsernameIgnoreCase(String username);
}
