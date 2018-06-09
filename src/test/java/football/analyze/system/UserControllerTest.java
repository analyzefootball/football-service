package football.analyze.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Hassan Mushtaq
 * @since 6/9/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

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
    public void shouldNotAllowAnonymousAccessToGetAllUsers() throws Exception {
        MockHttpServletRequestBuilder request = get("/users");
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldNotAllowRegularUserToGetAllUsers() throws Exception {
        String token = createAndLoginRegularUser();
        MockHttpServletRequestBuilder request = get("/users").header("Authorization", token);
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowAdminUserToGetAllUsers() throws Exception {
        String token = createAndLoginAdminUser();
        MockHttpServletRequestBuilder request = get("/users").header("Authorization", token);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"displayName\":\"ADMIN\""));
    }

    @Test
    public void shouldNotAllowAnonymousAccessToGetUserByUsername() throws Exception {
        MockHttpServletRequestBuilder request = get("/users/someuser");
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowAdminUserToGetAnyUserByUsername() throws Exception {
        User adminUser = createUser(Role.ADMIN);
        User regularUser = createUser(Role.REGULAR);
        String token = login(adminUser.getUsername());
        MockHttpServletRequestBuilder request = get("/users/" + regularUser.getUsername()).header("Authorization", token);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"displayName\":\"REGULAR\""));
    }

    @Test
    public void shouldNotAllowRegularUserToGetAnyUserByUsername() throws Exception {
        User adminUser = createUser(Role.ADMIN);
        User regularUser = createUser(Role.REGULAR);
        String token = login(regularUser.getUsername());
        MockHttpServletRequestBuilder request = get("/users/" + adminUser.getUsername()).header("Authorization", token);
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldAllowRegularnUserToGetItself() throws Exception {
        User regularUser = createUser(Role.REGULAR);
        String token = login(regularUser.getUsername());
        MockHttpServletRequestBuilder request = get("/users/" + regularUser.getUsername()).header("Authorization", token);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8_VALUE))
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("\"displayName\":\"REGULAR\""));
    }

    private String createAndLoginRegularUser() throws Exception {
        User user = createUser(Role.REGULAR);
        return login(user.getUsername());
    }

    private String createAndLoginAdminUser() throws Exception {
        User user = createUser(Role.ADMIN);
        return login(user.getUsername());
    }

    private User createUser(Role role) {
        User user = new User(role.name(), role, UUID.randomUUID().toString(),
                bCryptPasswordEncoder.encode("password"));
        userRepository.save(user);
        return user;
    }

    private String login(String username) throws Exception {
        MockHttpServletRequestBuilder request = post("/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\" : \"" + username + "\", \"password\" : \"password\"}");
        MvcResult result = mockMvc.perform(request)
                .andReturn();
        return result.getResponse().getHeader("Authorization");
    }
}