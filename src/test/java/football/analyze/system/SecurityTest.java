package football.analyze.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Hassan Mushtaq
 * @since 6/5/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    public void shouldReturnForbiddenWithoutAuthentication() throws Exception {
        MockHttpServletRequestBuilder request = get("/");
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionIfIncorrectData() throws Exception {
        MockHttpServletRequestBuilder request = post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"user\" : \"dasdas\", \"pass\" : \"das\"}");
        mockMvc.perform(request);
    }

    @Test
    public void shouldReturnUnauthorizedIfLoginWithInCorrectCredentials() throws Exception {
        MockHttpServletRequestBuilder request = post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\" : \"dasdas\", \"password\" : \"das\"}");
        mockMvc.perform(request)
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnSuccessAndHeaderWithSuccessfulLogin() throws Exception {
        User adminUser = new User("admin user", Role.ADMIN, "admin@admin.com", bCryptPasswordEncoder.encode("password"), null);
        userRepository.save(adminUser);
        MockHttpServletRequestBuilder request = post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\" : \"admin@admin.com\", \"password\" : \"password\"}");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(header().exists("Authorization"));
    }
}
