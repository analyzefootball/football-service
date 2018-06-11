package football.analyze.invite;

import football.analyze.common.Migration;
import football.analyze.system.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private Migration migration;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void sendInviteShouldSaveInviteAndSendEmail() throws Exception {
        MockHttpServletRequestBuilder request = post("/invitations")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"email\" : \"something@something.com\", \"role\" : \"REGULAR\"}");

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$._links.self.href", startsWith("http://localhost/invitations")));

        Mockito.verify(invitationService).sendInvite(Mockito.any(Invitation.class), eq("http://localhost/invitations/{inviteId}"));
    }

    @Test
    public void getAllInvitesShouldReturnInvitations() throws Exception {
        Invitation invitation1 = new Invitation("anc@abc.com", Role.REGULAR);
        List<Invitation> invitations = Arrays.asList(invitation1);
        when(invitationService.findAll()).thenReturn(invitations);
        MockHttpServletRequestBuilder request = get("/invitations");

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"email\":\"anc@abc.com\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"role\":\"REGULAR\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"createDate\""));
    }

    @Test
    public void getInviteByIdShouldReturnInvitation() throws Exception {
        String id = "someid";
        Invitation invitation = new Invitation("anc@abc.com", Role.REGULAR);
        when(invitationService.findById(id)).thenReturn(invitation);
        MockHttpServletRequestBuilder request = get("/invitations/" + id);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"email\":\"anc@abc.com\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"role\":\"REGULAR\""));
        assertTrue(result.getResponse().getContentAsString().contains("\"createDate\""));
    }

    @Test
    public void getInviteByIdShouldReturnNotFoundIfMissingInvitation() throws Exception {
        String id = "someid";
        when(invitationService.findById(id)).thenReturn(null);
        MockHttpServletRequestBuilder request = get("/invitations/" + id);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}