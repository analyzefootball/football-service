package football.analyze.common;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@Component
public class DefaultEmailService implements EmailService {

    private final JavaMailSender emailSender;

    public DefaultEmailService(JavaMailSender javaMailSender) {
        this.emailSender = javaMailSender;
    }

    @Override
    public void sendMail(Email email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getTo());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setFrom(email.getFrom());
        mailMessage.setText(email.getBody());
        emailSender.send(mailMessage);
    }
}
