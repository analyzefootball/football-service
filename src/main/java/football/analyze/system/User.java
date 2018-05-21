package football.analyze.system;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Hassan Mushtaq
 * @since 5/18/18
 */

@Getter
@Entity
@Inheritance
@DiscriminatorColumn(name = "role")
@Table(name = "User")
public class User {

    @Id
    private UUID id;

    private String displayName;

    private Role role;

    private Credentials credentials;

    //For jackson mapper
    private User() {
    }

    public User(UUID id, String displayName, Role role, Credentials credentials) {
        this.id = id;
        this.displayName = displayName;
        this.role = role;
        this.credentials = credentials;
    }
}
