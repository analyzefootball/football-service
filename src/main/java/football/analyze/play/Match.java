package football.analyze.play;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public class Match {

    @Setter
    private Team homeTeam;

    @Setter
    private Team awayTeam;

    private LocalDateTime dateTime;

    private Integer matchNumber;

    private MatchType matchType;

    @Setter
    private Integer homeTeamScore;

    @Setter
    private Integer awayTeamScore;

    private Match() {
    }

    public Match(Team homeTeam, Team awayTeam, LocalDateTime dateTime, Integer matchNumber, MatchType matchType) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.dateTime = dateTime;
        this.matchNumber = matchNumber;
        this.matchType = matchType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return Objects.equals(matchNumber, match.matchNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(matchNumber);
    }

    public ResultType getResultType() {
        if (homeTeamScore == null || awayTeamScore == null) {
            return ResultType.INVALID;
        }
        if (homeTeamScore.equals(awayTeamScore)) {
            return ResultType.DRAW;
        } else if (homeTeamScore > awayTeamScore) {
            return ResultType.HOMEWIN;
        } else if (awayTeamScore > homeTeamScore) {
            return ResultType.AWAYWIN;
        }
        return ResultType.INVALID;
    }
}
