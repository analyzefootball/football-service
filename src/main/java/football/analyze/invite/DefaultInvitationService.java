package football.analyze.invite;

import football.analyze.common.Email;
import football.analyze.common.EmailService;
import football.analyze.system.IsAdmin;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@Service
@IsAdmin
public class DefaultInvitationService implements InvitationService {

    private final InvitationRepository invitationRepository;

    private final EmailService emailService;

    private final static String FROM = "admin@analyze.football";

    private final static String SUBJECT = "Invitation to join Analyze Football";

    public DefaultInvitationService(InvitationRepository invitationRepository, EmailService emailService) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
    }

    @Override
    public void sendInvite(Invitation invitation, String link) {
        try {
            Invitation existing = invitationRepository.findByEmail(invitation.getEmail());
            if (existing != null) {
                invitationRepository.delete(existing);
            }
            invitationRepository.save(invitation);
            String correctLink = link.replace("{inviteId}", invitation.getId());
            String body = "Please click following link to complete sign-up<br/><br/>" + correctLink;
            Email email = new Email(FROM, invitation.getEmail(), body, SUBJECT);
            emailService.sendMail(email);
        } catch (Exception e) {
            invitationRepository.delete(invitation);
            throw new RuntimeException(e);
        }
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
