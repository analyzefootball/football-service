package football.analyze.invite;

import football.analyze.common.Email;
import football.analyze.common.EmailService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Hassan Mushtaq
 * @since 6/7/18
 */
public class MockInvitationServiceTest {

    private InvitationRepository invitationRepository;

    private EmailService emailService;

    private String link = "http://localhost/{inviteId}";

    private DefaultInvitationService defaultInvitationService;

    @Before
    public void setup() {
        invitationRepository = mock(InvitationRepository.class);
        emailService = mock(EmailService.class);
        defaultInvitationService = new DefaultInvitationService(invitationRepository, emailService, link);
    }

    @Test
    public void shouldDeleteExistingInvite() {
        Invitation invitation = mock(Invitation.class);
        Invitation existing = mock(Invitation.class);

        when(invitation.getEmail()).thenReturn("ab@ab.com");
        when(invitation.getId()).thenReturn("1234");
        when(invitationRepository.findByEmail("ab@ab.com")).thenReturn(existing);
        when(invitationRepository.save(invitation)).thenReturn(invitation);

        defaultInvitationService.sendInvite(invitation);
        verify(invitationRepository).delete(existing);
    }

    @Test(expected = RuntimeException.class)
    public void shouldRollbackTransaction() throws Exception {
        String link = "http://localhost/{inviteId}";
        Invitation invitation = mock(Invitation.class);
        Invitation existing = mock(Invitation.class);

        when(invitation.getEmail()).thenReturn("ab@ab.com");
        when(invitation.getId()).thenReturn("1234");
        when(invitationRepository.findByEmail("ab@ab.com")).thenReturn(existing);
        when(invitationRepository.save(invitation)).thenReturn(invitation);
        doThrow(new Exception()).when(emailService).sendMail(any(Email.class));

        defaultInvitationService.sendInvite(invitation);

        verify(invitationRepository).delete(invitation);
    }
}