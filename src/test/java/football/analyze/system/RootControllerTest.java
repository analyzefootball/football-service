package football.analyze.system;

import football.analyze.play.TournamentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = RootController.class)
@AutoConfigureMockMvc(secure = false)
public class RootControllerTest {

    @MockBean
    private TournamentRepository tournamentRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void hasCorrectUrls() throws Exception {
        MockHttpServletRequestBuilder request = get("/");

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._links.users.href", equalTo("http://localhost/users")))
                .andExpect(jsonPath("$._links.tournaments.href", equalTo("http://localhost/tournaments")));
    }
}