package football.analyze.system;

import football.analyze.invite.Invitation;
import football.analyze.invite.InvitationRepository;
import football.analyze.play.Schedule;
import football.analyze.play.Team;
import football.analyze.play.Tournament;
import football.analyze.play.TournamentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Hassan Mushtaq
 * @since 6/10/18
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultUserServiceImplTest {

    private InvitationRepository invitationRepository;

    private UserRepository userRepository;

    private TournamentRepository tournamentRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private DefaultUserServiceImpl userService;

    @Before
    public void setup() {
        invitationRepository = mock(InvitationRepository.class);
        userRepository = mock(UserRepository.class);
        tournamentRepository = mock(TournamentRepository.class);
        userService = new DefaultUserServiceImpl(invitationRepository, userRepository, bCryptPasswordEncoder, tournamentRepository);
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
        Tournament tournament = mock(Tournament.class);
        Schedule schedule = mock(Schedule.class);
        when(tournament.getSchedule()).thenReturn(schedule);
        when(schedule.getMatches()).thenReturn(Collections.emptyList());
        when(invitationRepository.findById("123")).thenReturn(Optional.of(invitation));
        when(tournamentRepository.findByName("Fifa 2018 World Cup")).thenReturn(tournament);
        userService.signUpUser("123", user);
        verify(userRepository).save(user);
        verify(invitationRepository).delete(invitation);
    }

    @Test
    public void getAll() {
        final User user1 = new User("display name", Role.REGULAR, "username", "password", null);
        final User user2 = new User("display name2", Role.REGULAR, "username2", "password2", null);
        List<User> allUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(allUsers);
        List<User> serviceUsers = userService.findAll();
        assertThat(serviceUsers, containsInAnyOrder(user1, user2));
    }

    @Test
    public void getByUserName() {
        final User user1 = new User("display name", Role.REGULAR, "username", "password", null);
        when(userRepository.findByUsernameIgnoreCase("username")).thenReturn(user1);
        User serviceUser = userService.findByUsername("username");
        assertThat(serviceUser, is(user1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserShouldThrowExceptionIfUsernameNotFound() {
        final User user1 = new User("display name", Role.REGULAR, "username", "password", null);
        when(userRepository.findByUsernameIgnoreCase("abcd")).thenReturn(null);
        userService.updateUser("abcd", user1);
    }

    @Test
    public void updateUserShouldUpdateGivenFields() {
        final User original = new User("display name", Role.REGULAR, "username", "password", null);
        when(userRepository.findByUsernameIgnoreCase("username")).thenReturn(original);
        final Team team1 = new Team("abcd", "qpr");
        final User updatedUser = new User("new name", Role.ADMIN, null, "password2", team1);
        userService.updateUser("username", updatedUser);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUsername(), equalTo("username"));
        assertThat(argumentCaptor.getValue().getPassword(), equalTo(original.getPassword()));
        assertThat(argumentCaptor.getValue().getPassword(), not("password"));
        assertThat(argumentCaptor.getValue().getRole(), equalTo(Role.ADMIN));
        assertThat(argumentCaptor.getValue().getDisplayName(), equalTo("new name"));
        assertThat(argumentCaptor.getValue().getFavoriteTeam(), equalTo(team1));
    }
}