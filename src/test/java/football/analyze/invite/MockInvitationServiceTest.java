package football.analyze.invite;

import football.analyze.common.Email;
import football.analyze.common.EmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * @author Hassan Mushtaq
 * @since 6/7/18
 */
@RunWith(MockitoJUnitRunner.class)
public class MockInvitationServiceTest {

    @Mock
    private InvitationRepository invitationRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private DefaultInvitationService defaultInvitationService;

    @Test
    public void shouldDeleteExistingInvite() {
        String link = "http://localhost/{inviteId}";
        Invitation invitation = mock(Invitation.class);
        Invitation existing = mock(Invitation.class);

        when(invitation.getEmail()).thenReturn("ab@ab.com");
        when(invitation.getId()).thenReturn("1234");
        when(invitationRepository.findByEmail("ab@ab.com")).thenReturn(existing);
        when(invitationRepository.save(invitation)).thenReturn(invitation);

        defaultInvitationService.sendInvite(invitation, link);
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

        defaultInvitationService.sendInvite(invitation, link);

        verify(invitationRepository).delete(invitation);
    }
}