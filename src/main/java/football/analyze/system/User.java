package football.analyze.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import football.analyze.common.Entity;
import lombok.Getter;

/**
 * @author Hassan Mushtaq
 * @since 5/18/18
 */

@Getter
public class User extends Entity {

    private String displayName;

    private Role role;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //For jackson mapper
    private User() {
    }

    public User(String displayName, Role role, String username, String password) {
        super();
        this.displayName = displayName;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }
}
