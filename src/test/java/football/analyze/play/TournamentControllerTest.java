package football.analyze.play;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static football.analyze.system.SecurityConstants.AUTHORITIES;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Hassan Mushtaq
 * @since 5/27/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TournamentControllerTest {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Autowired
    private TournamentRepository tournamentRepository;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private String token;

    @Before
    public void setup() throws UnsupportedEncodingException {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        token = createJWT();
    }

    @Test
    public void testTournamentControllerMethods() throws Exception {
        MockHttpServletRequestBuilder request =
                get("/tournaments/").header("Authorization", "Bearer " + token);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$._links.self[0].href", is("http://localhost/tournaments")))
                .andExpect(jsonPath("$._embedded.tournamentList[0].name", is("Fifa 2018 World Cup")));

    }

    private String createJWT() throws UnsupportedEncodingException {
        ZonedDateTime zdt = LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());
        String[] grantedAuthorities = {"ROLE_ADMIN"};
        return JWT.create().withSubject("admin")
                .withArrayClaim(AUTHORITIES, grantedAuthorities)
                .withClaim("admin", true)
                .withClaim("displayName", "Super Admin")
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC512(jwtSecret));
    }
}