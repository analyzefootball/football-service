package football.analyze.system;

import football.analyze.play.Match;

import java.util.List;

public interface UserService {

    User signUpUser(String inviteId, User user);

    void updateUser(String username, User user);

    void saveUser(User user);

    List<User> findAll();

    User findByUsername(String username);

    void updateUsersWithMatch(Match existing);

    void updateUserWithMissingMatches(String username);
}
