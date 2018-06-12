package football.analyze.invite;

import football.analyze.common.Email;
import football.analyze.common.EmailService;
import org.springframework.beans.factory.annotation.Value;
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

    private final String link;

    private final static String FROM = "admin@analyze.football";

    private final static String SUBJECT = "Invitation to join Analyze Football";

    public DefaultInvitationService(InvitationRepository invitationRepository, EmailService emailService,
                                    @Value("${signup.link}") String link) {
        this.invitationRepository = invitationRepository;
        this.emailService = emailService;
        this.link = link;
    }

    @Override
    public void sendInvite(Invitation invitation) {
        try {
            Invitation existing = invitationRepository.findByEmail(invitation.getEmail());
            if (existing != null) {
                invitationRepository.delete(existing);
            }
            invitationRepository.save(invitation);
            String correctLink = link + invitation.getId();
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
