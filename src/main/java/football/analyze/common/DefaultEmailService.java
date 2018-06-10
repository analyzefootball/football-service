package football.analyze.common;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

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
    @Async
    public void sendMail(Email email) throws Exception {
        MimeMessage mail = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setFrom(email.getFrom());
        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setText(email.getBody(), true);
        emailSender.send(mail);
    }
}
