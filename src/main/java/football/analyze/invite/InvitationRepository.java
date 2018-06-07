package football.analyze.invite;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
public interface InvitationRepository extends MongoRepository<Invitation, String> {
}
