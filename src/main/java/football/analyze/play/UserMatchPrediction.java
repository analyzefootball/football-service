package football.analyze.play;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Hassan Mushtaq
 * @since 6/13/18
 */
@Getter
@Setter
@NoArgsConstructor
public class UserMatchPrediction {

    private String user;

    private Integer homeTeamScore;

    private Integer awayTeamScore;

    public UserMatchPrediction(String user, Integer homeTeamScore, Integer awayTeamScore) {
        this.user = user;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }
}
