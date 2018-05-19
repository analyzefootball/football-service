package football.analyze;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.TimeZone;

import static org.junit.Assert.assertEquals;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationTest {

    @Test
    public void systemTimezoneShouldBeUTC() {
        assertEquals(TimeZone.getTimeZone("UTC"), TimeZone.getDefault());
    }

}
