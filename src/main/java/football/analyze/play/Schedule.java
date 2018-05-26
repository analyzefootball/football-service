package football.analyze.play;

import lombok.Getter;

import java.util.List;

/**
 * @author Hassan Mushtaq
 * @since 5/25/18
 */
@Getter
public class Schedule {

    private List<Match> matches;

    private Schedule() {
    }

    public Schedule(List<Match> matches) {
        this.matches = matches;
    }
}
