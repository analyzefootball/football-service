package football.analyze.system;

import football.analyze.common.Entity;
import lombok.Getter;
import org.springframework.data.annotation.Id;


import java.util.UUID;

/**
 * @author Hassan Mushtaq
 * @since 5/18/18
 */

@Getter
public class User extends Entity {

    private String displayName;

    private Role role;

    private Credentials credentials;

    //For jackson mapper
    private User() {
        //super();
    }

    public User(String id, String displayName, Role role, Credentials credentials) {
        super(id);
        this.displayName = displayName;
        this.role = role;
        this.credentials = credentials;
    }
}
