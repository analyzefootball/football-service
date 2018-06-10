package football.analyze.system;

import football.analyze.invite.Invitation;
import football.analyze.invite.InvitationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author Hassan Mushtaq
 * @since 6/10/18
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceImplTest {

    private InvitationRepository invitationRepository;

    private UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private DefaultUserServiceImpl userService;

    @Before
    public void setup() {
        invitationRepository = mock(InvitationRepository.class);
        userRepository = mock(UserRepository.class);
        userService = new DefaultUserServiceImpl(invitationRepository, userRepository, bCryptPasswordEncoder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingInvitation() {
        final User user = new User("display name", Role.ADMIN, "username", "password", null);
        when(invitationRepository.findById("123")).thenReturn(Optional.empty());
        userService.signUpUser("123", user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingUser() {
        Invitation invitation = mock(Invitation.class);
        when(invitationRepository.findById("123")).thenReturn(Optional.of(invitation));
        userService.signUpUser("123", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mismatchUsername() {
        final User user = new User("display name", Role.ADMIN, "username", "password", null);
        Invitation invitation = new Invitation("user", Role.ADMIN);
        when(invitationRepository.findById("123")).thenReturn(Optional.of(invitation));
        userService.signUpUser("123", user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mismatchRole() {
        final User user = new User("display name", Role.ADMIN, "username", "password", null);
        Invitation invitation = new Invitation("username", Role.REGULAR);
        when(invitationRepository.findById("123")).thenReturn(Optional.of(invitation));
        userService.signUpUser("123", user);
    }

    @Test
    public void successFlow() {
        final User user = new User("display name", Role.REGULAR, "username", "password", null);
        Invitation invitation = new Invitation("username", Role.REGULAR);
        when(invitationRepository.findById("123")).thenReturn(Optional.of(invitation));
        userService.signUpUser("123", user);
        verify(userRepository).save(user);
        verify(invitationRepository).delete(invitation);
    }
}