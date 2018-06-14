package football.analyze.play;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 6/13/18
 */
@Getter
@Setter
@NoArgsConstructor
public class PlayedMatch {

    private Team homeTeam;

    private Team awayTeam;

    private LocalDateTime dateTime;

    private Integer matchNumber;

    private MatchType matchType;

    private Integer homeTeamScore;

    private Integer awayTeamScore;

    private List<UserMatchPrediction> userMatchPredictions;

    public PlayedMatch(Match match, List<UserMatchPrediction> userMatchPredictions) {
        this.homeTeam = match.getHomeTeam();
        this.awayTeam = match.getAwayTeam();
        this.dateTime = match.getDateTime();
        this.matchNumber = match.getMatchNumber();
        this.matchType = match.getMatchType();
        this.userMatchPredictions = userMatchPredictions;
    }
}
