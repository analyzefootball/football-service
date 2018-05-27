package football.analyze.play;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ResourceUtils;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Hassan Mushtaq
 * @since 5/27/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TournamentControllerTest {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTournamentControllerMethods() throws Exception {
        File file = ResourceUtils.getFile("classpath:fixtures/worldcup.json");
        Tournament tournament = jacksonObjectMapper.readValue(file, Tournament.class);
        tournamentRepository.save(tournament);

        MockHttpServletRequestBuilder request = get("/tournaments/" + tournament.getId());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/tournaments/" + tournament.getId())))
                .andExpect(jsonPath("$.name", is("Fifa 2018 World Cup")));

    }
}