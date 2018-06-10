package football.analyze.system;

import football.analyze.invite.Invitation;
import football.analyze.invite.InvitationRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
@Service
public class DefaultUserServiceImpl implements UserService {

    private final InvitationRepository invitationRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DefaultUserServiceImpl(InvitationRepository invitationRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User signUpUser(String inviteId, User user) {
        Invitation invitation = invitationRepository.findById(inviteId).orElse(null);
        if (invitation == null || user == null
                || !invitation.getEmail().equals(user.getUsername()) || !invitation.getRole().equals(user.getRole())) {
            throw new IllegalArgumentException();
        }
        user.encodePassword(bCryptPasswordEncoder);
        userRepository.save(user);
        invitationRepository.delete(invitation);
        return user;
    }
}
