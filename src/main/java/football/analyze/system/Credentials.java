package football.analyze.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Embeddable;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@Getter
@Embeddable
public class Credentials {

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    //For Jackson Mapper
    private Credentials() {
    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
