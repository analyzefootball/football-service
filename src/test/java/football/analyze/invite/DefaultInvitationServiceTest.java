package football.analyze.invite;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import football.analyze.system.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * @author Hassan Mushtaq
 * @since 6/7/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultInvitationServiceTest {

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private InvitationRepository invitationRepository;

    @Value("${spring.mail.port}")
    private Integer mailPort;


    @Before
    public void setup() {
        invitationRepository.deleteAll();
    }

    @Test
    public void sendInviteShouldSendEmailAndCreateRecordInDb() throws MessagingException {
        GreenMail smtpServer = new GreenMail(new ServerSetup(mailPort, null, "smtp"));
        smtpServer.start();
        String link = "http://anyurl/{inviteId}";
        Invitation invitation = new Invitation("fake@fakemail.com", Role.ADMIN);

        invitationService.sendInvite(invitation, link);

        //testing email part
        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        MimeMessage current = receivedMessages[0];
        assertEquals("Invitation to join Analyze Football", current.getSubject());
        assertEquals("fake@fakemail.com", current.getAllRecipients()[0].toString());
        smtpServer.stop();

        //testing db
        Invitation dbInvitation = invitationRepository.findById(invitation.getId()).get();
        assertEquals("fake@fakemail.com", dbInvitation.getEmail());
        assertEquals(Role.ADMIN, dbInvitation.getRole());

    }

    @Test
    public void getAllShouldReturnAllRecord() {
        Invitation invitation1 = new Invitation("abc@abc.com", Role.ADMIN);
        Invitation invitation2 = new Invitation("xyz@abc.com", Role.REGULAR);
        List<Invitation> invitationList = Arrays.asList(invitation1, invitation2);
        invitationRepository.saveAll(invitationList);

        List<Invitation> invitations = invitationService.findAll();

        assertThat(invitations, containsInAnyOrder(hasProperty("email", is("abc@abc.com")),
                hasProperty("email", is("xyz@abc.com"))));
    }

    @Test
    public void findByIdShouldReturnRecord() {
        Invitation invitation1 = new Invitation("abc@abc.com", Role.ADMIN);
        invitationRepository.save(invitation1);

        Invitation invitation = invitationService.findById(invitation1.getId());

        assertEquals("abc@abc.com", invitation.getEmail());
        assertEquals(Role.ADMIN, invitation.getRole());
    }
}