package football.analyze.invite;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = InvitationController.class)
@AutoConfigureMockMvc(secure = false)
public class InvitationControllerTest {

    @MockBean
    private InvitationService invitationService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void sendInviteShouldSaveInviteAndSendEmail() throws Exception {
        MockHttpServletRequestBuilder request = post("/invitation")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"email\" : \"something@something.com\", \"role\" : \"REGULAR\"}");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.self.href", startsWith("http://localhost/invitation")));

        Mockito.verify(invitationService).sendInvite(Mockito.any(Invitation.class));
    }

}