package football.analyze.system;

import football.analyze.invite.Invitation;
import football.analyze.invite.InvitationRepository;
import football.analyze.play.Match;
import football.analyze.play.MatchType;
import football.analyze.play.Tournament;
import football.analyze.play.TournamentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
@Service
public class DefaultUserServiceImpl implements UserService {

    private final InvitationRepository invitationRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final TournamentRepository tournamentRepository;

    public DefaultUserServiceImpl(InvitationRepository invitationRepository, UserRepository userRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder, TournamentRepository tournamentRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public User signUpUser(String inviteId, User user) {
        Invitation invitation = invitationRepository.findById(inviteId).orElse(null);
        if (invitation == null || user == null
                || !invitation.getEmail().equals(user.getUsername()) || !invitation.getRole().equals(user.getRole())) {
            throw new IllegalArgumentException();
        }
        User existing = userRepository.findByUsernameIgnoreCase(user.getUsername());
        if (existing != null) {
            throw new IllegalArgumentException();
        }
        user.encodePassword(bCryptPasswordEncoder);
        Tournament tournament = tournamentRepository.findByName("Fifa 2018 World Cup");
        List<Match> basicMatches = new ArrayList<>(48);
        tournament.getSchedule().getMatches().forEach(match -> {
            if (match.getMatchType().equals(MatchType.GROUP))   {
                match.setAwayTeamScore(null);
                match.setHomeTeamScore(null);
                basicMatches.add(match);
            }
        });
        user.initializePredictions(basicMatches);
        userRepository.save(user);
        invitationRepository.delete(invitation);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public void updateUser(String username, User user) {
        User existingUser = userRepository.findByUsernameIgnoreCase(username);
        if (existingUser == null) {
            throw new IllegalArgumentException();
        }
        existingUser.update(user, bCryptPasswordEncoder);
        userRepository.save(existingUser);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
