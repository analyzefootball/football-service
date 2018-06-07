package football.analyze.common;

import lombok.Getter;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@Getter
public class Email {

    private final String from;

    private final String to;

    private final String body;

    private final String subject;

    public Email(String from, String to, String body, String subject) {
        this.from = from;
        this.to = to;
        this.body = body;
        this.subject = subject;
    }
}
