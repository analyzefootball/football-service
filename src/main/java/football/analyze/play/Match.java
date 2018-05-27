package football.analyze.play;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public class Match {

    private Team homeTeam;

    private Team awayTeam;

    private LocalDateTime dateTime;

    private Integer matchNumber;

    @Setter
    private Integer homeTeamScore;

    @Setter
    private Integer awayTeamScore;

    private Match() {
    }

    public Match(Team homeTeam, Team awayTeam, LocalDateTime dateTime, Integer matchNumber) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.dateTime = dateTime;
        this.matchNumber = matchNumber;
    }
}
