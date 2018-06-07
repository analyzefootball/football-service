package football.analyze.invite;

import football.analyze.common.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@Service
public class DefaultInvitationService implements InvitationService {

    private final InvitationRepository invitationRepository;

    private final EmailService emailService;

    public DefaultInvitationService(InvitationRepository invitationRepository, EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendInvite(Invitation invitation) {
        invitationRepository.save(invitation);
    }

    @Override
    public List<Invitation> findAll() {
        return invitationRepository.findAll();
    }

    @Override
    public Invitation findById(String inviteId) {
        return invitationRepository.findById(inviteId).orElse(null);
    }
}
