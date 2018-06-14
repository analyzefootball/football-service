package football.analyze.play;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hassan Mushtaq
 * @since 6/11/18
 */
@NoArgsConstructor
@Getter
@Setter
public class UserPrediction {

    private String user;

    private Match match;

    public UserPrediction(String user, Match match) {
        this.user = user;
        this.match = match;
    }
}
