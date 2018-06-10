package football.analyze.invite;

import football.analyze.common.Migration;
import football.analyze.system.User;
import football.analyze.system.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Hassan Mushtaq
 * @since 6/10/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = SignUpController.class)
@AutoConfigureMockMvc(secure = false)
public class SignUpControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private Migration migration;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldBeBadRequestIfUserServiceThrowsException() throws Exception {
        MockHttpServletRequestBuilder request = post("/signup/1234")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"email\" : \"something@something.com\", \"role\" : \"REGULAR\"}");

        when(userService.signUpUser(any(String.class), any(User.class))).thenThrow(new RuntimeException());
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

}