package football.analyze.invite;

import football.analyze.common.Entity;
import football.analyze.system.Role;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author Hassan Mushtaq
 * @since 6/6/18
 */
@Getter
@Document(collection = "invitation")
public class Invitation extends Entity {

    private String email;

    @Indexed(name = "deleteAt", expireAfterSeconds = 172800)
    private LocalDateTime createDate = LocalDateTime.now();

    private Role role;

    private boolean accepted;

    //For jackson mapper
    private Invitation() {
    }

    public Invitation(String email, Role role) {
        this.email = email;
        this.role = role;
    }
}
