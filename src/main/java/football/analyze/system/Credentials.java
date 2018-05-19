package football.analyze.system;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * @author Hassan Mushtaq
 * @since 5/19/18
 */
@Getter
@Setter
@Embeddable
public class Credentials {

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


}
