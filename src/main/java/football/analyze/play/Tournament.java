package football.analyze.play;

import football.analyze.common.Entity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public class Tournament extends Entity {

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Team> teams;

    private List<Group> groups;

    private Schedule schedule;

    private Tournament() {
    }

    public Tournament(String name, LocalDate startDate, LocalDate endDate, List<Team> teams, List<Group> groups, Schedule schedule) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teams = teams;
        this.groups = groups;
        this.schedule = schedule;
    }

    public void updateMatchResult(Match match) {
        Match existingMatch = schedule.getMatches().stream().filter(match::equals).findFirst().get();
        existingMatch.setHomeTeamScore(match.getHomeTeamScore());
        existingMatch.setAwayTeamScore(match.getAwayTeamScore());
    }
}
