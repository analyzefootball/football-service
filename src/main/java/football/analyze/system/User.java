package football.analyze.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import football.analyze.common.Entity;
import football.analyze.play.Team;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Hassan Mushtaq
 * @since 5/18/18
 */

@Getter
public class User extends Entity {

    @NotBlank
    private String displayName;

    @NotNull
    private Role role;

    @NotBlank
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    private String password;

    private Team favoriteTeam;

    //For jackson mapper
    private User() {
    }

    public User(String displayName, Role role, String username, String password, Team favoriteTeam) {
        super();
        this.displayName = displayName;
        this.role = role;
        this.username = username;
        this.password = password;
        this.favoriteTeam = favoriteTeam;
    }

    public boolean isAdmin() {
        return role.equals(Role.ADMIN);
    }

    void encodePassword(BCryptPasswordEncoder encoder) {
        encoder.encode(this.password);
    }

    void update(User user, BCryptPasswordEncoder encoder) {
        if (user.getPassword() != null) {
            this.password = encoder.encode(user.getPassword());
        }
        if (user.getRole() != null) {
            this.role = user.getRole();
        }
        if (user.getDisplayName() != null) {
            this.displayName = user.getDisplayName();
        }
        if (user.getFavoriteTeam() != null) {
            this.favoriteTeam = user.getFavoriteTeam();
        }
    }
}
