package football.analyze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Hassan Mushtaq
 * @since 6/7/18
 */
@Configuration
public class TestConfig {

//    @Bean
//    @Profile("test")
//    public JavaMailSender javaMailSender() {
//        return new JavaMailSenderImpl();
//    }
}