package football.analyze.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = RootController.class, secure = false)
public class RootControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void hasCorrectLoginUrl() throws Exception {
        MockHttpServletRequestBuilder request = get("/");

        mockMvc.perform(request)
                .andExpect(jsonPath("$._links.login.href", equalTo("http://localhost/login")));
    }
}