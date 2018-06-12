package football.analyze.play;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
public interface TournamentRepository extends MongoRepository<Tournament, String> {

    Tournament findByName(String name);
}
