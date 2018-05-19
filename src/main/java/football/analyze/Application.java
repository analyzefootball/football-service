package football.analyze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Spring boot application starting point.
 * @author hassan
 * @since 5/7/18
 */
@SpringBootApplication
public class Application {

    @PostConstruct
    void init()  {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
